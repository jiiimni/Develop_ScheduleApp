package kr.spartaclub.develop_scheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor // JPA는 기본 생성자가 필요함
@Entity
@Table(name = "schedules")
public class Schedule extends BaseTimeEntity { // createdAt/updatedAt 자동 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     // 작성 유저명
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     private User user;


    // 할일 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 할일 내용 - TEXT로 저장해서 긴 글도 저장 가능하게 함
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 생성용 생성자
    // (Controller -> Service -> Entity 생성)
    public Schedule(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    // 수정 로직
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
