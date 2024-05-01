package com.JJoINT.CamPuzl.global.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class LoginRequestDTO {
    @NotBlank
    private final String studentId;
    @NotBlank
    private final String password;


}
