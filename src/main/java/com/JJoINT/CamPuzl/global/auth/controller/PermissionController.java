package com.JJoINT.CamPuzl.global.auth.controller;


import com.JJoINT.CamPuzl.global.auth.dto.UpgradeRequestDTO;
import com.JJoINT.CamPuzl.global.auth.service.UpgradePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PermissionController {
    private final UpgradePermissionService upgradePermissionService;

    @PostMapping("/permission")
    @Operation(summary = "권한 업그레이드 로직" , description = "학번과 유닛코드 그리고 권한 요청 비밀번호를 입력하면 권한이 업데이트 됩니다")
    public String upgradePermission(@RequestBody UpgradeRequestDTO upgradeRequestDTO) {
        upgradePermissionService.upgradePermission(upgradeRequestDTO);
        return "success";
    }
}
