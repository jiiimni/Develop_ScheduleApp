package kr.spartaclub.develop_scheduleapp.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {

    // 언제 발생했는지
    private final LocalDateTime timestamp = LocalDateTime.now();

    // HTTP 상태코드 (400, 404 등)
    private final int status;

    // 에러 설명
    private final String message;

    // 필드별 validation 에러(있을 때만 사용)
    private final Map<String, String> fieldErrors;

    // 일반 에러용 생성자
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.fieldErrors = null;
    }

    // validation 에러용 생성자
    public ErrorResponse(int status, String message, Map<String, String> fieldErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
