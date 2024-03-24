package com.JJoINT.CamPuzl.domain.member.domain;

import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Setter//세터 테스트에서만 사용
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Column(length = 20,nullable = false)
    private String name;
    @Column(length = 9, unique = true, nullable = false)
    private String studentId;

    @JsonIgnore
    @JoinColumn(unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private PubTable pubTable;


}
