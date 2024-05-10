package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.PubComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.PubCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.repository.PubCommentRepository;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.domain.pub.repository.PubRepository;
import com.JJoINT.CamPuzl.global.auth.dto.SecurityMemberDTO;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PubCommentService {
    private final PubCommentRepository pubCommentRepository;
    private final MemberRepository memberRepository;
    private final PubRepository pubRepository;

    public Page<PubComment> getCommentsByPubId(Long id, Pageable pageable) {
        return pubCommentRepository.findByPubIdAndDeletedAtIsNull(id, pageable);
    }

    @Transactional
    public void saveComment(Long pubId, PubCommentDTO commentDto, SecurityMemberDTO securityMemberDTO) {
        Member writer = memberRepository.findById(securityMemberDTO.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Pub pub = pubRepository.findById(pubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PUB_NOT_FOUND));

        PubComment comment = PubComment.builder()
                .review(commentDto.getReview())
                .rating(commentDto.getRating())
                .writer(writer)
                .pub(pub)
                .build();

        pubCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        PubComment comment = pubCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        comment.builder().deletedAt(LocalDateTime.now());
        pubCommentRepository.save(comment);
    }
}
