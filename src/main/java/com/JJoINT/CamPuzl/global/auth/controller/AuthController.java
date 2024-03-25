package com.JJoINT.CamPuzl.global.auth.controller;

import com.JJoINT.CamPuzl.global.auth.dto.*;
import com.JJoINT.CamPuzl.global.auth.service.AuthService;
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

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {

        SignUpResponseDTO responseDTO = authService.signUp(signUpRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<GeneratedTokenDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String studentId = String.valueOf(loginRequestDTO.getStudentId());
        String password = String.valueOf(loginRequestDTO.getPassword());
        GeneratedTokenDTO generatedTokenDTO = authService.login(studentId, password);
        return ResponseEntity.ok(generatedTokenDTO);

    }

    @PostMapping("/test")
    public String test() {
        return "sucess";
    }
}
