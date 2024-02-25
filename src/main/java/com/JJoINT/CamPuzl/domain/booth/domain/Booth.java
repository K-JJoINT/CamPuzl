package com.JJoINT.CamPuzl.domain.booth.domain;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.member.domain.Organization;
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

import java.util.Set;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booth")
public class Booth extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TentNum tentNum;

    @Min(value = 0)
    @Max(value = 5)
    private double totalRating;
    private String explanation;
    private String contents;
    private String event;
    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    Organization organization;

    @JsonIgnore
    @OneToMany(mappedBy = "booth",fetch = FetchType.LAZY)
    private Set<BoothComment> comment;



}
