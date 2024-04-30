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
    //커밋용 주석
    private final BoothCommentService boothCommentService;

    //한줄평 등록
    //TODO 해당 사람이 부스에 참여했는지 판단해야 함(@AuthenticationPrincipal 사용)
    @PostMapping("/booth/comment")
    public ResponseEntity<Long> saveBoothComment(@RequestBody BoothCommentDTO boothCommentDTO) {
        BoothComment boothComment = boothCommentService.save(boothCommentDTO);
        Long boothCommentID = boothComment.getId();
        return new ResponseEntity<>(boothCommentID, HttpStatus.CREATED);
    }

    //한줄평 수정
    @PatchMapping("/booth/comment/{boothCommentID}")
    //TODO 실제로 부스 코멘트를 작성한 사람이어야 함
    public ResponseEntity<String> updateBoothComment(@RequestBody BoothCommentDTO boothCommentDTO) {
        BoothComment boothComment = boothCommentService.update(boothCommentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //한줄평 삭제
    @PostMapping("/booth/comment/{boothCommentID}")
    public  ResponseEntity<String> deleteById(@PathVariable("boothCommentID") Long boothCommentID) {
        boothCommentService.deleteById(boothCommentID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
