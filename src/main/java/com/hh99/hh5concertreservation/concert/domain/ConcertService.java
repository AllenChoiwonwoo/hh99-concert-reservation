package com.hh99.hh5concertreservation.concert.domain;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IReservationRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class ConcertService {
    private final IConcertRepository concertRepository;
    private final IReservationRepository reservationRepository;
    private final Map<Integer, Integer> seatState;
    private final int AVAIL = 0;
    private final int TEMP_RESERVED = 1;
    private final int RESERVED = 2;

    public ConcertService(IConcertRepository concertRepository, IReservationRepository reservationRepository) {
        this.concertRepository = concertRepository;
        this.reservationRepository = reservationRepository;
        this.seatState = new HashMap<>(50);
        for (int i = 1; i <= 50; i++) {
            seatState.put(i,AVAIL);
        }
    }

    public List<ConcertScheduleInfo> findSchedules(Long concertId) {
        List<ConcertScheduleInfo> concert = concertRepository.findSchedules(concertId);
        return concert;
    }
    public Map<Integer, Integer> findLeftSeats(Long concertScheduleId) {
        List<SeatsInfo> reservedSeat = reservationRepository.findReveredSeats(concertScheduleId);
        reservedSeat.forEach(i -> {
            if (i.getState() > AVAIL) seatState.put(i.getSeatNo(), TEMP_RESERVED);
        });
        return seatState;
    }

    @Transactional
    public ReservationResult reserve(ReservationCommand command) {
        ReservationEntity reservation = validateSeat(command);
        try { // FIXME : 이 중복 예약 방지 예외처리는 service 에서 하는게 맞나? ,repository 에서 하는게 맞나? -> 일단 내 생각은 비즈니스 로직에서 처리하는게 맞는거 같다.
            ReservationEntity saved = reservationRepository.save(reservation.update(command));
            return new ReservationResult(command.getConcertId(), saved);
        }catch (DataIntegrityViolationException e) {
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT3);
        }catch (OptimisticLockException e) {
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT4);
        }
    }

    //FIXME : 로직이 뭔가 별로이다.
    public ReservationEntity validateSeat(ReservationCommand command) {
        if(seatState.containsKey(command.getSeatNo()) == false)
            throw new CustomException(CustomException.ErrorEnum.NO_SEAT);

        Optional<ReservationEntity> reservationEntity = reservationRepository.findReserveInfo(command.getConcertDescId(), command.getSeatNo());
        if (reservationEntity.isEmpty()){
            return ReservationEntity.builder().build();
        }
        ReservationEntity entity = reservationEntity.get();
        if (entity.getStatus() == TEMP_RESERVED || entity.getStatus() == RESERVED) { // 임시예약 || 예약확정
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT2);
        }
        return reservationEntity.get();
    }

    public ReservationEntity findTempRevervation(Long concertDescId, Integer seatNo, Integer status) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findReserveInfo(concertDescId, seatNo, status);
        if (reservationEntity.isEmpty()) {
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT);
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

    public void expireReservation() {
        List<ReservationEntity> list = reservationRepository.findRevervationsByStatus(TEMP_RESERVED);
        list.stream().filter(i -> i.checkExpired()).forEach(i -> reservationRepository.save(i));
    }
}