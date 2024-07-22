package com.hh99.hh5concertreservation.concert.infra;

import com.hh99.hh5concertreservation.concert.domain.entity.ConcertOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConcertOptionJpaRepository extends JpaRepository<ConcertOption, Long>{
}
