package com.JJoINT.CamPuzl.domain.pub.dto.request;

import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RequestPubSaveDto {
    private TentNum tentNum;
    private String pubName;
    private int sales;
    private double totalRating;
    private String event;
}
