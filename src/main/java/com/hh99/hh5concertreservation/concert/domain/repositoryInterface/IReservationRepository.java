package com.hh99.hh5concertreservation.concert.domain.repositoryInterface;

import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

public interface IReservationRepository {
    ReservationEntity save(ReservationEntity reservation);

    List<SeatsInfo> findReveredSeats(Long concertScheduleId);

    Optional<ReservationEntity> findReserveInfo(Long concertDescId, Integer seatNo);

    Optional<ReservationEntity> findReserveInfo(Long concertDescId, Integer seatNo, Integer status);

    List<ReservationEntity> findRevervationsByStatus(int status);
}
