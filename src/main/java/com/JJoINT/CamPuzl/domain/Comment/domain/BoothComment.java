package com.JJoINT.CamPuzl.domain.Comment.domain;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.validator.MultipleOfHalf;
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
@Table(name = "booth_comment")
public class BoothComment extends BaseEntity {
    private String review;
    @MultipleOfHalf
    @Min(value = 0)
    @Max(value = 5)
    @Column(nullable = false)
    private double rating;
    @JoinColumn(columnDefinition = "varchar(20)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
    @JoinColumn(columnDefinition = "varchar(100)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Booth booth;

}
