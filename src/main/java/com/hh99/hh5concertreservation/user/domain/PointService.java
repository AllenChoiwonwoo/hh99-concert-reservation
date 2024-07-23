package com.hh99.hh5concertreservation.user.domain;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.user.presentation.dto.PointResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hh99.hh5concertreservation.common.CustomException.ErrorEnum.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PointService {
    
    private final PointRepository pointRepository;
    
    @Transactional
    public PointResult charge(Long userId , Long amount) {
        UserPointEntity userPoint = pointRepository.save(findPoint(userId).addPoint(amount));
        return new PointResult(userPoint.getUserId(), userPoint.getBalance());
    }
    
    public PointResult getPoint(Long userId) {
        UserPointEntity point = findPoint(userId);
        return new PointResult(point.getUserId(), point.getBalance());
    }

    public UserPointEntity subtractPoint(Long userId, Long price) {
        UserPointEntity result = pointRepository.save(findPoint(userId).subtract(price));
        return result;
    }
    public UserPointEntity findPoint(Long userId) {
        UserPointEntity userPoint = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        return userPoint;
    }
}
