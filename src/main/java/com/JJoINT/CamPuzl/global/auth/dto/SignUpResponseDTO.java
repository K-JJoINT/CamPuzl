package com.JJoINT.CamPuzl.global.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignUpResponseDTO {
    private String studentId;
    private String name;


}
