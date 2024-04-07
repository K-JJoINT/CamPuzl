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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoothCommentServiceImpl implements BoothCommentService{
    private final BoothCommentRepository boothCommentRepository;
    private final BoothRepository boothRepository;

    //TODO 멤버 정보도 저장해야 함
    @Override
    public BoothComment save(Long boothId, BoothCommentDTO boothCommentDTO) {
        Optional<Booth> optionalBooth = boothRepository.findById(boothId);

        if(optionalBooth.isPresent()) {
            Booth booth = optionalBooth.get();

            //코멘트가 저장되는 부스와 사용자가 작성하는 코멘트의 부스가 동일해야 함
            if(Objects.equals(boothId, booth.getId())) {
                BoothComment boothComment = new BoothComment(boothCommentDTO.getReview(),
                        boothCommentDTO.getRating(),
                        booth);

                return boothCommentRepository.save(boothComment);
            } else { //동일하지 않았을 때
                throw new BusinessException(ErrorCode.ILLEGAL_ARGUMENT);
            }
        }
        else {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public ArrayList<BoothCommentDTO> findByIdAll(Long id) {
        ArrayList<BoothComment> listBoothComment = boothCommentRepository.findByBoothId(id);
        ArrayList<BoothCommentDTO> listBoothCommentDTO = new ArrayList<>();

        for (BoothComment boothComment : listBoothComment) {
            Booth booth = boothComment.getBooth();
            BoothCommentDTO boothCommentDTO = new BoothCommentDTO(boothComment.getReview(),
                    boothComment.getRating(),
                    booth);

            listBoothCommentDTO.add(boothCommentDTO);
        }

        return listBoothCommentDTO;
    }

    @Override
    public BoothComment update(Long boothID, BoothCommentDTO boothCommentDTO) {
        Optional<BoothComment> optionalBoothComment = boothCommentRepository.findById(boothID);
        if(optionalBoothComment.isPresent()) {
            BoothComment boothComment = optionalBoothComment.get();

            boothComment.updateInfo(boothComment.getReview(), boothComment.getRating());

            return boothComment;
        } else {
            throw new BusinessException(ErrorCode.BOOTH_COMMENT_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        boothRepository.deleteById(id);
    }
}
