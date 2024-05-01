package com.JJoINT.CamPuzl.global.auth.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDetailsDTO {
    private String studentId;
    private String password;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
}
