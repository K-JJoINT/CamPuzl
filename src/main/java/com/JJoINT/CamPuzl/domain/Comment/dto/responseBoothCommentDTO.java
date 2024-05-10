package com.JJoINT.CamPuzl.domain.Comment.dto;

import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class responseBoothCommentDTO {
    private String review;
    private double rating;
    private String writer;
}
