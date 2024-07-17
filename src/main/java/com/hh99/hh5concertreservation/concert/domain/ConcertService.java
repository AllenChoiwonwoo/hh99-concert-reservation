package com.hh99.hh5concertreservation.concert.domain;

import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConcertService {
    private final IConcertRepository concertRepository;
    private final IReservationRepository reservationRepository;
    private final Map<Integer, Integer> seatState;

    public ConcertService(IConcertRepository concertRepository, IReservationRepository reservationRepository) {
        this.concertRepository = concertRepository;
        this.reservationRepository = reservationRepository;
        this.seatState = new HashMap<>(50);
        for (int i = 1; i <= 50; i++) {
            seatState.put(i,0);
        }
    }

    public List<ConcertScheduleInfo> findSchedules(Long concertId) {
        List<ConcertScheduleInfo> concert = concertRepository.findSchedules(concertId);
        return concert;
    }

    public Map<Integer, Integer> findLeftSeats(Long concertScheduleId) {
        List<SeatsInfo> reftSeats = reservationRepository.findReftSeats(concertScheduleId);
        reftSeats.forEach(i -> {
            if (i.getState() > 0) seatState.put(i.getSeatNo(), 1);
        });
        return seatState;
    }

    public ReservationResult reserve(ReservationCommand command) {
        validateSeat(command.getConcertDescId(), command.getSeatNo());
    ReservationEntity reservationEntity = new ReservationEntity(command.getUserId(),command.getConcertDescId(), command.getSeatNo());
    ReservationEntity saved = reservationRepository.save(reservationEntity);
        return new ReservationResult(command.getConcertId(), saved);
}

    public void checkSeat(Long concertDescId, Integer seatNo) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.checkSeat(concertDescId, seatNo);
        if (reservationEntity.isPresent()) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }
    }
    public ReservationEntity checkSeat(Long concertDescId, Integer seatNo, Integer status) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.checkSeat(concertDescId, seatNo, status);
        if (reservationEntity.isEmpty()) {
            throw new IllegalStateException("임시 예약되지 않은 좌석입니다.");
        }
        return reservationEntity.get();
    }

    public void validateSeat(Long concertDescId, Integer seatNo) {
        if(seatState.containsKey(seatNo) == false)
            throw new InputMismatchException("존제하지 않는 좌석입니다.");

        Optional<ReservationEntity> reservationEntity = reservationRepository.findByConsertOptionIdAndSeatNo(concertDescId, seatNo);
        if (reservationEntity.isEmpty()){
            return;
        }
        ReservationEntity entity = reservationEntity.get();
        if (entity.getStatus() == 1 || entity.getStatus() == 2) { // 임시예약 || 예약확정
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }
    }

    public ReservationEntity findTempRevervation(Long concertDescId, Integer seatNo, Integer status) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findByConsertOptionIdAndSeatNo(concertDescId, seatNo, status);
        if (reservationEntity.isEmpty()) {
            throw new IllegalStateException("임시 예약되지 않은 좌석입니다.");
        }
        return reservationEntity.get();
    }

    public Long findPrice(Long concertDescId) {
        return concertRepository.findConcertOptionPrice(concertDescId);
    }

    public ReservationEntity confirmReservation(ReservationEntity reservation) {
        reservation.setConfirm();
        return reservationRepository.save(reservation);
    }

    public List<ConcertEntity> findConcerts() {
        return concertRepository.findConserts();
    }

    public void expireReservation() {
        List<ReservationEntity> list = reservationRepository.findAllTempRevervation(1);
        for (ReservationEntity reservation : list) {
            reservation.validate();
            reservationRepository.save(reservation);
        }
    }


    public Optional<ReservationEntity> findReservation(Long concertDescId , Integer seatNo) {
        return null;
    }
}
