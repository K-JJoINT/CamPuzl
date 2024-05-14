package com.JJoINT.CamPuzl.domain.booth.controller;

import com.JJoINT.CamPuzl.domain.Comment.dto.ResponseBoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.service.BoothCommentService;
import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothWithCommentDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.request.SaveRequestBoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.request.UpdateRequestBoothDTO;
import com.JJoINT.CamPuzl.domain.booth.service.BoothService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Tag(name = "Booth", description = "부스 API")
@RestController
@RequiredArgsConstructor
public class BoothController {

    private final BoothService boothService;
    private final BoothCommentService boothCommentService;

    // 부스 등록
    @Operation(summary = "부스 등록" , description = "부스 이름, 텐트 번호, 설명, 콘텐츠, 이벤트를 사용하여 부스 등록")
    @PostMapping("booth/save")
    public String save(@RequestBody SaveRequestBoothDTO saveRequestBoothDTO) {
        Booth savedBooth = boothService.save(saveRequestBoothDTO);
        return "부스가 정상적으로 등록되었습니다";
    }

    //부스 전체 목록 조회
    @Operation(summary = "부스 전체 목록 조회", description = "한 부스의 부스 이름, 텐트 번호, 총 평점, 설명, 콘텐츠, 이벤트의 정보 리턴")
    @GetMapping("/booth")
    public List<BoothDTO> findAll(){
        return boothService.findAll();
    }

    //부스 상세 조회(부스 코멘트, 멤버 조회?)
    @Operation(summary = "각 부스별 상세 정보 조회", description = "부스 이름, 총 평점, 텐트 번호, 설명, 콘텐츠, 이벤트, 사용자의 리뷰, 사용자의 점수, 사용자의 이름의 정보 리턴")
    @GetMapping("booth/{boothId}")
    public BoothWithCommentDTO findById(@PathVariable("boothId") Long boothId) {
        BoothDTO boothDTO = boothService.findById(boothId);
        ArrayList<ResponseBoothCommentDTO> listBoothCommentDTO = boothCommentService.findByIdAll(boothId);

        return new BoothWithCommentDTO(boothDTO, listBoothCommentDTO);
    }

    //부스 정보 수정
    @Operation(summary = "부스 정보 수정", description = "부스 이름, 텐트 번호, 설명, 콘텐츠, 이벤트를 수정")
    @PatchMapping("booth/{boothId}")
    public String updateBooth(@PathVariable("boothId") Long boothID, @RequestBody UpdateRequestBoothDTO requestDTO){
        Booth booth = boothService.update(boothID, requestDTO);
        return "부스의 정보가 정상적으로 수정되었습니다";
    }

    //부스 논리적 삭제
    //TODO 헤더로 넘어온 멤버 정보 == 부스 작성자 -> 삭제
    @Operation(summary = "부스 논리적 삭제", description = "논리적 삭제 진행")
    @PostMapping("booth/{boothID}")
    public String deleteById(@PathVariable("boothID") Long boothId) {
        boothService.deleteById(boothId);
        boothCommentService.deletedByIdAll(boothId);
        return "부스가 정상적으로 삭제되었습니다";
    }
}
