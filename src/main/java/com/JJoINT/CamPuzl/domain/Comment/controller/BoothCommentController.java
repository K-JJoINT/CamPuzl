package com.JJoINT.CamPuzl.domain.Comment.controller;


import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.requestBoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.service.BoothCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoothCommentController {
    private final BoothCommentService boothCommentService;

    //한줄평 등록
    //TODO 해당 사람이 부스에 참여했는지 판단해야 함
    @Operation(summary = "부스 평점 등록", description = "부스의 한줄평, 점수 그리고 부스를 가져와서 등록합니다")
    @PostMapping("/boothComment/save/{boothId}")
    public String saveBoothComment(@PathVariable("boothId") Long boothId, @RequestBody requestBoothCommentDTO requestDTO) {
        BoothComment boothComment = boothCommentService.save(boothId, requestDTO);
        Long boothCommentID = boothComment.getId();
        return "부스 평점이 정상적으로 등록되었습니다";
    }

    //한줄평 수정
    //TODO 실제로 부스 코멘트를 작성한 사람이어야 함
    @Operation(summary = "부스 평점 수정", description = "부스의 한줄평, 점수 그리고 부스를 가져와서 등록합니다")
    @PatchMapping("/boothComment/update/{boothCommentId}")
    public String updateBoothComment(@PathVariable("boothCommentId") Long boothCommentId, @RequestBody requestBoothCommentDTO requestDTO) {
        BoothComment boothComment = boothCommentService.update(boothCommentId, requestDTO);
        return "부스 평점이 정상적으로 수정되었습니다";
    }

    //한줄평 삭제
    @Operation(summary = "부스 평점 삭제", description = "물리적 삭제를 진행합니다")
    @PostMapping("/boothComment/delete/{boothCommentID}")
    public  String deleteById(@PathVariable("boothCommentID") Long boothCommentID) {
        boothCommentService.deleteById(boothCommentID);
        return "부스 평점이 정상적으로 삭제되었습니다";
    }
}
