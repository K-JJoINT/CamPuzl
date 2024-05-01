package com.JJoINT.CamPuzl.domain.reservation.domain;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@SuperBuilder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Column(nullable = false)
    private boolean isConfirmed;
    private int waitingNum;
    @JoinColumn(columnDefinition = "varchar(100)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pub pub;
    @JoinColumn(columnDefinition = "varchar(20)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reservationId;
    //이건 생성자를 써도 무방함, 나는 다른 메서드이름과 초기값을 지정하려고 사용함
    public static Reservation reserve(int waitingNum, Pub pub, Member reservationName){
        Reservation reservation = new Reservation();
        reservation.isConfirmed = false;
        reservation.waitingNum = waitingNum;
        reservation.pub = pub;
        reservation.reservationId = reservationName;
        return reservation;
    }

}
