package com.JJoINT.CamPuzl.global.auth.service;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.global.auth.dto.*;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.global.auth.jwt.JwtProvider;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.enums.Role;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.JJoINT.CamPuzl.global.enums.Role.STUDENT;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        Optional<Member> findStudentId = memberRepository.findByStudentId(signUpRequestDTO.getStudentId());
        if (findStudentId.isPresent()) {
            throw new BusinessException(ErrorCode.MEMBER_PROFILE_DUPLICATION);
        }

        Role role = STUDENT;

        String hashedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        Member member = Member.builder()
                .name(signUpRequestDTO.getName())
                .studentId(signUpRequestDTO.getStudentId())
                .password(hashedPassword)
                .build();
        member.setRole(role);

        memberRepository.save(member);
        return SignUpResponseDTO.builder()
                .name(member.getName())
                .studentId(member.getStudentId())
                .build();
    }


    @Transactional
    public GeneratedTokenDTO login(String studentId, String password) {
        Optional<Member> findStudentId = memberRepository.findByStudentId(studentId);

        if (findStudentId.isPresent()) {
            Member member = findStudentId.get();
            if (passwordEncoder.matches(password, member.getPassword())) {
                SecurityMemberDTO securityMemberDTO = SecurityMemberDTO.builder()
                        .studentId(member.getId())
                        .role(member.getRole())
                        .name(member.getName())
                        .build();

                GeneratedTokenDTO generatedTokenDTO = jwtProvider.generateTokens(securityMemberDTO);

                return generatedTokenDTO;
            } else {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            }
        } else {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}



