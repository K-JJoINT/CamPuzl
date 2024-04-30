package com.JJoINT.CamPuzl.domain.booth.controller;

import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.service.BoothCommentService;
import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothWithCommentDTO;
import com.JJoINT.CamPuzl.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoothController {

    private final BoothService boothService;
    private final BoothCommentService boothCommentService;

    // 부스 등록
    @PostMapping("/booth/save")
    public ResponseEntity<Long> save(@RequestBody BoothDTO boothDTO) {
        Booth savedBooth = boothService.save(boothDTO);
        Long boothID = savedBooth.getId();
        return new ResponseEntity<>(boothID, HttpStatus.CREATED);
    }

    //부스 전체 목록 조회
    @GetMapping("/booth")
    public ResponseEntity<List<BoothDTO>> findAll(){
        List<BoothDTO> booths = boothService.findAll();
        return new ResponseEntity<>(booths, HttpStatus.OK);
    }

    //부스 상세 조회(부스 코멘트, 멤버 조회?)
    @GetMapping("/booth/{boothID}")
    public ResponseEntity<BoothWithCommentDTO> findById(@PathVariable("boothID") Long boothID) {
        BoothDTO boothDTO = boothService.findById(boothID);
        ArrayList<BoothCommentDTO> listBoothCommentDTO = boothCommentService.findByIdAll(boothID);

        BoothWithCommentDTO boothWithCommentDTO = new BoothWithCommentDTO(boothDTO, listBoothCommentDTO);

        return new ResponseEntity<>(boothWithCommentDTO, HttpStatus.OK);
    }

    //부스 정보 수정
    @PatchMapping("/booth/{boothID}")
    public ResponseEntity<String> updateBooth(@PathVariable("boothID") Long boothID, @RequestBody BoothDTO boothDTO){
        Booth booth = boothService.update(boothID, boothDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //부스 논리적 삭제
    //TODO 헤더로 넘어온 멤버 정보 == 부스 작성자 -> 삭제
    @PostMapping("/booth/{boothID}")
    public ResponseEntity<BoothDTO> deleteById(@PathVariable("boothID") Long id) {
        boothService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
