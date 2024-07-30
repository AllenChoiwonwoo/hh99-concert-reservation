package com.hh99.hh5concertreservation.concert.infra;

import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertEntity;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertOption;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements IConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertOptionJpaRepository concertOptionJpaRepository;

    @Override
    public List<ConcertScheduleInfo> findSchedules(Long concertId) {
        concertJpaRepository.findById(concertId);
        return null;
    }

    @Override
    public List<SeatsInfo> findReftSeats(Long concertScheduleId) {
        return null;
    }

    @Override
    public Optional<ReservationEntity> checkSeat(Long concertDescId, Integer seatNo) {
        return Optional.empty();
    }

    @Override
    public Optional<ReservationEntity> checkSeat(Long concertDescId, Integer seatNo, Integer status) {
        return Optional.empty();
    }

    @Override
    public ReservationEntity save(ReservationEntity reservationEntity) {
        return null;
    }

    @Override
    public Long findConcertOptionPrice(Long concertDescId) {
        return null;
    }

    @Override
    public List<ConcertEntity> findConcerts() {
        return concertJpaRepository.findAll();
    }

    @Override
    public ConcertEntity save(ConcertEntity concert) {
        ConcertEntity save = concertJpaRepository.save(concert);
        return save;
    }

    /**
     * @param concertOption
     * @return
     */
    @Override
    public ConcertOption save(ConcertOption concertOption) {
       return concertOptionJpaRepository.save(concertOption);
    }

    /**
     * @param concertId
     * @return
     */
    @Override
    public Optional<ConcertEntity> findConcertById(Long concertId) {
        Optional<ConcertEntity> concert = concertJpaRepository.findById(concertId);
        return concert;
    }
}
