package com.JJoINT.CamPuzl.global.auth.controller;


import com.JJoINT.CamPuzl.global.auth.dto.UpgradeRequestDTO;
import com.JJoINT.CamPuzl.global.auth.service.UpgradePermissionService;
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
    public String upgradePermission(@RequestBody UpgradeRequestDTO upgradeRequestDTO) {
        upgradePermissionService.upgradePermission(upgradeRequestDTO);
        return "success";
    }
}
