package com.JJoINT.CamPuzl.domain.pub.domain;


import com.JJoINT.CamPuzl.domain.member.domain.Organization;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pub")
public class Pub extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TentNum tentNum;



    //매출
    private int sales;
    @Min(value = 0)
    @Max(value = 5)
    private double totalRating;

    @Column(nullable = false)
    private String pubName;

    private String event;

    private String bootName;





}
