package com.JJoINT.CamPuzl.domain.reservation.service;

import com.JJoINT.CamPuzl.domain.reservation.controller.CommonReservationService;
import com.JJoINT.CamPuzl.domain.reservation.repository.PubTableRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.OrderRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonReservationServiceImpl implements CommonReservationService {
    private final PubTableRepository pubTableRepository;
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;

    @Override
    public void cancelReservation() {

    }

    @Override
    public void cancelMenuSelection() {

    }

    //TODO:메뉴 보여주는 기능


    //TODO:대기번호 최신화 하는 로직 필요,예약취소나 예약 확정 발생하면 대기번호 최신화

}
