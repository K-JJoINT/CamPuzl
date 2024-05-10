package com.JJoINT.CamPuzl.domain.pub.controller;

import com.JJoINT.CamPuzl.domain.pub.dto.request.RequestMenuSaveDto;
import com.JJoINT.CamPuzl.domain.pub.dto.request.RequestPubSaveDto;
import com.JJoINT.CamPuzl.domain.pub.dto.response.ResponseMenuListDto;
import com.JJoINT.CamPuzl.domain.pub.dto.response.ResponsePubDetailDto;
import com.JJoINT.CamPuzl.domain.pub.dto.response.ResponsePubListDto;
import com.JJoINT.CamPuzl.domain.pub.service.PubService;
import com.JJoINT.CamPuzl.global.auth.dto.SecurityMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "Pub", description = "주점 API")
@RestController
@RequestMapping("/pubs")
public class PubController {
    private final PubService pubService;

    @Operation(summary = "주점 전체 목록 조회")
    @GetMapping
    public Page<ResponsePubListDto> getPubs() {
        return pubService.getPubs(Pageable.ofSize(10));
    }

    @Operation(summary = "주점 상세 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePubDetailDto> getPubDetail(@PathVariable Long id) {
        ResponsePubDetailDto pubDTO = pubService.getPubDetail(id);
        if (pubDTO != null) {
            return ResponseEntity.ok(pubDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //일단 어드민만 되도록 설정해놓음, 정책 관련해서 생각 좀 해봐야할듯
    @Operation(summary = "주점 저장", description = "주점 저장")
    @PostMapping("/save")
    public ResponseEntity<String> savePub(@RequestBody RequestPubSaveDto requestPubSaveDto, @AuthenticationPrincipal SecurityMemberDTO securityMemberDTO) {
        try {
            Long userId = securityMemberDTO.getId();
            pubService.savePub(requestPubSaveDto, userId);
            return new ResponseEntity<>("주점이 성공적으로 저장되었습니다", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("사용자가 인증되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "주점 메뉴 저장")
    @PostMapping("/{id}/menusave")
    public ResponseEntity<String> saveMenu(@PathVariable Long id, @RequestBody RequestMenuSaveDto menuSaveDto, @AuthenticationPrincipal SecurityMemberDTO securityMemberDTO) {
        pubService.saveMenu(id, menuSaveDto, securityMemberDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("메뉴가 성공적으로 저장되었습니다");
    }

    @Operation(summary = "주점 메뉴 목록 조회")
    @GetMapping("/{id}/menulist")
    public ResponseEntity<Page<ResponseMenuListDto>> getMenuList(@PathVariable Long id, Pageable pageable) {
        Page<ResponseMenuListDto> menuList = pubService.getMenuListByPubId(id, pageable);
        return ResponseEntity.ok(menuList);
    }

    //TODO : 칼럼에 동아리 이미지도 넣을 수 있게 만들기

    @Operation(summary = "주점 정보 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePub(@PathVariable Long id, @RequestBody RequestPubSaveDto requestPubSaveDto, @AuthenticationPrincipal SecurityMemberDTO securityMemberDTO) {
        try {
            pubService.updatePub(id, requestPubSaveDto, securityMemberDTO.getId());
            return ResponseEntity.ok("주점이 성공적으로 수정되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "주점 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePub(@PathVariable Long id, @AuthenticationPrincipal SecurityMemberDTO securityMemberDTO) {
        pubService.deletePub(id, securityMemberDTO.getId());
        return ResponseEntity.ok("주점이 성공적으로 삭제되었습니다");
    }
}
