package kr.spartaclub.develop_scheduleapp.controller;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import kr.spartaclub.develop_scheduleapp.exception.CustomException;
import org.springframework.http.HttpStatus;
import kr.spartaclub.develop_scheduleapp.dto.ScheduleRequest;
import kr.spartaclub.develop_scheduleapp.dto.ScheduleResponse;
import kr.spartaclub.develop_scheduleapp.dto.ScheduleUpdateRequest;
import kr.spartaclub.develop_scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody ScheduleRequest request, HttpSession session) {

        Long loginUserId = (Long) session.getAttribute(AuthController.LOGIN_USER_ID);
        if (loginUserId == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        return ResponseEntity.ok(scheduleService.create(loginUserId, request));
    }


    // 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findOne(id));
    }

    // 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequest request
    ) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }


    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
