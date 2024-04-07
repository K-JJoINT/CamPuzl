package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.repository.BoothCommentRepository;
import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.repository.BoothRepository;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoothServiceImpl implements BoothService{

    private final BoothRepository boothRepository;
    private final BoothCommentRepository boothCommentRepository;

   //TODO 현재는 모든 사람이 등록 가능 -> 나중에 권한 수정
    //부스 등록
    @Override
    public Booth save(BoothDTO boothDTO) {

        List<Booth> boothList = boothRepository.findAll();
        //부스 이름이 동일하면 안 됨
        for(Booth booth : boothList) {
            if(booth.getBoothName().equals(boothDTO.getBoothName())) {
                throw new BusinessException(ErrorCode.BOOTH_NAME_DUPLICATION);
            }
        }
        Booth booth = new Booth(boothDTO.getBoothName(),
                boothDTO.getTentNum(),
                boothDTO.getExplanation(),
                boothDTO.getContents(),
                boothDTO.getEvent());

        return boothRepository.save(booth);
    }

    //모든 전체 목록 조회
    @Override
    public List<BoothDTO> findAll() {
        List<Booth> boothEntityList = boothRepository.findAll();
        List<BoothDTO> boothDTOList = new ArrayList<>();

        for(Booth boothEntity : boothEntityList) {
            if(boothEntity.getDeletedAt() == null) {
                boothDTOList.add(new BoothDTO(boothEntity.getBoothName(),
                        boothEntity.getTentNum(),
                        boothEntity.getTotalRating(),
                        boothEntity.getExplanation(),
                        boothEntity.getContents(),
                        boothEntity.getEvent()));
            }
        }

        return boothDTOList;
    }

    //부스 상세 조회
    @Override
    public BoothDTO findById(Long id) {
        Optional<Booth> optionalBooth = boothRepository.findById(id);
        if(optionalBooth.isPresent()) {
            Booth booth = optionalBooth.get();
            if(booth.getDeletedAt()==null) {
                BoothDTO boothDTO = new BoothDTO(booth.getBoothName(),
                        booth.getTentNum(),
                        booth.getTotalRating(),
                        booth.getExplanation(),
                        booth.getContents(),
                        booth.getEvent());

                return boothDTO;
            } else {
                throw new BusinessException(ErrorCode.BOOTH_ALREADY_DELETED);
            }
        } else {
            throw new BusinessException(ErrorCode.BOOTH_NOT_FOUND);
        }
    }

    //부스 정보 수정
    //TODO 현재는 모든 사람이 수정 가능 -> 나중에 권한 수정
    //TODO organization에서 booth 조회 -> organization으로 member 조회
    @Override
    public Booth update(Long boothID, BoothDTO boothDTO) {
        Optional<Booth> optionalBooth = boothRepository.findById(boothID);
        if(optionalBooth.isPresent()) {
            Booth booth = optionalBooth.get();

            booth.updateInfo(boothDTO.getBoothName(),
                    boothDTO.getTentNum(),
                    boothDTO.getExplanation(),
                    boothDTO.getContents(),
                    boothDTO.getEvent());

            return booth;
        } else {
            throw new BusinessException(ErrorCode.BOOTH_NOT_FOUND);
        }
    }

    //TODO 현재는 모든 사람이 삭제 가능 -> 나중에 권한 수정
    //부스 논리적 삭제
    @Override
    public void deleteById(Long id) {

        Optional<Booth> optionalBooth = boothRepository.findById(id);

        if(optionalBooth.isPresent()) {
            Booth booth = optionalBooth.get();
            //deleteAt 변경
            booth.delete();
            boothRepository.save(booth);
        } else {
            throw new BusinessException(ErrorCode.ILLEGAL_ARGUMENT);
        }
    }
}
