package com.JJoINT.CamPuzl.domain.pub.dto.response;

import com.JJoINT.CamPuzl.global.enums.TentNum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
@Getter
public class ResponsePubDetailDto {
    private TentNum tentNum;
    private String pubName;
    private int sales;
    private double totalRating;
    private String event;
}
