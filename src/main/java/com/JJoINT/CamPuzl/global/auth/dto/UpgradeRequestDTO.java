package com.JJoINT.CamPuzl.global.auth.dto;

import lombok.*;

@Getter
@Builder
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UpgradeRequestDTO {
    private final String studentId;
    private final String permissionPassword;
    private final String unitCode;


}
