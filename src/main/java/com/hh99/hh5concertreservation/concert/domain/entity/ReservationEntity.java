package com.hh99.hh5concertreservation.concert.domain.entity;

import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "reservation", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"concert_option_id", "seat_no"} , name = "unique_index")
})
@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "concert_id")
    private Long concertId;
    @Column(name = "concert_option_id")
    private Long concertOptionId;
    @Column(name = "expired_at")
    private Long expiredAt;
    @Column(name = "seat_no")
    private Integer seatNo;
    @Column(name = "status")
    private Integer status; // 0  공석, 1 임시 예약, 2 예약 완료 ,-1 만료
    @Column(name = "price")
    private Integer price;

    @Version
    @ToString.Exclude
    private Long version;


    public ReservationEntity(Long userId, Long concertDescId, Integer seatNo, Integer price) {
        this.userId = userId;
        this.concertOptionId = concertDescId;
        this.seatNo = seatNo;
        this.status = 1;
        this.expiredAt = System.currentTimeMillis() + 1000 * 60 * 5;
        this.price = price;
    }


    public void setConfirm() {
        this.status = 2;
    }

    public boolean checkExpired() {
        if (this.status == 1 && this.expiredAt < System.currentTimeMillis()) {
            this.status = -1;
            return true;
        }
        return false;
    }

    public ReservationEntity update(ReservationCommand command) {
        this.userId = command.getUserId();
        this.concertId  = command.getConcertId();
        this.concertOptionId = command.getConcertDescId();
        this.seatNo = command.getSeatNo();
        this.price = command.getPrice();
        this.status = 1;
        this.expiredAt = System.currentTimeMillis() + 1000 * 60 * 5;
        return this;
    }
}
