package kr.spartaclub.develop_scheduleapp.dto;

import kr.spartaclub.develop_scheduleapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

// 일정 응답 DTO
@Getter
public class ScheduleResponse {

    private final Long id;
    private final String username;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}

