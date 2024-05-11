package com.JJoINT.CamPuzl.domain.pub.dto;

import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.global.enums.MenuDivision;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class MenuDTO {
    private Long id;
    private MenuDivision division;
    private String foodName;
    private int price;

    // 엔티티를 DTO로 변환하는 메서드
    public static MenuDTO fromEntity(Menu menu) {
        return MenuDTO.builder()
                .id(menu.getId())
                .division(menu.getDivision())
                .foodName(menu.getFoodName())
                .price(menu.getPrice())
                .build();
    }

    // DTO를 엔티티로 변환하는 메서드
    public Menu toEntity() {
        return Menu.builder()
                .id(this.id)
                .division(this.division)
                .foodName(this.foodName)
                .price(this.price)
                .build();
    }
}