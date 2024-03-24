package com.JJoINT.CamPuzl.domain.reservation.service;

import com.JJoINT.CamPuzl.domain.reservation.controller.PubReservationService;
import com.JJoINT.CamPuzl.domain.reservation.repository.PubTableRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.OrderRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PubReservationServiceImpl implements PubReservationService {
    private final PubTableRepository pubTableRepository;
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;

    //reservation의 isConfirmed를 true로 바꾸기

    //TODO: 예약 확정시 해당 사용자의 다른 모든 예약을 취소 해야함
    @Override
    public void confirmReservation() {

    }

    @Override
    public void listTableMenuOrders() {

    }

    @Override
    public void confirmCooking() {

    }

    //TODO:테이블과 멤버에서 서로를 삭제해 줘야함
    //TODO:reservation의 isConfirmed를 false로 바꾸기
    @Override
    public void completePayment() {

    }

    //TODO:메뉴 상태 변경 기능 만들기, 예를들어 재료 소진으로 상태가 변경되었다면 메뉴 보여주는 곳에 상태가 업데이트 하고 주문시 예외 처리

}
