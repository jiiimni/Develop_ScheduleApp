package kr.spartaclub.develop_scheduleapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequest {

    @NotBlank(message = "username은 필수입니다.")
    private String username;

    @Email(message = "email 형식이 올바르지 않습니다.")
    @NotBlank(message = "email은 필수입니다.")
    private String email;

    @NotBlank(message = "password는 필수입니다.")
    @Size(min = 8, message = "password는 최소 8자 이상이어야 합니다.")
    private String password;
}