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
public class ResponsePubListDto {

    private TentNum tentNum;
    private String pubName;
    private double totalRating;
}
