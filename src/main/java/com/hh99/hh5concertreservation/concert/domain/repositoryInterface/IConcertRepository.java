package com.hh99.hh5concertreservation.concert.domain.repositoryInterface;

import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IConcertRepository {
    List<ConcertScheduleInfo> findSchedules(Long concertId);

    List<SeatsInfo> findReftSeats(Long concertScheduleId);
}
