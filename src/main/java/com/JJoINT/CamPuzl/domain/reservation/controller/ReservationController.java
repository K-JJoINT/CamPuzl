package com.JJoINT.CamPuzl.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final CommonReservationService commonReservationService;

    private final MemberReservationService memberReservationService;
    private final PubReservationService pubReservationService;
}
