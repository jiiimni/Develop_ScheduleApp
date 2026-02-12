package kr.spartaclub.develop_scheduleapp.service;

import kr.spartaclub.develop_scheduleapp.dto.ScheduleRequest;
import kr.spartaclub.develop_scheduleapp.dto.ScheduleResponse;
import kr.spartaclub.develop_scheduleapp.dto.ScheduleUpdateRequest;
import kr.spartaclub.develop_scheduleapp.entity.Schedule;
import kr.spartaclub.develop_scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.spartaclub.develop_scheduleapp.entity.User;
import kr.spartaclub.develop_scheduleapp.repository.UserRepository;


import java.util.List;


@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public ScheduleResponse create(ScheduleRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 유저가 존재하지 않습니다. id=" + request.getUserId()));

        Schedule saved = scheduleRepository.save(
                new Schedule(user, request.getTitle(), request.getContent())
        );

        return new ScheduleResponse(saved);
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponse findOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 일정이 존재하지 않습니다. id=" + id));

        return new ScheduleResponse(schedule);
    }

    // 수정
    @Transactional
    public ScheduleResponse update(Long id, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 일정이 존재하지 않습니다. id=" + id));

        schedule.update(request.getTitle(), request.getContent());

        return new ScheduleResponse(schedule);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "해당 일정이 존재하지 않습니다. id=" + id);
        }
        scheduleRepository.deleteById(id);
    }
}