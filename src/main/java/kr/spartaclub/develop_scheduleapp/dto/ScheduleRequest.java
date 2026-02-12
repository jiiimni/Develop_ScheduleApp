package kr.spartaclub.develop_scheduleapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;


// 일정 생성 요청 DTO
// Entity 직접 받지 않고 DTO로 받는 이유: 보안/유지보수/필드 통제(원하지 않는 값 들어오는 것 방지)
@Getter
public class ScheduleRequest {

    @NotNull(message = "userId는 필수입니다.")
    @Positive(message = "userId는 1 이상의 값이어야 합니다.")
    private Long userId;

    @NotBlank(message = "title은 필수입니다.")
    private String title;

    @NotBlank(message = "content는 필수입니다.")
    private String content;
}
