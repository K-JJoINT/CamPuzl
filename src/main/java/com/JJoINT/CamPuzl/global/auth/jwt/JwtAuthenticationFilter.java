package com.JJoINT.CamPuzl.global.auth.jwt;

import com.JJoINT.CamPuzl.global.auth.dto.GeneratedTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        String token = resolveToken((HttpServletRequest) request);
//
//        if (token != null) {
//            Claims claims = jwtTokenProvider.validateToken(token);
//            if (claims != null) { // 유효한 토큰인 경우
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//        }
//
//        filterChain.doFilter(request, response);
//    }
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    String token = resolveToken((HttpServletRequest) request);

    if (token != null) {
        try {
            Claims claims = jwtTokenProvider.validateToken(token);
            if (claims != null) { // 유효한 토큰인 경우
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            // Access token이 만료된 경우
            try {
                // Refresh token이 유효한지 확인
                if (jwtTokenProvider.isRefreshTokenValid(token)) {
                    // Refresh token을 사용하여 Access token과 Refresh token 재발급
                    GeneratedTokenDTO generatedTokenDTO = jwtTokenProvider.reissueToken(token);

                    // 새로운 토큰을 클라이언트에게 전달 (예: HTTP 응답 헤더에 포함)
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.addHeader("New-Access-Token", generatedTokenDTO.getAccessToken());
                    httpResponse.addHeader("New-Refresh-Token", generatedTokenDTO.getRefreshToken());

                    // 필터 체인을 중단하여 더 이상의 작업을 수행하지 않음
                    return;
                }
            } catch (Exception ex) {

                log.error("Refresh token is not valid", ex);
            }

            // Access token이 만료되었고 Refresh token이 유효하지 않은 경우
            // 예: 클라이언트에게 다시 로그인 페이지로 이동하도록 요청
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/login"); // 재로그인 페이지로 리다이렉트
            return; // 필터 체인을 중단하여 더 이상의 작업을 수행하지 않음
        }
    }

    filterChain.doFilter(request, response);
}

//todo
    // test 후 private로 되돌리기


    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}