package com.JJoINT.CamPuzl.global.auth.service;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.global.auth.dto.UpgradeRequestDTO;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.enums.Role;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
@Slf4j
@RequiredArgsConstructor
public class UpgradePermissionService {

    private final MemberRepository memberRepository;
    public void upgradePermission(UpgradeRequestDTO upgradeRequestDTO) {
        Optional<Member> findStudentId = memberRepository.findByStudentId(upgradeRequestDTO.getStudentId());
        String permissionPassword = upgradeRequestDTO.getPermissionPassword();
        String unitCode = upgradeRequestDTO.getUnitCode();
        String permissionAccessPassword = "123";
        if (findStudentId.isPresent() && permissionPassword.equals(permissionAccessPassword)) {
            Member member = findStudentId.get();
            switch (unitCode) {
                case "001":
                    member.setRole(Role.STUDENT_COUNCIL_MANAGER);
                    break;
                case "002":
                    member.setRole(Role.CLUB_MANAGER);
                    break;
                case "003":
                    member.setRole(Role.GENERAL_STUDENT_COUNCIL);
                    break;
                case "005":
                    member.setRole(Role.ADMIN);
                    break;
            }
            memberRepository.save(member);
        } else {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

}
