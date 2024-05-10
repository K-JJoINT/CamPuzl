package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.responseBoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.dto.requestBoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.repository.BoothCommentRepository;
import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.repository.BoothRepository;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.global.auth.jwt.JwtProvider;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoothCommentServiceImpl implements BoothCommentService{
    private final BoothCommentRepository boothCommentRepository;
    private final BoothRepository boothRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    //TODO 멤버 정보도 저장해야 함
    @Override
    @Transactional
    public BoothComment save(HttpServletRequest request, Long boothId, requestBoothCommentDTO requestDTO) {

        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        // HttpServletRequest에서 Authorization 헤더를 가져옴
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더의 값이 없거나 Bearer로 시작하지 않으면 null 반환
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        // 토큰 부분만 추출하여 반환
        String token = authorizationHeader.substring(7);

        // JWT 토큰을 사용하여 현재 로그인한 사용자의 ID를 추출
        long currentUserId = jwtProvider.extractIdFromToken(token);
        Member member = memberRepository.findById(currentUserId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // 한 사용자는 하나의 부스에서 하나의 평점만 등록할 수 있음
        List<BoothComment> comments = boothCommentRepository.findByBoothId(boothId);
        for(BoothComment boothComment : comments) {
            if(boothComment.getWriter().getId().equals(member.getId())) {
                throw new BusinessException(ErrorCode.BOOTH_COMMENT_ALREADY_RATED);
            }
        }

        BoothComment boothComment = new BoothComment(
                requestDTO.getReview(),
                requestDTO.getRating(),
                member,
                booth);

        //평점 수정
        double commentRating = requestDTO.getRating();

        double boothTotalRating = 0;
        double averageRating = 0;

        if (!comments.isEmpty()) {
            for (BoothComment comment : comments) {
                boothTotalRating += comment.getRating();
            }
            boothTotalRating += commentRating;
            averageRating = boothTotalRating / (comments.size()+1);
            booth.updateTotalRating(averageRating);
        } else {
            booth.updateTotalRating(commentRating);
        }
        return boothCommentRepository.save(boothComment);

    }

    @Override
    public ArrayList<responseBoothCommentDTO> findByIdAll(Long boothId) {

        Booth booth = boothRepository.findById(boothId).orElseThrow(()
                -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        ArrayList<BoothComment> listBoothComment = boothCommentRepository.findByBooth(booth);
        ArrayList<responseBoothCommentDTO> listBoothCommentDTO = new ArrayList<>();

        for (BoothComment boothComment : listBoothComment) {
            responseBoothCommentDTO responseBoothCommentDTO = new responseBoothCommentDTO(
                    boothComment.getReview(),
                    boothComment.getRating(),
                    boothComment.getWriter().getName());

            listBoothCommentDTO.add(responseBoothCommentDTO);
        }

        return listBoothCommentDTO;
    }

    @Override
    @Transactional
    public BoothComment update(Long boothCommentId, requestBoothCommentDTO requestDTO) {
        BoothComment boothComment = boothCommentRepository.findById(boothCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOTH_COMMENT_NOT_FOUND));

        boothComment.updateInfo(requestDTO.getReview(), requestDTO.getRating());

        return boothComment;
    }

    @Override
    @Transactional
    public void deleteById(Long boothCommentId) {
        boothCommentRepository.deleteById(boothCommentId);
    }

    @Override
    public void deletedByIdAll(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        ArrayList<BoothComment> listBoothComment = boothCommentRepository.findByBooth(booth);

        for (BoothComment boothComment : listBoothComment) {
            boothCommentRepository.deleteById(boothComment.getId());
        }
    }
}
