package com.JJoINT.CamPuzl.domain.Comment.domain;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.validator.MultipleOfHalf;
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
@Table(name = "pub_comment")
public class PubComment extends BaseEntity {
    private String review;

    @Min(value = 0)
    @Max(value = 5)
    @MultipleOfHalf
    @Column(nullable = false)
    private double rating;
    @JoinColumn(columnDefinition = "varchar(20)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;

}
