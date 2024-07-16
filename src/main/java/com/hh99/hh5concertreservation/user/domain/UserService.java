package com.hh99.hh5concertreservation.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    public UserPointEntity subtractPoint(Long userId, Long price) {
        UserPointEntity point = findPoint(userId);
        point.subtract(price);
        UserPointEntity result = userRepository.save(point);
        return result;
    }
    public UserPointEntity findPoint(Long userId) {
        UserPointEntity userPoint = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        return userPoint;
    }
}
