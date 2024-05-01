package com.JJoINT.CamPuzl.domain.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@AllArgsConstructor
public class RequestReservePubDto {


    private Long memberId;


    private Long pubId;
}
