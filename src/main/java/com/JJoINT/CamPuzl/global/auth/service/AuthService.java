package com.JJoINT.CamPuzl.global.auth.service;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.global.auth.dto.*;
import com.JJoINT.CamPuzl.global.auth.jwt.JwtTokenProvider;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.global.enums.ErrorCode;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberDetailsService memberDetailsService;


    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        Optional<Member> findStudentId = memberRepository.findByStudentId(signUpRequestDTO.getStudentId());
        if (findStudentId.isPresent()) {
            throw new BusinessException(ErrorCode.MEMBER_PROFILE_DUPLICATION);
        }
        String hashedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        Member member = Member.builder()
                .name(signUpRequestDTO.getName())
                .studentId(signUpRequestDTO.getStudentId())
                .password(hashedPassword)
                .build();


        memberRepository.save(member);
        return SignUpResponseDTO.builder()
                .name(member.getName())
                .studentId(member.getStudentId())
                .build();
    }

    //    @Transactional
//    public GeneratedTokenDTO login(String studentId, String password) {
//        Optional<Member> optionalMember = memberRepository.findByStudentId(studentId);
//
//        Member member = optionalMember.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
//
//        if (!encoder.matches(password, member.getPassword())) {
//            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
//        }
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(studentId, password);
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        GeneratedTokenDTO generatedTokenDTO = jwtTokenProvider.generateToken(authentication);
//
//        return generatedTokenDTO;
//    }
//    @Transactional
//    public GeneratedTokenDTO login(String studentId, String password) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(studentId, password);
//
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        GeneratedTokenDTO generatedTokenDTO = jwtTokenProvider.generateToken(authentication);
//
//        return generatedTokenDTO;
//    }
    @Transactional
    public GeneratedTokenDTO login(String studentId, String password) {
        // UserDetails 가져오기
        UserDetailsDTO userDetails = memberDetailsService.loadUserByUsername(studentId);

        // 비밀번호 일치 여부 확인
//        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//            System.out.println("userDetails = " + userDetails.getPassword());
//            System.out.println("password = " + password);
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }

        // JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        jwtTokenProvider.saveRefreshToken(studentId, refreshToken);

        // GeneratedTokenDTO에 액세스 토큰과 리프레시 토큰을 담아서 반환
        return GeneratedTokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
