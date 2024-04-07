package com.JJoINT.CamPuzl.domain.Comment.dto;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoothCommentDTO {
    private String review;
    private double rating;
    private Member writer;
    private Booth booth;

    public BoothCommentDTO(String review, double rating, Booth booth) {
        this.review = review;
        this.rating = rating;
        this.booth = booth;
    }
}
