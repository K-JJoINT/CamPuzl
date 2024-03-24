package com.JJoINT.CamPuzl.domain.member.domain;

import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {


    @Column(length = 20,nullable = false)
    private String name;
    @Column(length = 9, unique = true, nullable = false)
    private String studentId;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @JoinColumn(unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private PubTable pubTable;

    @Column
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
