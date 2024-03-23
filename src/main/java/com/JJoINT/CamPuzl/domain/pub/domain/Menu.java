package com.JJoINT.CamPuzl.domain.pub.domain;

import com.JJoINT.CamPuzl.domain.reservation.domain.DishOrder;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.enums.Division;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "menu")
public class Menu extends BaseEntity {
    @Column(length = 100,nullable = false)
    @Enumerated(EnumType.STRING)
    private Division division;
    @Column(length = 100,nullable = false)
    private String foodName;
    @Column(nullable = false)
    private int price;

    @JoinColumn(columnDefinition = "varchar(100)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;



}
