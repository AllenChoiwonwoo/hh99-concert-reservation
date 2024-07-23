package com.hh99.hh5concertreservation.user.presentation;


import com.hh99.hh5concertreservation.user.domain.PointService;
import com.hh99.hh5concertreservation.user.presentation.dto.PointBalanceResponse;
import com.hh99.hh5concertreservation.user.presentation.dto.PointResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final PointService pointService;
    
    @PutMapping("/{userId}/point")
    public ResponseEntity charge(@PathVariable Long userId, @RequestBody Long amount){
        PointResult result = pointService.charge(userId, amount);
        return ResponseEntity.ok(new PointBalanceResponse(result));
    }
    @GetMapping("/{userId}/point")
    public ResponseEntity getPoint(@PathVariable Long userId){
        PointResult result = pointService.getPoint(userId);
        return ResponseEntity.ok(new PointBalanceResponse(result));
    }
    
}

