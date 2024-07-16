package com.hh99.hh5concertreservation.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointBalanceResponse {
    private Integer balance;
    
    public PointBalanceResponse(PointResult result) {
        this.balance = result.getBalance();
    }
}
