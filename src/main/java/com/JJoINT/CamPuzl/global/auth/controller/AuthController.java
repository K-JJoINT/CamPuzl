package com.JJoINT.CamPuzl.global.auth.controller;

import com.JJoINT.CamPuzl.global.auth.dto.*;
import com.JJoINT.CamPuzl.global.auth.jwt.JwtProvider;
import com.JJoINT.CamPuzl.global.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 로직" , description = "학번과 비밀번호를 사용하여 회원가입을 진행합니다.")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {

        SignUpResponseDTO responseDTO = authService.signUp(signUpRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 로직" , description = "학번과 비밀번호를 사용하여 로그인을 진행합니다.")
    public ResponseEntity<GeneratedTokenDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        String studentId = String.valueOf(loginRequestDTO.getStudentId());
        String password = String.valueOf(loginRequestDTO.getPassword());
        GeneratedTokenDTO generatedTokenDTO = authService.login(studentId, password);
        return ResponseEntity.ok(generatedTokenDTO);

    }
}
