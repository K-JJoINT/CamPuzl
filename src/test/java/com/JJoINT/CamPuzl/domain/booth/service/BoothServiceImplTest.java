package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.repository.BoothRepository;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class BoothServiceImplTest {

    @Autowired
    private BoothRepository boothRepository;
    @Autowired
    private BoothServiceImpl boothService;

    @Test
    void 부스등록() {
        //given
        BoothDTO boothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();

        //when
        Booth booth = boothService.save(boothDTO);

        //then
        assertThat(boothDTO.getBoothName()).isEqualTo(booth.getBoothName());
        assertThat(boothDTO.getTentNum()).isEqualTo(booth.getTentNum());
        assertThat(boothDTO.getExplanation()).isEqualTo(booth.getExplanation());
        assertThat(boothDTO.getContents()).isEqualTo(booth.getContents());
        assertThat(boothDTO.getEvent()).isEqualTo(booth.getEvent());
    }

    @Test
    void 부스등록_부스이름이같으면오류발생() {
        //given
        BoothDTO boothDTO1 = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();

        BoothDTO boothDTO2 = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A5)
                .explanation("솜사탕 천원")
                .contents("파란색 솜사탕")
                .event("가위바위보 이기면 솜사탕 오백원")
                .build();

        //when
        Booth booth1 = boothService.save(boothDTO1);

        //then
        // 중복 부스 이름 저장 시 BusinessException이 발생하는지 확인
        BusinessException exception = assertThrows(BusinessException.class, () -> boothService.save(boothDTO2));
        assertEquals(ErrorCode.BOOTH_NAME_DUPLICATION, exception.getErrorCode());
    }

    @Test
    void 모든부스찾기_삭제된부스는나오지않음() {
        //given
        BoothDTO boothDTO1 = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();
        BoothDTO boothDTO2 = BoothDTO.builder()
                .boothName("피자 가게")
                .tentNum(TentNum.A1)
                .explanation("피자 5백원")
                .contents("페페로니 피자")
                .event("이벤트는 추후에")
                .build();
        BoothDTO boothDTO3 = BoothDTO.builder()
                .boothName("치킨 가게")
                .tentNum(TentNum.A1)
                .explanation("치킨 5백원")
                .contents("뿌링클")
                .event("이벤트는 추후에")
                .build();
        BoothDTO boothDTO4 = BoothDTO.builder()
                .boothName("족발 가게")
                .tentNum(TentNum.A1)
                .explanation("족발 5백원")
                .contents("앞다리")
                .event("이벤트는 추후에")
                .build();

        //when
        Booth booth1 = boothService.save(boothDTO1);
        Booth booth2 = boothService.save(boothDTO2);
        Booth booth3 = boothService.save(boothDTO3);
        Booth booth4 = boothService.save(boothDTO4);

        // 하나의 부스 삭제
        boothService.deleteById(booth1.getId());

        //then
        List<BoothDTO> all = boothService.findAll();
        assertThat(3).isEqualTo(all.size());
    }


    @Test
    void 존재하는_부스_상세조회() {
        //given
        BoothDTO expectedBoothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();

        // 주어진 부스를 저장하고 ID를 가져옴
        Booth booth = boothService.save(expectedBoothDTO);
        Long boothId = booth.getId();

        //when
        BoothDTO foundBoothDTO = boothService.findById(boothId);

        //then
        assertThat(foundBoothDTO).isNotNull();
        assertThat(foundBoothDTO.getBoothName()).isEqualTo(expectedBoothDTO.getBoothName());
        assertThat(foundBoothDTO.getTentNum()).isEqualTo(expectedBoothDTO.getTentNum());
        assertThat(foundBoothDTO.getExplanation()).isEqualTo(expectedBoothDTO.getExplanation());
        assertThat(foundBoothDTO.getContents()).isEqualTo(expectedBoothDTO.getContents());
        assertThat(foundBoothDTO.getEvent()).isEqualTo(expectedBoothDTO.getEvent());
    }

    @Test
    void 존재하지않는_부스_상세조회() {
        //given
        BoothDTO expectedBoothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();

        Booth booth = boothService.save(expectedBoothDTO);
        Long boothId = booth.getId();
        boothService.deleteById(boothId);

        //when
        //부스가 삭제된 이력이 있으면 예외 발생
        BusinessException exception = assertThrows(BusinessException.class, () -> boothService.findById(boothId));

        //then
        assertEquals(ErrorCode.BOOTH_ALREADY_DELETED, exception.getErrorCode());
    }

    @Test
    void 부스_정보_수정() {
        // given
        BoothDTO boothDTO = BoothDTO.builder()
                .boothName("수정 전 부스")
                .tentNum(TentNum.A1)
                .explanation("수정 전 설명")
                .contents("수정 전 내용")
                .event("수정 전 이벤트")
                .build();
        Booth existingBooth = boothRepository.save(new Booth(boothDTO.getBoothName(),
                boothDTO.getTentNum(),
                boothDTO.getExplanation(),
                boothDTO.getContents(),
                boothDTO.getEvent()));

        Long boothId = existingBooth.getId();

        // when
        BoothDTO updatedBoothDTO = BoothDTO.builder()
                .boothName("수정 후 부스")
                .tentNum(TentNum.A2)
                .explanation("수정 후 설명")
                .contents("수정 후 내용")
                .event("수정 후 이벤트")
                .build();
        boothService.update(boothId, updatedBoothDTO);

        // then
        Optional<Booth> updatedBoothOptional = boothRepository.findById(boothId);
        assertThat(updatedBoothOptional).isPresent();
        Booth updatedBooth = updatedBoothOptional.get();
        assertThat(updatedBooth.getBoothName()).isEqualTo(updatedBoothDTO.getBoothName());
        assertThat(updatedBooth.getTentNum()).isEqualTo(updatedBoothDTO.getTentNum());
        assertThat(updatedBooth.getExplanation()).isEqualTo(updatedBoothDTO.getExplanation());
        assertThat(updatedBooth.getContents()).isEqualTo(updatedBoothDTO.getContents());
        assertThat(updatedBooth.getEvent()).isEqualTo(updatedBoothDTO.getEvent());
    }

    @Test
    void deleteById() {
        //given
        BoothDTO boothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();
        Booth booth = boothService.save(boothDTO);

        //when
        boothService.deleteById(booth.getId());

        //then
        Optional<Booth> deletedBoothOptional = boothRepository.findById(booth.getId());
        assertThat(deletedBoothOptional).isPresent();
        Booth deletedBooth = deletedBoothOptional.get();
        assertThat(deletedBooth.getDeletedAt()).isNotNull();
    }
}