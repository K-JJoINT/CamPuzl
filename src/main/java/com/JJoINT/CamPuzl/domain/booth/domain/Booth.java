package com.JJoINT.CamPuzl.domain.booth.domain;

import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booth")
public class Booth extends BaseEntity {

    private String boothName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TentNum tentNum;

    @Min(value = 0)
    @Max(value = 5)
    private double totalRating;
    private String explanation;
    private String contents;
    private String event;

    public Booth(String boothName, TentNum tentNum, String explanation, String contents, String event) {
        this.boothName = boothName;
        this.tentNum = tentNum;
        this.explanation = explanation;
        this.contents = contents;
        this.event = event;
    }


    public void delete() {
        this.setDeletedAt(LocalDateTime.now()); // 부모 클래스의 메서드를 활용하여 deletedAt 업데이트
    }

    public void updateInfo(String boothName, TentNum tentNum, String explanation, String contents, String event) {
        this.boothName = boothName;
        this.tentNum = tentNum;
        this.explanation = explanation;
        this.contents = contents;
        this.event = event;
    }
}
