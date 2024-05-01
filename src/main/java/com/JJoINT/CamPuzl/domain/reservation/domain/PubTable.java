package com.JJoINT.CamPuzl.domain.reservation.domain;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "confirmedReservation")
public class PubTable extends BaseEntity {

    private int totalAmount;

    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;
    @JoinColumn(columnDefinition = "varchar(20)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reservationName;

}
