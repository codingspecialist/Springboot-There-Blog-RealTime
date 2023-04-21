package shop.mtcoding.thereblog.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 60) // 20자이하만 받을 예정
    private String password;
    @Column(length = 50)
    private String email;
    private String role; // USER(고객)
    private String profile; // 유저 프로필 사진의 경로
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 프로필 사진 변경
    public void changeProfile(String profile){
        this.profile = profile;
    }

    // 회원정보 수정
    public void update(String password, String email) {
        this.password = password;
        this.email = email;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}