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
    
    public void subtract(Long price) {
        this.balance -= price;
    }
    
    //    public UserPointEntity(Long userId, Long valance) {
//        this.userId = userId;
//        this.valance = valance;
//    }
//    public UserPointEntity charge(Long point) {
//        this.valance += point;
//        return this;
//    }
//    public UserPointEntity consume(Long point) {
//        this.valance -= point;
//        return this;
//    }
//    public boolean isEnoughPoint(Long point) {
//        return this.valance >= point;
//    }
//    public Long getValance() {
//        return this.valance;
//    }
}
