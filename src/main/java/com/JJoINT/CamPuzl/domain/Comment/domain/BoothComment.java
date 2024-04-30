package com.JJoINT.CamPuzl.domain.Comment.domain;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
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
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booth_comment")
public class BoothComment extends BaseEntity {
    //커밋용 주석
    private String review;
    @MultipleOfHalf
    @Min(value = 0)
    @Max(value = 5)
    @Column(nullable = false)
    private double rating;
//    @JoinColumn(columnDefinition = "varchar(20)",nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member writer;
    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Booth booth;

    public BoothComment(String review, double rating, Booth booth) {
        this.review = review;
        this.rating = rating;
        this.booth = booth;
    }

    public void updateInfo(String review, double rating) {
        this.review = review;
        this.rating = rating;
    }
}
