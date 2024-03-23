package com.JJoINT.CamPuzl.global.auth.service;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.global.auth.dto.UserDetailsDTO;
import com.JJoINT.CamPuzl.global.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsDTO loadUserByUsername(String studentId) throws UsernameNotFoundException {
        return memberRepository.findByStudentId(studentId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다"));
    }

    private UserDetailsDTO createUserDetails(Member member) {
        return UserDetailsDTO.builder()
                .name(member.getName())
                .password(passwordEncoder.encode(member.getPassword()))
                .build();
    }
}
