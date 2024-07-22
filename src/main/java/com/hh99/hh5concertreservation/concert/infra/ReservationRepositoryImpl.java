package com.hh99.hh5concertreservation.concert.infra;

import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements IReservationRepository {
    private final ReservationJpaRepository repo;
    /**
     * @param reservation
     * @return
     */
    @Override
    public ReservationEntity save(ReservationEntity reservation) {
        return repo.save(reservation);
    }

    /**
     * @param concertScheduleId
     * @return
     */
    @Override
    public List<SeatsInfo> findReveredSeats(Long concertScheduleId) {
        List<SeatsInfo> list = repo.findReveredSeats(concertScheduleId);
        return list;
    }

    /**
     * @param concertDescId
     * @param seatNo
     * @return
     */

    @Override
    public Optional<ReservationEntity> findReserveInfo(Long concertDescId, Integer seatNo) {
        return repo.findByConcertOptionIdAndSeatNo(concertDescId, seatNo);
    }
    @Override
    public Optional<ReservationEntity> findReserveInfo(Long concertDescId, Integer seatNo, Integer status) {
        return  repo.findByConcertOptionIdAndSeatNoAndStatus(concertDescId, seatNo, status);
    }

    /**
     * @param status
     * @return
     */
    @Override
    public List<ReservationEntity> findRevervationsByStatus(int status) {
        List<ReservationEntity> list = repo.findAllByStatus(status);
        return list;
    }
}
