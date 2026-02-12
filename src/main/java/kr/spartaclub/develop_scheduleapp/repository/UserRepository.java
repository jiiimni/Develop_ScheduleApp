package kr.spartaclub.develop_scheduleapp.repository;

import kr.spartaclub.develop_scheduleapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
