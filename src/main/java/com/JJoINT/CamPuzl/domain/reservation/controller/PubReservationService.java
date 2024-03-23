package com.JJoINT.CamPuzl.domain.reservation.controller;

public interface PubReservationService {
    //1.예약 확정하기 -> 예약 확정 페이지로 데이터 복사, 테이블 배정,
    void confirmReservation();

    //2.주문 조회 -> 테이블 별로 주문 구분, 주문내역, 총 금액 조회 ,반환타입 컬렉션 및 페이징 처리
    void listTableMenuOrders();

    //3.조리 확정
    void confirmCooking();

    //4.계산완료
    //TODO: 일단 한번에 계산하도록 만들고 나중에 회의를 통해 주문마다 계산하는 등의 정책 정하기
    void completePayment();

}
