package com.JJoINT.CamPuzl.domain.reservation.domain;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pub_table")
public class PubTable extends BaseEntity {

    @Column(nullable = false)
    private String tableNum;

    @Min(0)
    private int totalAmount;

    @JoinColumn(columnDefinition = "varchar(100)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;
}
