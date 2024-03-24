package com.JJoINT.CamPuzl.domain.member.domain;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.enums.Division;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "organization")
public class Organization extends BaseEntity {
    @Column(length = 100,nullable = false)
    private String organizationName;
    @Column(length = 100,nullable = false)
    @Enumerated(EnumType.STRING)
    private Division division;
    @Column(length = 100)
    private String explanation;
    @Column(length = 20,unique = true,nullable = false)
    private String uuid;

    @JoinColumn(columnDefinition = "varchar(100)")
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    private Member representative;

    @JoinColumn(unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Pub pub;
    @JoinColumn(unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Booth booth;

    public void init(Division division, String organizationName, String uuid) {
        this.division = Division.STUDENT;
        this.organizationName = "게스트";
        this.uuid = "asdfaseasefasfaf";
    }


}