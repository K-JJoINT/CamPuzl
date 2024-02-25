package com.JJoINT.CamPuzl.domain.member.domain;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.domain.PubComment;
import com.JJoINT.CamPuzl.domain.reservation.domain.ConfirmedReservation;
import com.JJoINT.CamPuzl.domain.reservation.domain.Reservation;
import com.JJoINT.CamPuzl.global.common.BaseEntity;
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
@Table(name = "member")
public class Member extends BaseEntity {

    @Column(length = 20,nullable = false)
    private String name;
    @Column(length = 9,unique = true,nullable = false)
    private String studentId;

    @JsonIgnore
    @Column(nullable = false)
    @OneToMany(mappedBy = "username",fetch = FetchType.LAZY)
    private Set<MemberAndOrganization> organizations;
    @JsonIgnore
    @OneToMany(mappedBy ="reservationName" ,fetch = FetchType.LAZY)
    private Set<Reservation> reservations;
    @JsonIgnore
    @OneToMany(mappedBy ="reservationName" ,fetch = FetchType.LAZY)
    private Set<ConfirmedReservation> confirmedReservations;

    @JsonIgnore
    @OneToMany(mappedBy = "writer",fetch = FetchType.LAZY)
    private Set<PubComment> pubComments;
    @JsonIgnore
    @OneToMany(mappedBy = "writer",fetch = FetchType.LAZY)
    private Set<BoothComment> boothComments;

}
