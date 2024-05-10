package com.JJoINT.CamPuzl.domain.pub.service;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.member.domain.Organization;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.domain.member.repository.OrganizationRepository;
import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.domain.pub.domain.Pub;
import com.JJoINT.CamPuzl.domain.pub.dto.PubDTO;
import com.JJoINT.CamPuzl.domain.pub.dto.request.RequestMenuSaveDto;
import com.JJoINT.CamPuzl.domain.pub.dto.request.RequestPubSaveDto;
import com.JJoINT.CamPuzl.domain.pub.dto.response.ResponseMenuListDto;
import com.JJoINT.CamPuzl.domain.pub.dto.response.ResponsePubDetailDto;
import com.JJoINT.CamPuzl.domain.pub.dto.response.ResponsePubListDto;
import com.JJoINT.CamPuzl.domain.pub.repository.MenuRepository;
import com.JJoINT.CamPuzl.domain.pub.repository.PubRepository;
import com.JJoINT.CamPuzl.global.auth.jwt.JwtProvider;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.enums.MenuDivision;
import com.JJoINT.CamPuzl.global.enums.Role;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PubService {
    private final MemberRepository memberRepository;
    private final PubRepository pubRepository;
    private final OrganizationRepository organizationRepository;
    private final JwtProvider jwtProvider;
    private MenuRepository menuRepository;

    public PubService(MemberRepository memberRepository, PubRepository pubRepository, OrganizationRepository organizationRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.pubRepository = pubRepository;
        this.organizationRepository = organizationRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void savePub(RequestPubSaveDto requestPubSaveDto, Long memberId) {

        // memberId를 이용하여 사용자의 역할(Role)을 조회
        Role role = memberRepository.findRoleById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // Only allow execution if the role is ADMIN
        if (role != Role.ADMIN) {
            throw new BusinessException(ErrorCode.NO_PERMISSION);
        }

        // DTO를 엔티티로 변환
        Pub pub = Pub.builder()
                .tentNum(requestPubSaveDto.getTentNum())
                .pubName(requestPubSaveDto.getPubName())
                .sales(requestPubSaveDto.getSales())
                .totalRating(requestPubSaveDto.getTotalRating())
                .event(requestPubSaveDto.getEvent())
                .build();
        // 엔티티 저장
        pubRepository.save(pub);
    }

    public ResponsePubDetailDto getPubDetail(Long id) {
        Pub pub = pubRepository.findById(id)
                .orElse(null);
        if (pub != null) {
            return ResponsePubDetailDto.builder()
                    .tentNum(pub.getTentNum())
                    .pubName(pub.getPubName())
                    .sales(pub.getSales())
                    .totalRating(pub.getTotalRating())
                    .event(pub.getEvent())
                    .build();
        } else {
            return null;
        }
    }

    public void updatePub(Long id, RequestPubSaveDto requestPubSaveDto, Long memberId) {
        Organization organization = organizationRepository.findByRepresentativeId(memberId);
        if (organization == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Pub pub = pubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pub not found"));
        pub = Pub.builder()
                .id(id)
                .tentNum(requestPubSaveDto.getTentNum())
                .pubName(requestPubSaveDto.getPubName())
                .sales(requestPubSaveDto.getSales())
                .totalRating(requestPubSaveDto.getTotalRating())
                .event(requestPubSaveDto.getEvent())
                .build();

        // 엔티티 저장 (업데이트)
        pubRepository.save(pub);
    }

    public Page<ResponsePubListDto> getPubs(Pageable pageable) {
        Page<Pub> pubsPage = pubRepository.findAll(pageable);

        return pubsPage.map(pub -> ResponsePubListDto.builder()
                .tentNum(pub.getTentNum())
                .pubName(pub.getPubName())
                .totalRating(pub.getTotalRating())
                .build());
    }

    public void saveMenu(Long id, RequestMenuSaveDto menuSaveDto, Long memberId) {
        Organization organization = organizationRepository.findByRepresentativeId(memberId);
        if (organization == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Pub pub = pubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pub not found"));

        Menu menu = Menu.builder()
                .division(menuSaveDto.getDivision())
                .foodName(menuSaveDto.getFoodName())
                .price(menuSaveDto.getPrice())
                .pub(pub)
                .build();

        menuRepository.save(menu);
    }

    public Page<ResponseMenuListDto> getMenuListByPubId(Long pubId, Pageable pageable) {
        Page<Menu> menuPage = menuRepository.findByPubIdOrderByDivisionAsc(pubId, pageable);
        List<ResponseMenuListDto> menuList = new ArrayList<>();

        // 안주 먼저 추가
        menuPage.getContent().stream()
                .filter(menu -> menu.getDivision() == MenuDivision.SNACK)
                .map(menu -> new ResponseMenuListDto(
                        menu.getId(),
                        menu.getDivision().getDescription(),
                        menu.getFoodName(),
                        menu.getPrice()
                ))
                .forEach(menuList::add);

        // 음료 추가
        menuPage.getContent().stream()
                .filter(menu -> menu.getDivision() == MenuDivision.BEVERAGE)
                .map(menu -> new ResponseMenuListDto(
                        menu.getId(),
                        menu.getDivision().getDescription(),
                        menu.getFoodName(),
                        menu.getPrice()
                ))
                .forEach(menuList::add);

        return PageableExecutionUtils.getPage(menuList, pageable,
                menuPage::getTotalElements);
    }

    @Transactional
    public void deletePub(Long pubId, Long memberId) {
        Organization organization = organizationRepository.findByRepresentativeId(memberId);
        if (organization == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }
        Pub pub = pubRepository.findById(pubId)
                .orElseThrow(() -> new IllegalArgumentException("Pub not found"));
        pub.builder()
                .deletedAt(LocalDateTime.now())
                .build();
        pubRepository.save(pub);

        List<Menu> menuList = menuRepository.findByPubId(pubId);
        menuList.forEach(menu -> {
            Menu updatedMenu = menu.builder()
                    .deletedAt(LocalDateTime.now())
                    .build();
            menuRepository.save(updatedMenu);
        });
    }
}