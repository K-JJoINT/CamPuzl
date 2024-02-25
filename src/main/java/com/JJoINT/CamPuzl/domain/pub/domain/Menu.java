package com.JJoINT.CamPuzl.domain.pub.domain;

import com.JJoINT.CamPuzl.domain.reservation.domain.Order;
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
    private Division food;
    @Column(length = 100)
    private String division;
    @Column(nullable = false)
    private int price;
    @JsonIgnore
    @OneToMany(mappedBy = "menu",fetch = FetchType.LAZY)
    private Set<Order> orders;

}
