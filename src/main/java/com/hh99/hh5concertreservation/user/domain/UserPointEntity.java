package com.hh99.hh5concertreservation.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class UserPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "balance", nullable = false)
    private Long balance;

    public UserPointEntity(Long userId, Long amount) {
        this.userId = userId;
        this.balance = amount;
    }

    public UserPointEntity subtract(Long price) {
        this.balance -= price;
        return this;
    }

    public UserPointEntity addPoint(Long amount) {
        this.balance += amount;
        return this;
    }
}
