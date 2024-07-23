package com.hh99.hh5concertreservation.user.infra;

import com.hh99.hh5concertreservation.user.domain.UserPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPointEntity, Long> {

    Optional<UserPointEntity> findByUserId(Long userId);
}
