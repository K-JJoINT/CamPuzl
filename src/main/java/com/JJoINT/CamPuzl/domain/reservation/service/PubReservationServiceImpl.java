package com.JJoINT.CamPuzl.domain.reservation.service;

import com.JJoINT.CamPuzl.domain.reservation.controller.PubReservationService;
import com.JJoINT.CamPuzl.domain.reservation.repository.ConfirmedReservationRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.OrderRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PubReservationServiceImpl implements PubReservationService {
    private final ConfirmedReservationRepository confirmedReservationRepository;
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;
}
