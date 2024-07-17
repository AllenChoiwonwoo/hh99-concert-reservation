package com.hh99.hh5concertreservation.user.domain;

import com.hh99.hh5concertreservation.user.presentation.dto.PointResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    
    private final PointRepository pointRepository;
    
    @Transactional
    public PointResult charge(Long userId , Long amount) {
        UserPointEntity userPoint = pointRepository.addPoint(userId, amount);
        return new PointResult(userPoint.getUserId(), userPoint.getBalance());
    }
    
    public PointResult getPoint(Long userId) {
        UserPointEntity point = pointRepository.findPoint(userId);
        return new PointResult(point.getUserId(), point.getBalance());
    }
}
