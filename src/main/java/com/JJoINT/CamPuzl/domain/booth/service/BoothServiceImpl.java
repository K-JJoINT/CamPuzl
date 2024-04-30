package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.repository.BoothRepository;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoothServiceImpl implements BoothService{

    private final BoothRepository boothRepository;

   //TODO 현재는 모든 사람이 등록 가능 -> 나중에 권한 수정
    //부스 등록
    @Override
    @Transactional
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
    public BoothDTO findById(Long boothID) {
        Booth booth = boothRepository.findById(boothID).orElseThrow(()
                -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

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
    }

    //부스 정보 수정
    //TODO 현재는 모든 사람이 수정 가능 -> 나중에 권한 수정
    //TODO organization에서 booth 조회 -> organization으로 member 조회
    @Override
    @Transactional
    public Booth update(Long boothID, BoothDTO boothDTO) {
        Booth booth = boothRepository.findById(boothID).orElseThrow(()
                -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        booth.updateInfo(boothDTO.getBoothName(),
                boothDTO.getTentNum(),
                boothDTO.getExplanation(),
                boothDTO.getContents(),
                boothDTO.getEvent());

            return booth;
        }

    //TODO 현재는 모든 사람이 삭제 가능 -> 나중에 권한 수정
    //부스 논리적 삭제
    @Override
    public void deleteById(Long boothID) {
        Booth booth = boothRepository.findById(boothID).orElseThrow(()
                -> new BusinessException(ErrorCode.ILLEGAL_ARGUMENT));

        //deleteAt 변경
        booth.delete();
        boothRepository.save(booth);
    }
}
