package com.JJoINT.CamPuzl.domain.Comment.dto;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoothCommentDTO {
    private String review;
    private double rating;
    private Member writer;
    private Booth booth;

    //TODO 부스 코멘트를 찾을 때 사용하는 dto, 현재 Member는 사용하지 않아 생성자에 Member를 표함하지 않았지만 향후에 추가할 예정
    public BoothCommentDTO(String review, double rating, Booth booth) {
        this.review = review;
        this.rating = rating;
        this.booth = booth;
    }
}
