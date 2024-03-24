package com.JJoINT.CamPuzl.global.auth.dto;


import com.JJoINT.CamPuzl.global.enums.Division;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class SecurityMemberDTO {
    private final Long studentId;
    @Setter
    private Division division;

    private final String name;

    public static SecurityMemberDTO fromClaims(Claims claims) {
        return SecurityMemberDTO.builder().studentId(Long.valueOf(claims.getId())).name(claims.get("email", String.class)).division(Division.valueOf(claims.get("division", String.class))).build();
    }
}
