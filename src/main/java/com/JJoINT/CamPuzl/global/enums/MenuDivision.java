package com.JJoINT.CamPuzl.global.enums;

import lombok.Getter;

@Getter
public enum MenuDivision {
    SNACK("F001","안주"),
    BEVERAGE("F002","음료");

    private final String code ;
    private final String description;

    MenuDivision(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
