package com.JJoINT.CamPuzl.domain.reservation.dto.response;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@ToString
@Getter
public class ResponseListOrderDto {

    private String tableNum;
    private int totalAmount;

    private List<MenuDto> menus;

    @Getter
    @Builder
    @ToString
    public static class MenuDto {

        private int quantity;
        private String menu;
        private int price;
    }

}
