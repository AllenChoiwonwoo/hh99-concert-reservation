package com.hh99.hh5concertreservation.user.infra;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.user.domain.PointRepository;
import com.hh99.hh5concertreservation.user.domain.UserPointEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements PointRepository {

    private final UserPointJpaRepository repo;

    /**
     * @param userPoint
     * @return
     */
    @Override
    public UserPointEntity save(UserPointEntity userPoint) {
        return repo.save(userPoint);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Optional<UserPointEntity> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
