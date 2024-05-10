package com.JJoINT.CamPuzl.domain.Comment.dto;


import com.JJoINT.CamPuzl.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PubCommentResponseDTO {
    private Long id;
    private String review;
    private double rating;
    private Member writer;
}