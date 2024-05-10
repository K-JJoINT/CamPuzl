package com.JJoINT.CamPuzl.domain.Comment.dto;


import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class requestBoothCommentDTO {
    private String review;
    private double rating;
    private Booth booth;
}
