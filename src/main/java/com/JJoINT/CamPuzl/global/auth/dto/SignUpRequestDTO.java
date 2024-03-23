package com.JJoINT.CamPuzl.global.auth.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDTO {

    private String studentId;
    private String name;
    private String password;

}
