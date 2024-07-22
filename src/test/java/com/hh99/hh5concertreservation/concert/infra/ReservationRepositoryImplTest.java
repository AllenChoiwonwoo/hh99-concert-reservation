package com.hh99.hh5concertreservation.concert.infra;

import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationRepositoryImplTest {
    @Autowired
    private ReservationRepositoryImpl repo;

    ReservationEntity book1;
    ReservationEntity book2;

    @BeforeEach
    void beforeAll() {
        book1 = ReservationEntity.builder()
                .concertId(10l)
                .concertOptionId(100L)
                .userId(1L)
                .seatNo(1)
                .status(1)
                .price(100000)
                .build();
        book2 = ReservationEntity.builder()
                .concertId(10l)
                .concertOptionId(100L)
                .userId(2L)
                .seatNo(3)
                .status(2)
                .price(100000)
                .build();

        repo.save(book1);
        repo.save(book2);
    }
    @Test
    void findReveredSeats() {
        //when
        List<SeatsInfo> reveredSeats = repo.findReveredSeats(100L);
        //then
        assert reveredSeats.size() == 2;
    }

    @DisplayName("좌석 예약적보 조회 성공")
    @Test
    void success_testFindReserveInfo() {
        //when
        Optional<ReservationEntity> reserveInfo = repo.findReserveInfo(100L, 1);
        //then
        assertTrue(reserveInfo.isPresent());
        assert 1 == reserveInfo.get().getSeatNo();

        Optional<ReservationEntity> reserveInfo1 = repo.findReserveInfo(100L, 1, 1);
        assertTrue(reserveInfo1.isPresent());
        assert 1 == reserveInfo1.get().getSeatNo();
    }

    @DisplayName("좌석 예약적보 조회 실패")
    @Test
    void fail_testFindReserveInfo() {
        //when
        Optional<ReservationEntity> emptyReserveInfo = repo.findReserveInfo(100L, 2);
        Optional<ReservationEntity> reserveInfo2 = repo.findReserveInfo(100L, 3, 1);
        //then
        assertFalse(emptyReserveInfo.isPresent());
        assert reserveInfo2.isPresent() == false;
    }


@DisplayName("success 임시 예악 상태인 예약만 조회")
    @Test
    void success_findAllTempRevervation() {

        // when
        List<ReservationEntity> allTempRevervation = repo.findRevervationsByStatus(1);
        //then
        assert allTempRevervation.size() == 1;
        assert allTempRevervation.get(0).getStatus() == 1;
        assert allTempRevervation.get(0).getConcertOptionId() == 100L;
    }
}