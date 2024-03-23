package com.JJoINT.CamPuzl.domain.reservation.service;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.domain.pub.repository.MenuRepository;
import com.JJoINT.CamPuzl.domain.pub.repository.PubRepository;
import com.JJoINT.CamPuzl.domain.reservation.controller.MemberReservationService;
import com.JJoINT.CamPuzl.domain.reservation.domain.DishOrder;
import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import com.JJoINT.CamPuzl.domain.reservation.domain.Reservation;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestListOrderDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestReservePubDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestSelectMenuDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.response.ResponseListOrderDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.response.ResponseReservePubDto;;
import com.JJoINT.CamPuzl.domain.reservation.repository.OrderRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.ReservationRepository;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//TODO: 멤버 정보 지금은 dto에서 받아오는걸로 되어 있지만 토큰 로직 완성되면 tockenProvider에서 받아와야 함
@Service
@RequiredArgsConstructor
public class MemberReservationServiceImpl implements MemberReservationService {

    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final PubRepository pubRepository;
    private final MenuRepository menuRepository;


    @Override
    @Transactional
    public ResponseReservePubDto reservePub(RequestReservePubDto requestDto) {
        Optional<Member> optionalMember = memberRepository.findById(requestDto.getMemberId());
        if(!optionalMember.isPresent()){
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //멤버
        Member member = optionalMember.get();

        //TODO: 이미 해당 주점에 예약이 있으면 예약을 못하게 해야 함
        //멤버에서 확정된 예약(테이블) 있는지 조회하고 있다면 예외 발생
        if(member.getPubTable() != null){
            throw new BusinessException(ErrorCode.ALREADY_EXIST_OTHER_PUPTABLE);
        }
        Optional<Pub> optionalPub = pubRepository.findById(requestDto.getPubId());
        if(!optionalPub.isPresent()){
            throw new BusinessException(ErrorCode.PUB_NOT_FOUND);
        }
        //주점
        Pub pub = optionalPub.get();
        //대기번호
        int waitingNum = reservationRepository.findLastWaitingNum(pub.getId()) + 1;
        Reservation reservation = Reservation.reserve(waitingNum,pub,member);


        reservationRepository.save(reservation);
        //대기번호 반환
        return new ResponseReservePubDto(waitingNum);
    }

    @Override
    @Transactional
    public void submitMenuSelection(RequestSelectMenuDto requestDto) {
        //멤버로아이디 받아와서 멤버로 테이블 조회
        Optional<Member> optionalMember = memberRepository.findById(requestDto.getMemberId());
        if(!optionalMember.isPresent()){
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //멤버
        Member member = optionalMember.get();
        PubTable pubTable = member.getPubTable();
        if(pubTable == null){
            throw new BusinessException(ErrorCode.NOT_YET_RESERVED);
        }

        for(RequestSelectMenuDto.MenuDto menuDto: requestDto.getMenus()){
            Optional<Menu> optionalMenu = menuRepository.findById(menuDto.getMenuId());
            if(!optionalMenu.isPresent()){
                throw new BusinessException(ErrorCode.MENU_NOT_FOUND);
            }
            Menu menu = optionalMenu.get();
            Optional<DishOrder> optionalOrder = orderRepository.findByMenuAndPubTable(menu,pubTable);
            //TODO : 같은 아이디의 메뉴가 두 개 이상 들어온다면 컨트롤러에서 예외 발생
            //한번의 요청의 같은 메뉴가 오지 않으니깐 트랜잭션을 한번에 넘겨도 된다.
            if (!optionalOrder.isPresent()){
                //메뉴랑 펍테이블도 추가로 지정 해줘야 하는가?
                DishOrder dishOrder = DishOrder.order(menuDto.getQuantity(), menu, pubTable);
                orderRepository.save(dishOrder);

            }
            else {
                DishOrder dishOrder = optionalOrder.get().updateQuantity(menuDto.getQuantity());
                orderRepository.save(dishOrder);
            }

        }
        //TODO:여기서 이벤트를 발생시켜서 주문 내역을 pub에 보내주면 좋겠다. 나중에 구현하는걸로 하고 일단은 새로고침으로....
    }


    @Override
    public ResponseListOrderDto listMenuOrderDetails(RequestListOrderDto requestDto) {
        //필요한거 멤버아이디, 펍 테이블

        //멤버로아이디 받아와서 멤버로 테이블 조회
        Optional<Member> optionalMember = memberRepository.findById(requestDto.getMemberId());
        if(!optionalMember.isPresent()){
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //멤버
        Member member = optionalMember.get();
        //펍 테이블
        PubTable pubTable = member.getPubTable();
        if(pubTable == null){
            throw new BusinessException(ErrorCode.NOT_YET_RESERVED);
        }
        //dishorders
        List<DishOrder> dishOrders = orderRepository.findByPubTable(pubTable);

        //TODO: 컨트롤러에서 오더 조회하고 없다면 일단 null넘기고 컨트롤러에서 response에 주문이 없다는 메시지
        List<ResponseListOrderDto.MenuDto> menus = new ArrayList<>();
        dishOrders.forEach(order -> {
            ResponseListOrderDto.MenuDto menu = ResponseListOrderDto.MenuDto.builder()
                    .menu(order.getMenu().getFoodName())
                    .price(order.getMenu().getPrice())
                    .quantity(order.getQuantity())
                    .build();

            menus.add(menu);
        });

        ResponseListOrderDto responseDto = ResponseListOrderDto.builder()
                .tableNum(pubTable.getTableNum())
                .totalAmount(pubTable.getTotalAmount())
                .menus(menus)
                .build();

        return responseDto;

    }
}
