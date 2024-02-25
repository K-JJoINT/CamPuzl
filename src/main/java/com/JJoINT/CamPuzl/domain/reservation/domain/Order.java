package com.JJoINT.CamPuzl.domain.reservation.domain;

import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order")
public class Order extends BaseEntity {
    @JoinColumn(columnDefinition = "varchar(100)",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ConfirmedReservation reservationName;

}
