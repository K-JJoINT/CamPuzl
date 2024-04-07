package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.repository.BoothCommentRepository;
import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.repository.BoothRepository;
import com.JJoINT.CamPuzl.domain.booth.service.BoothServiceImpl;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoothCommentServiceImplTest {

    @Autowired
    private BoothCommentRepository boothCommentRepository;
    @Autowired
    private BoothRepository boothRepository;
    @Autowired
    private BoothCommentServiceImpl boothCommentService;
    @Autowired
    private BoothServiceImpl boothService;

    @Test
    void 부스_코멘트_등록() {
        //given
        BoothDTO boothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();
        Booth booth = boothService.save(boothDTO);
        Long boothId = booth.getId();

        BoothCommentDTO boothCommentDTO = new BoothCommentDTO("오 맛있어요", 4.5, booth);

        //when
        BoothComment savedBoothComment = boothCommentService.save(boothId, boothCommentDTO);


        //then
        assertThat(savedBoothComment.getBooth().getBoothName()).isEqualTo(boothDTO.getBoothName());
        assertThat(savedBoothComment.getBooth().getTentNum()).isEqualTo(boothDTO.getTentNum());
        assertThat(savedBoothComment.getBooth().getExplanation()).isEqualTo(boothDTO.getExplanation());
        assertThat(savedBoothComment.getBooth().getContents()).isEqualTo(boothDTO.getContents());
        assertThat(savedBoothComment.getBooth().getEvent()).isEqualTo(boothDTO.getEvent());
    }

    @Test
    void 부스_코멘트_모두_찾기() {
        //given
        BoothDTO boothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();
        Booth booth = boothService.save(boothDTO);
        Long boothId = booth.getId();

        BoothCommentDTO boothCommentDTO1 = new BoothCommentDTO("오 맛있어요", 4.5, booth);
        BoothCommentDTO boothCommentDTO2 = new BoothCommentDTO("오 맛있어요", 4.5, booth);
        BoothCommentDTO boothCommentDTO3 = new BoothCommentDTO("오 맛있어요", 4.5, booth);
        BoothCommentDTO boothCommentDTO4 = new BoothCommentDTO("오 맛있어요", 4.5, booth);

        boothCommentService.save(boothId, boothCommentDTO1);
        boothCommentService.save(boothId, boothCommentDTO2);
        boothCommentService.save(boothId, boothCommentDTO3);
        boothCommentService.save(boothId, boothCommentDTO4);

        Assertions.assertEquals(4, boothCommentService.findByIdAll(booth.getId()).size());
    }

    @Test
    void 부스_코멘트_수정() {
        //given
        BoothDTO boothDTO = BoothDTO.builder()
                .boothName("솜사탕 가게")
                .tentNum(TentNum.A1)
                .explanation("솜사탕 5백원")
                .contents("무지개 솜사탕")
                .event("이벤트는 추후에")
                .build();
        Booth booth = boothService.save(boothDTO);
        Long boothId = booth.getId();

        BoothCommentDTO existingBoothComment = new BoothCommentDTO("오 맛있어요", 4.5, booth);
        boothCommentService.save(boothId, existingBoothComment);

        //when
        BoothCommentDTO updatedBoothComment = new BoothCommentDTO("노맛", 1, booth);

        //then
        ArrayList<BoothCommentDTO> savedComments = boothCommentService.findByIdAll(boothId);

        // 부스에 저장된 코멘트가 존재하는지 확인
        Assertions.assertFalse(savedComments.isEmpty());

        // 부스에 저장된 코멘트와 updatedBoothComment의 속성들을 비교
        for (BoothCommentDTO savedComment : savedComments) {
            if (savedComment.getReview().equals(updatedBoothComment.getReview())) {
                Assertions.assertEquals(savedComment.getRating(), updatedBoothComment.getRating());
            }
        }
    }

    @Test
    void deleteById() {

    }
}