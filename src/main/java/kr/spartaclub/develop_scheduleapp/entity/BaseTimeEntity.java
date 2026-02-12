package kr.spartaclub.develop_scheduleapp.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

// 엔티티가 최초 저장될 때 자동으로 들어가는 값
    @CreatedDate
    private LocalDateTime createdAt;

// 엔티티가 수정될 때 자동으로 갱신되는 값
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
