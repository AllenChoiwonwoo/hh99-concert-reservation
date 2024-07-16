package com.hh99.hh5concertreservation.user.domain;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository {

    Optional<UserPointEntity> findByUserId(Long userId);

    UserPointEntity save(UserPointEntity point);
}
