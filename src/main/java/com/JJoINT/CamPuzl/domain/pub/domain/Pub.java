package com.JJoINT.CamPuzl.domain.pub.domain;


import com.JJoINT.CamPuzl.domain.Comment.domain.PubComment;
import com.JJoINT.CamPuzl.domain.member.domain.Organization;
import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import com.JJoINT.CamPuzl.domain.reservation.domain.Reservation;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pub")
public class Pub extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TentNum tentNum;


    private String pubName;

    //매출
    private int sales;
    @Min(value = 0)
    @Max(value = 5)
    private double totalRating;

    private String event;







}
