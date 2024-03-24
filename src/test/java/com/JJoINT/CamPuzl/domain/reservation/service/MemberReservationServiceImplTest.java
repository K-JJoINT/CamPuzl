package com.JJoINT.CamPuzl.domain.reservation.service;
import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.domain.pub.repository.MenuRepository;
import com.JJoINT.CamPuzl.domain.pub.repository.PubRepository;
import com.JJoINT.CamPuzl.domain.reservation.domain.DishOrder;
import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import com.JJoINT.CamPuzl.domain.reservation.domain.Reservation;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestListOrderDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestReservePubDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.request.RequestSelectMenuDto;
import com.JJoINT.CamPuzl.domain.reservation.dto.response.ResponseListOrderDto;
import com.JJoINT.CamPuzl.domain.reservation.repository.OrderRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.PubTableRepository;
import com.JJoINT.CamPuzl.domain.reservation.repository.ReservationRepository;
import com.JJoINT.CamPuzl.global.enums.Division;
import com.JJoINT.CamPuzl.global.enums.TentNum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)//id는 자동증가 옵션으로 @Transactional의 영향을 받지 않아 롤백되지 않고 증가된 상태로 인스턴스가 생성된다 따라서 이 어노테이션을 사용했는데 매서드마다 applicationContext를 만들기 때문에 하나씩 실행하는것과 다를게 없고 실행속도도 느리기 때문에 권장하지 않는다
@Transactional
class MemberReservationServiceImplTest {

    @Autowired
    private MemberReservationServiceImpl memberReservationService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PubRepository pubRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PubTableRepository pubTableRepository;
    @Autowired
    private OrderRepository orderRepository;

    //init()

    @BeforeEach
    void init(){
        //멤버인스턴스2개
        Member member1 = Member.builder().name("최민성").studentId("201904115").build();
        Member member2 = Member.builder().name("김세윤").studentId("202004020").build();

        //주점 인스턴스
        Pub pub = Pub.builder().tentNum(TentNum.A1).totalRating(2.5).build();
        pubRepository.save(pub);
        //메뉴 1개
        Menu menu = Menu.builder().pub(pub).division(Division.BEVERAGE).foodName("참이슬").price(5000).build();
        menuRepository.save(menu);
        //펍테이블 인스턴스 1개 (멤버1이 예약되어 있다)- reservation인스턴스도 같이 하나
        Reservation reservation = Reservation.builder().isConfirmed(true).waitingNum(0).pub(pub).reservationId(member1).build();
        PubTable pubTable = PubTable.builder().tableNum("1").totalAmount(0).pub(pub).build();
        member1.setPubTable(pubTable);
        memberRepository.save(member1);
        memberRepository.save(member2);
        reservationRepository.save(reservation);
        pubTableRepository.save(pubTable);
    }




    //주점 예약 성공
    @Test
    void reservePub() {

        //requestDto 준비
        RequestReservePubDto requestReservePubDto = new RequestReservePubDto(2L,1L);
        //주점예약 메서드 실행
         memberReservationService.reservePub(requestReservePubDto);


        //검증 reservation이 잘 생성되나?
        Reservation reservation = reservationRepository.findById(2L).get();
        assertThat(2L).isEqualTo(reservation.getId());//생성 자체가 되어 있는지
        assertThat(1).isEqualTo(reservation.getWaitingNum());//대기번호가 잘 생성되었는지
        assertThat(1L).isEqualTo(reservation.getPub().getId());//pub이 잘 들어 갔는지
        assertThat(2L).isEqualTo(reservation.getReservationId().getId()); // 멤버2가 잘 들어갔는지
      //  log.info("generated reservation : "+reservation.toString());

    }
    //실패//멤버에 확정 테이블이 있을 경우
    //실패//예약하려는 주점에 이미 예약이 되어 있는 경우


    //메뉴 고르기 성공

    @Test
    void submitMenuSelection() {

        //requestDto 준비
        RequestSelectMenuDto.MenuDto menuDto = new RequestSelectMenuDto.MenuDto(1L,3);
        List<RequestSelectMenuDto.MenuDto> menuDtos = new ArrayList<>();
        menuDtos.add(menuDto);
        RequestSelectMenuDto requestSelectMenuDto = new RequestSelectMenuDto(1L,menuDtos);
        //메뉴 주문 메서드 실행
        memberReservationService.submitMenuSelection(requestSelectMenuDto);

        //메뉴 잘  들어갔는지 로그
        Optional<DishOrder> order = orderRepository.findById(1l);
        System.out.println("order = " + order.get().getMenu().getFoodName());

        //검증 : Oder 가 잘 생성 되었나?
        DishOrder dishOrder = orderRepository.findById(1L).get();
        assertThat(1L).isEqualTo(dishOrder.getId());//생성 자체가 되어 있는지
        assertThat(3).isEqualTo(dishOrder.getQuantity());//수량이 잘 들어 갔는지
        assertThat(1L).isEqualTo(dishOrder.getMenu().getId());//메뉴가 잘 들어 갔는지
        assertThat(1L).isEqualTo(dishOrder.getPubTable().getId());//주문 테이블이 잘 들어 갔는지
       // log.info("generated order" + dishOrder.toString());


    }
    //성공//이미 주문한 동일 메뉴로의 주문이 있을 경우
    //실패//아직 예약이 확정되지 않은 경우
    //실패//


    //메뉴 정보

    @Test
    void listMenuOrderDetails() {

        //requestDto 준비
        RequestListOrderDto requestListOrderDto = new RequestListOrderDto(1L);
        //메뉴 주문
            //requestDto 준비
            RequestSelectMenuDto.MenuDto menuDto = new RequestSelectMenuDto.MenuDto(1L,3);
            List< RequestSelectMenuDto.MenuDto> menuDtos = new ArrayList<>();
            menuDtos.add(menuDto);
            RequestSelectMenuDto requestSelectMenuDto = new RequestSelectMenuDto(1L,menuDtos);
            //메뉴 주문 메서드 실행
            memberReservationService.submitMenuSelection(requestSelectMenuDto);


        //주문 내역보기 서비스 실행
        ResponseListOrderDto responseListOrderDto = memberReservationService.listMenuOrderDetails(requestListOrderDto);

        //검증 responseDto가 잘 생성되나?
        assertThat("1").isEqualTo(responseListOrderDto.getTableNum());//테이블 번호가 잘 들어갔는지
        ResponseListOrderDto.MenuDto menu = responseListOrderDto.getMenus().iterator().next();
        assertThat(menu.getMenu()).isEqualTo("참이슬");// 메뉴 잘 들어 갔는지
       // log.info("generated list" + responseListOrderDto);
    }
}