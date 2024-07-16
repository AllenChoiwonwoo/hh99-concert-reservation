package com.hh99.hh5concertreservation.concert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "concert_option_id")
    private Long concertOptionId;
    @Column(name = "expired_at")
    private Long expiredAt;
    @Column(name = "seat_no")
    private Integer seatNo;
    @Column(name = "status")
    private Integer status; // 0  공석, 1 임시 예약, 2 예약 완료

    public ReservationEntity(Long userId, Long concertDescId, Integer seatNo) {
        this.userId = userId;
        this.concertOptionId = concertDescId;
        this.seatNo = seatNo;
        this.status = 1;
        this.expiredAt = System.currentTimeMillis() + 1000 * 60 * 5;
    }

    public void setConfirm() {
        this.status = 2;
    }
}
