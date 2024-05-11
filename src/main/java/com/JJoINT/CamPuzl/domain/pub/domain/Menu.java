package com.JJoINT.CamPuzl.domain.pub.domain;

import com.JJoINT.CamPuzl.global.common.BaseEntity;
import com.JJoINT.CamPuzl.global.enums.MenuDivision;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu")
public class Menu extends BaseEntity {
    @Column(length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuDivision division;
    @Column(length = 100,nullable = false)
    private String foodName;
    @Column(nullable = false)
    private int price;

    @JoinColumn(columnDefinition = "varchar(100)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;

}
