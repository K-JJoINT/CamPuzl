package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.repository.BoothCommentRepository;
import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.repository.BoothRepository;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BoothCommentServiceImpl implements BoothCommentService{
    private final BoothCommentRepository boothCommentRepository;
    private final BoothRepository boothRepository;

    //TODO 멤버 정보도 저장해야 함
    @Override
    @Transactional
    public BoothComment save(BoothCommentDTO boothCommentDTO) {
        Long boothId = boothCommentDTO.getBooth().getId();
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        BoothComment boothComment = new BoothComment(boothCommentDTO.getReview(),
                boothCommentDTO.getRating(),
                booth);

        return boothCommentRepository.save(boothComment);
    }

    @Override
    public ArrayList<BoothCommentDTO> findByIdAll(Long id) {
        Booth booth = boothRepository.findById(id).orElseThrow(()
                -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        ArrayList<BoothComment> listBoothComment = boothCommentRepository.findByBooth(booth);
        ArrayList<BoothCommentDTO> listBoothCommentDTO = new ArrayList<>();

        for (BoothComment boothComment : listBoothComment) {
            BoothCommentDTO boothCommentDTO = new BoothCommentDTO(
                    boothComment.getReview(),
                    boothComment.getRating(),
                    boothComment.getBooth());

            listBoothCommentDTO.add(boothCommentDTO);
        }

        return listBoothCommentDTO;
    }

    @Override
    @Transactional
    public BoothComment update(BoothCommentDTO boothCommentDTO) {
        Long boothCommentID = boothCommentDTO.getBooth().getId();
        BoothComment boothComment = boothCommentRepository.findById(boothCommentID)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOTH_COMMENT_NOT_FOUND));

        boothComment.updateInfo(boothComment.getReview(), boothComment.getRating());

        return boothComment;
    }

    @Override
    public void deleteById(Long id) {
        boothRepository.deleteById(id);
    }
}
