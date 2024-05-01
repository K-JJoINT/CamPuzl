package com.JJoINT.CamPuzl.global.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDTO {
    @NotBlank
    private String studentId;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

}
