package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.saveRequestBoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.updateRequestBoothDTO;
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

    //부스 등록
    @Override
    @Transactional
    //TODO 현재는 모든 사람이 등록 가능 -> 나중에 권한 수정
    //TODO 회장이 등록
    public Booth save(saveRequestBoothDTO saveRequestBoothDTO) {

        List<Booth> boothList = boothRepository.findAllByDeletedAtIsNull();

        //부스 위치가 중복되면 안됨
        for(Booth booth : boothList) {
            if(booth.getTentNum().equals(saveRequestBoothDTO.getTentNum())) {
                throw new BusinessException(ErrorCode.BOOTH_DUPLICATE_LOCATION);
            }
        }

        Booth booth = new Booth(saveRequestBoothDTO.getBoothName(),
                saveRequestBoothDTO.getTentNum(),
                saveRequestBoothDTO.getExplanation(),
                saveRequestBoothDTO.getContents(),
                saveRequestBoothDTO.getEvent());

        return boothRepository.save(booth);
    }

    @Override
    public List<BoothDTO> findAll() {

        //논리적 삭제된 부스를 제외하고 모든 부스를 가져옴
        List<Booth> boothList = boothRepository.findAllByDeletedAtIsNull();

        List<BoothDTO> boothDTOList = new ArrayList<>();

        for(Booth booth : boothList){
            BoothDTO boothDTO = new BoothDTO(
                    booth.getBoothName(),
                    booth.getTentNum(),
                    booth.getTotalRating(),
                    booth.getExplanation(),
                    booth.getContents(),
                    booth.getEvent());
            boothDTOList.add(boothDTO);
        }

        return boothDTOList;
    }

    //부스 상세 조회
    @Override
    public BoothDTO findById(Long boothId) {

        //논리적 삭제된 부스인지 검사
        Booth booth = (Booth) boothRepository.findByIdAndDeletedAtIsNull(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        return new BoothDTO(booth.getBoothName(),
                booth.getTentNum(),
                booth.getTotalRating(),
                booth.getExplanation(),
                booth.getContents(),
                booth.getEvent());
    }

    //부스 정보 수정
    //TODO 현재는 모든 사람이 수정 가능 -> 나중에 권한 수정
    //TODO organization에서 booth 조회 -> organization으로 member 조회
    //TODO 회장 매니저 수정 가능, 회장이 승인
    @Override
    @Transactional
    public Booth update(Long boothID, updateRequestBoothDTO requestDTO) {
        Booth booth = boothRepository.findById(boothID).orElseThrow(()
                -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        booth.updateInfo(requestDTO.getBoothName(),
                requestDTO.getTentNum(),
                requestDTO.getExplanation(),
                requestDTO.getContents(),
                requestDTO.getEvent());

            return booth;
        }

    //TODO 현재는 모든 사람이 삭제 가능 -> 나중에 권한 수정
    //TODO 회장 권한
    //부스 논리적 삭제
    @Override
    @Transactional
    public void deleteById(Long boothID) {
        Booth booth = boothRepository.findById(boothID).orElseThrow(()
                -> new BusinessException(ErrorCode.BOOTH_NOT_FOUND));

        //deleteAt 변경
        booth.delete();
        boothRepository.save(booth);
    }
}
