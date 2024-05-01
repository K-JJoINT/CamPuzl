package com.JJoINT.CamPuzl.global.auth.dto;


import com.JJoINT.CamPuzl.global.enums.Role;
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
    private Role role;

    private final String name;

    public static SecurityMemberDTO fromClaims(Claims claims) {
        return SecurityMemberDTO.builder().studentId(Long.valueOf(claims.getId())).name(claims.get("name", String.class)).role(Role.valueOf(claims.get("role", String.class))).build();
    }
}
