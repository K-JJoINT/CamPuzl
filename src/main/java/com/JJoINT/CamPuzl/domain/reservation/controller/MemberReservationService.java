package com.JJoINT.CamPuzl.domain.reservation.controller;

import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestListOrderDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestReservePubDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestSelectMenuDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.response.ResponseListOrderDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.response.ResponseReservePubDto;

public interface MemberReservationService {


    //1. 주점 예약하기 -> 주점 매핑,예약자 매핑, 예약 날짜(현재 날짜로 배정), 대기번호 배정,  이미 확정된 예약이 있을 경우 예약 불가
    //요청:주점 아이디 -> dto,반환: 예약한 주점
     ResponseReservePubDto reservePub(RequestReservePubDto requestDto);

    //2. 주문하기 -> 메뉴 아이디 받아와서 오더랑 매핑,멤버조회 후 조회한 멤버로 확정 테이블 조회해서, 최소최대 수량 설정하기
    //요청:메뉴 아이디 -> dto,반환: 주문한 메뉴
    void submitMenuSelection(RequestSelectMenuDto requestDto);



    //3. 주문내역, 총 금액 조회
    //요청: 주문 아이디 ,반환: oder , 총 금액 -> dto
    ResponseListOrderDto listMenuOrderDetails(RequestListOrderDto requestDto);

}
