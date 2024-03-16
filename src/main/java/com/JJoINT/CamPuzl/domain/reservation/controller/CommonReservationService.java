package com.JJoINT.CamPuzl.domain.reservation.controller;


public interface CommonReservationService {
    //1. 예약 취소하기 -> 확정 예약 있는지 확인하고 있다면 둘 다 삭제, 취소 사유 보내도록 하기
    //요청:   반환:

    //2. 취소사유 보내기 -> pub이 취소하면 member 한테, member가 취소하면 pub한테, 조리 확정 있으면 취소 불가
    //요청: 반환:

    //3. 계산 완료 업데이트 -> pub이 계산완료 요청을 보내면 계산 완료 컬럼 업데이트
    //요청: 반환:

}
