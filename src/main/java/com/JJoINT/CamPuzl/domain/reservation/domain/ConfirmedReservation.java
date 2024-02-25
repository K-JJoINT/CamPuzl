package com.JJoINT.CamPuzl.domain.reservation.domain;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "confirmedReservation")
public class ConfirmedReservation extends BaseEntity {

    private int totalAmount;
    @Column(nullable = false)
    private LocalDate reservationDate;
    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;
    @JoinColumn(columnDefinition = "varchar(20)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reservationName;
    @JsonIgnore
    @OneToMany(mappedBy = "reservationName",fetch = FetchType.LAZY)
    private Set<Order> menus;

}
