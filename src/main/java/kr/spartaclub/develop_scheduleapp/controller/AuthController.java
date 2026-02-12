package kr.spartaclub.develop_scheduleapp.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
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
