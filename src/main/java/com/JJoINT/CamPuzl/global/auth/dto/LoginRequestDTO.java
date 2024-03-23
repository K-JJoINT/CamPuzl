package com.JJoINT.CamPuzl.global.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class LoginRequestDTO {
    private final String studentId;
    private final String password;


}
