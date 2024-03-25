package com.JJoINT.CamPuzl.global.auth.jwt;

import com.JJoINT.CamPuzl.domain.member.domain.Member;
import com.JJoINT.CamPuzl.global.auth.dto.GeneratedTokenDTO;
import com.JJoINT.CamPuzl.global.auth.dto.SecurityMemberDTO;
import com.JJoINT.CamPuzl.domain.member.repository.MemberRepository;
import com.JJoINT.CamPuzl.global.enums.TokenType;
import com.JJoINT.CamPuzl.global.error.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.JJoINT.CamPuzl.global.enums.ErrorCode.MEMBER_NOT_FOUND;
import static com.JJoINT.CamPuzl.global.enums.ErrorCode.MISMATCH_REFRESH_TOKEN;


@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final MemberRepository memberRepository;

    public JwtTokenProvider(JwtProperties jwtProperties, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        String secretKey = jwtProperties.getSecret();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateAccessToken(Authentication authentication) {

        return generateToken(authentication, TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, TokenType.REFRESH_TOKEN);

    }
    private String generateToken(Authentication authentication, TokenType tokenType) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date expiration = tokenType == TokenType.ACCESS_TOKEN ? new Date(now + 86400000) : new Date(now + 864000000); // 토큰 유효기간 설정

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Transactional
    public GeneratedTokenDTO reissueToken(String refreshToken) {
        GeneratedTokenDTO generatedTokenDTO;
        String reissuedRefreshToken;
        String reissuedAccessToken;
        Claims claims = validateToken(refreshToken);
        SecurityMemberDTO securityMemberDTO = SecurityMemberDTO.fromClaims(claims);

        Optional<Member> findMember = memberRepository.findByStudentId(String.valueOf(securityMemberDTO.getStudentId()));

        if(findMember.isEmpty()) {
            throw new BusinessException(MEMBER_NOT_FOUND);
        }

        Member member = findMember.get();

        if(member.getRefreshToken() == null) {
            throw new BusinessException(MISMATCH_REFRESH_TOKEN);
        }

        if(!member.getRefreshToken().equals(refreshToken)) {
            throw new BusinessException(MISMATCH_REFRESH_TOKEN);
        }

        reissuedRefreshToken = generateRefreshToken((Authentication) securityMemberDTO);
        reissuedAccessToken = generateAccessToken((Authentication) securityMemberDTO);
        member.setRefreshToken(refreshToken);

        memberRepository.save(member);

        generatedTokenDTO = GeneratedTokenDTO.builder().accessToken(reissuedAccessToken).refreshToken(reissuedRefreshToken).build();

        return generatedTokenDTO;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claimsJws.getBody(); // 유효한 토큰인 경우 토큰의 클레임을 반환
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return null; // 유효하지 않은 토큰인 경우 null을 반환
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    public void saveRefreshToken(String studentId, String refreshToken) {
        Optional<Member> findMember = memberRepository.findByStudentId(studentId);
        findMember.ifPresent(member -> memberRepository.updateRefreshToken(member.getId(), refreshToken));
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        try {
            // Refresh token의 만료 여부 확인
            Claims claims = validateToken(refreshToken);
            return claims != null;
        } catch (ExpiredJwtException e) {
            return false; // 만료된 Refresh token
        } catch (Exception e) {
            log.error("Error validating refresh token", e);
            return false; // 유효하지 않은 Refresh token
        }
    }
}