package kr.spartaclub.develop_scheduleapp.service;

import kr.spartaclub.develop_scheduleapp.dto.UserRequest;
import kr.spartaclub.develop_scheduleapp.dto.UserResponse;
import kr.spartaclub.develop_scheduleapp.dto.UserUpdateRequest;
import kr.spartaclub.develop_scheduleapp.entity.User;
import kr.spartaclub.develop_scheduleapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spartaclub.develop_scheduleapp.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }


        User saved = userRepository.save(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return new UserResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findOne(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다. id=" + id));
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다. id=" + id));

        user.update(request.getUsername(), request.getEmail());
        return new UserResponse(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다. id=" + id);
        }
        userRepository.deleteById(id);
    }
}
