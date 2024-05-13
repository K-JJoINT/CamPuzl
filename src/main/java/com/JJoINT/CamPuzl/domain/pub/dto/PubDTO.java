package com.JJoINT.CamPuzl.domain.pub.dto;

import com.JJoINT.CamPuzl.domain.member.domain.Organization;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Setter
@NoArgsConstructor
@SuperBuilder
public class PubDTO {
    private Long id;
    private TentNum tentNum;
    private String pubName;
    private int sales;
    private double totalRating;
    private String event;

    // 엔티티를 DTO로 변환하는 메서드
    public static PubDTO fromEntity(Pub pub) {
        return PubDTO.builder()
                .id(pub.getId())
                .tentNum(pub.getTentNum())
                .sales(pub.getSales())
                .totalRating(pub.getTotalRating())
                .event(pub.getEvent())
                .build();
    }

    // DTO를 엔티티로 변환하는 메서드
    public Pub toEntity() {
        return Pub.builder()
                .id(this.id)
                .tentNum(this.tentNum)
                .pubName(this.pubName)
                .sales(this.sales)
                .totalRating(this.totalRating)
                .event(this.event)
                .build();
    }
}
