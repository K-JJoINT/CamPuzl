package com.JJoINT.CamPuzl.global.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GeneratedTokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;

}
