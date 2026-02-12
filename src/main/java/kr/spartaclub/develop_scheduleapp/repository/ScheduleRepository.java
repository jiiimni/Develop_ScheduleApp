package kr.spartaclub.develop_scheduleapp.repository;

import kr.spartaclub.develop_scheduleapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
