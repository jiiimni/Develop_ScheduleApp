package kr.spartaclub.develop_scheduleapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 일정 수정 요청 DTO
@Getter
public class ScheduleUpdateRequest {

    @NotBlank(message = "title은 필수입니다.")
    private String title;

    @NotBlank(message = "content는 필수입니다.")
    private String content;
}

