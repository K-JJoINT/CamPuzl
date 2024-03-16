package com.JJoINT.CamPuzl.domain.reservation.service;

import com.JJoINT.CamPuzl.domain.reservation.controller.MemberReservationService;
import com.JJoINT.CamPuzl.domain.reservation.repository.ConfirmedReservationRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.OrderRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReservationServiceImpl implements MemberReservationService {
    private final ConfirmedReservationRepository confirmedReservationRepository;
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;
}
