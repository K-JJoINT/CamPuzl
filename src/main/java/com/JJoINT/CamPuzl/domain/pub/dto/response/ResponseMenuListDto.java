package com.JJoINT.CamPuzl.domain.pub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMenuListDto {
    private Long id;
    private String division;
    private String foodName;
    private int price;
}
