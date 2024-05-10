package com.JJoINT.CamPuzl.domain.booth.dto;


import com.JJoINT.CamPuzl.global.enums.TentNum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class updateRequestBoothDTO {
    private String boothName;
    private TentNum tentNum;
    private String explanation;
    private String contents;
    private String event;
}
