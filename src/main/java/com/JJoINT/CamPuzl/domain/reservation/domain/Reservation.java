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
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Column(nullable = false)
    private boolean isConfirmed;
    @Column(nullable = false)
    private LocalDate reservationDate;
    private int waitingNum;
    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;
    @JoinColumn(columnDefinition = "varchar(20)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reservationName;
}
