package com.JJoINT.CamPuzl.domain.reservation.controller;


public interface CommonReservationService {
    //1. 예약 취소하기 -> 확정 예약 있는지 확인하고 있다면 둘 다 삭제, 취소 사유 보내도록 하기
    void cancelReservation();

    //2. 주문 취소 -> 조리 확정시 취소 불가, 취소 사유 보내도록 하기
    //요청: 주문 아이디 ,반환: void
    void cancelMenuSelection();


}
