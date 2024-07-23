package com.hh99.hh5concertreservation.waiting.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "status")
    private Integer status; // 0: 대기중, 1: 진입 완료, -1: 만료
    @Column(name = "expired_at", nullable = false)
    private Long expiredAt;


    public TokenEntity(Long userId, int status) {
        this.userId = userId;
        this.status = status;
        this.token = makeRandomToken(userId);
        this.expiredAt = makeExpiredTime();
    }
    public String makeRandomToken(Long userId) {
        UUID uuid = UUID.nameUUIDFromBytes(new byte[]{userId.byteValue()});
        return uuid.toString();
    }
    public Long makeExpiredTime() {
        // 5분 후가 만료시간
        return System.currentTimeMillis() + (5 * 60 * 1000);
    }

    
    public TokenEntity setExpire() {
        this.status = -1;
        return this;
    }

    public TokenEntity renewExpiredAt() {
        this.expiredAt = this.makeExpiredTime();
        return this;
    }
}
