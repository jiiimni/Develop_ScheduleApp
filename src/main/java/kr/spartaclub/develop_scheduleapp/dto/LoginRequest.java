package kr.spartaclub.develop_scheduleapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    @NotBlank(message = "email은 필수입니다.")
    private String email;

    @NotBlank(message = "password는 필수입니다.")
    private String password;
}
