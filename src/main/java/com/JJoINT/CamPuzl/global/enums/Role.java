package com.JJoINT.CamPuzl.global.enums;

import lombok.Getter;

@Getter
public enum Role {
    STUDENT_COUNCIL_MANAGER("001","학생회 관리자"),
    CLUB_MANAGER("002","동아리 관리자"),
    GENERAL_STUDENT_COUNCIL("O003","총학생회"),
    STUDENT("004", "일반 학생"),
    ADMIN("005","총관리자");

    private final String code ;
    private final String description;

    Role(String code,  String description) {
        this.code = code;
        this.description = description;
    }
}
