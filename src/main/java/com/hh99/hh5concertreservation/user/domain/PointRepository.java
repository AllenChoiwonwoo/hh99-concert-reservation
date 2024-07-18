package com.hh99.hh5concertreservation.user.domain;

import com.hh99.hh5concertreservation.user.presentation.dto.PointResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PointRepository {
    
    UserPointEntity addPoint(Long userId , Long amount);
    
    UserPointEntity findPoint(Long userId);

    UserPointEntity save(UserPointEntity subtract);

    Optional<UserPointEntity> findByUserId(Long userId);
}
