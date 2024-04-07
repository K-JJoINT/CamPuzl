package com.JJoINT.CamPuzl.domain.Comment.controller;


import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.service.BoothCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoothCommentController {
    private final BoothCommentService boothCommentService;

    //한줄평 등록
    //TODO 해당 사람이 부스에 참여했는지 판단해야 함(@AuthenticationPrincipal 사용)
    @PostMapping("/booth/{id}/comment/save")
    public ResponseEntity<Long> save(@PathVariable("id") Long boothID, @RequestBody BoothCommentDTO boothCommentDTO) {
        BoothComment boothComment = boothCommentService.save(boothID, boothCommentDTO);
        return new ResponseEntity<>(boothID, HttpStatus.CREATED);
    }

    //한줄평 수정
    @PatchMapping("/booth/{id}/comment")
    //TODO 실제로 부스 코멘트를 작성한 사람이어야 함
    public ResponseEntity<String> updateBoothComment(@PathVariable("id") Long boothID, @RequestBody BoothCommentDTO boothCommentDTO) {
        BoothComment boothComment = boothCommentService.update(boothID, boothCommentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO soft delete/hard delete 고민해봐야 할듯
    //한줄평 삭제
    @PostMapping("/booth/{id}/comment/delete")
    public  ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        boothCommentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
