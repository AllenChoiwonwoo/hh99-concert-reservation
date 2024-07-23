package com.hh99.hh5concertreservation.concert.domain;

import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;
    @Mock
    private IConcertRepository concertRepository;
    @Mock
    private IReservationRepository reservationRepository;
    Long userId = 5L;
    Long concertId = 10L;
    Long concertDescId = 15L;
    int seatNo = 10;
    int price = 100000;


    @Test
    void findLeftSeats() {
        //given
        List<SeatsInfo> seatsInfos = new ArrayList<>();
        seatsInfos.add(new SeatsInfo(concertDescId, 0, 5));
        seatsInfos.add(new SeatsInfo(concertDescId, 1, 6));
        seatsInfos.add(new SeatsInfo(concertDescId, 2, 7));

        given(concertRepository.findReftSeats(concertDescId)).willReturn(seatsInfos);
        //when
        Map<Integer, Integer> result = concertService.findLeftSeats(concertDescId);
        //then
        System.out.println(result);
        assert 0 == result.get(5);
        assert 1 == result.get(6);
        assert 1 == result.get(7);
        assert 0 == result.get(8);
    }

//    @Test
//    void findSchedules() {
//    }

    @DisplayName("success : 임시예약 성공")
    @Test
    void reserve() {
        //given
        ReservationCommand command = new ReservationCommand(userId,concertId, concertDescId, seatNo,price);
        ReservationEntity entity = new ReservationEntity(userId, concertDescId, seatNo,price);
        given(reservationRepository.save(any())).willReturn(entity);
        //when
        ReservationResult result = concertService.reserve(command);
        //then
        assert concertId == result.getConcertId();
        assert 1 == result.getReservationState();
    }

    @DisplayName("fail : 이미 예약된 좌석입니다.")
    @Test
    void checkSeat() {
        //given
        given(reservationRepository.findByConsertOptionIdAndSeatNo(concertDescId, seatNo)).willReturn(Optional.of(new ReservationEntity(userId, concertDescId, seatNo, price)));
        //when
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> concertService.validateSeat(concertDescId, seatNo));
        assertEquals("이미 예약된 좌석입니다.", illegalStateException.getMessage());
    }

    @DisplayName("fail : 존제하지 않는 좌석입니다.")
    @Test
    void validateSeat() {
        //when
        InputMismatchException e = assertThrows(InputMismatchException.class, () -> concertService.validateSeat(concertDescId, seatNo));
        assertEquals("존제하지 않는 좌석입니다.", e.getMessage());
    }
}