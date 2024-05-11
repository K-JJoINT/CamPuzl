package com.JJoINT.CamPuzl.domain.pub.dto.request;

import com.JJoINT.CamPuzl.global.enums.Division;
import com.JJoINT.CamPuzl.global.enums.MenuDivision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMenuSaveDto {
    private MenuDivision division;
    private String foodName;
    private int price;
}