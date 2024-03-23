package com.JJoINT.CamPuzl.domain.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class RequestSelectMenuDto {


    private Long memberId;

    private List<MenuDto> menus;

    @Getter
    @AllArgsConstructor
    public static class MenuDto{
        private Long menuId;
        private int quantity;
    }

}
