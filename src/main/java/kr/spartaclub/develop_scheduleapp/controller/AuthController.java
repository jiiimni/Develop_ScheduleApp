package kr.spartaclub.develop_scheduleapp.controller;

import jakarta.servlet.http.HttpSession;
import kr.spartaclub.develop_scheduleapp.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import kr.spartaclub.develop_scheduleapp.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kr.spartaclub.develop_scheduleapp.dto.LoginRequest;
import kr.spartaclub.develop_scheduleapp.entity.User;
import kr.spartaclub.develop_scheduleapp.repository.UserRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public static final String LOGIN_USER_ID = "LOGIN_USER_ID";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // 로그인 성공: 세션에 로그인 유저 id 저장
        session.setAttribute(LOGIN_USER_ID, user.getId());

        return ResponseEntity.ok("LOGIN_OK");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("LOGOUT_OK");
    }
}
