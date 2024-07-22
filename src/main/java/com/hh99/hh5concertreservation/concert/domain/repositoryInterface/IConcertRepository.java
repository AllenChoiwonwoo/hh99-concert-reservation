package com.hh99.hh5concertreservation.concert.domain.repositoryInterface;

import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertEntity;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertOption;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

public interface IConcertRepository {
    List<ConcertScheduleInfo> findSchedules(Long concertId);

    List<SeatsInfo> findReftSeats(Long concertScheduleId);

    Optional<ReservationEntity> checkSeat(Long concertDescId, Integer seatNo);
    Optional<ReservationEntity> checkSeat(Long concertDescId, Integer seatNo, Integer status);
    ReservationEntity save(ReservationEntity reservationEntity);
    Long findConcertOptionPrice(Long concertDescId);

    List<ConcertEntity> findConcerts();
    ConcertEntity save(ConcertEntity concert);

    ConcertOption save(ConcertOption concertOption);

//    ConcertOption save(ConcertOption concertOption);
}
