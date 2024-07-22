package com.hh99.hh5concertreservation.integration;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReserveIntegrationTest {
    @Autowired
    private ConcertService concertService;
    @Autowired
    private IReservationRepository repository;
    // ANSI escape codes
    private static final String RESET = "\033[0m"; // Reset color
    private static final String GREEN = "\033[0;32m"; // Green
    private static final String ORANGE = "\033[0;33m"; // Orange (Note: Standard ANSI does not support orange, but yellow is commonly used)



    ReservationCommand command = new ReservationCommand(1L, 1L, 2L, 5);



    @DisplayName("success 콘서트 예약 통합 테스트")
    @Test
    void reserveConcert() {
        // when
        ReservationResult reserve = concertService.reserve(command);

        // then
        assert Objects.nonNull(reserve);
        assert command.getSeatNo() == reserve.getSeatNo();
    }

    @DisplayName("success 콘서트 예약 통합 테스트2")
    @Test
    void reserveConcert2() {
        //given
        ReservationEntity entity = ReservationEntity.builder()
                .userId(10L)
                .concertId(1L)
                .concertOptionId(2L)
                .seatNo(5)
                .expiredAt(System.currentTimeMillis() - (6 * 60 * 1000))
                .price(100000)
                .status(0)
                .build();
        repository.save(entity);

        // when
        ReservationResult reserve = concertService.reserve(command);

        // then
        assert Objects.nonNull(reserve);
        assert command.getSeatNo() == reserve.getSeatNo();
    }

    @DisplayName("fail 이. 선. 좌")
    @Test
    void failTest() {
        //given
        concertService.reserve(command);
        //when, then
        ReservationCommand command2 = new ReservationCommand(2L, 1L, 2L, 5);
        CustomException exception = assertThrows(CustomException.class, () -> concertService.reserve(command2));
        assertEquals("2003", exception.getCode());
    }

    @DisplayName("success 예약 동시성 테스트")
    @Test
    void success_synchronizeReserveTest() {
        Long startTime = System.currentTimeMillis();

        ReservationEntity entity = ReservationEntity.builder()
                .userId(10L)
                .concertId(1L)
                .concertOptionId(2L)
                .seatNo(5)
                .expiredAt(System.currentTimeMillis() - (6 * 60 * 1000))
                .price(100000)
                .status(0)
                .build();
        repository.save(entity);


        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (long i = 1; i <= 200L; i++) {
            long userId = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                ReservationCommand command = new ReservationCommand(userId, 1L, 2L, 5);
                try {
                    ReservationResult reserve = concertService.reserve(command);
                    System.out.println(GREEN  + Thread.currentThread().getId()+ " code : " +0000L +" - reservae : " + reserve.toString());
                }catch (CustomException e){
                    System.out.println(ORANGE + Thread.currentThread().getId() +" code : " + e.getCode() + " - ERROR    : " + e.getMessage());
//                    e.printStackTrace();
                }catch (Exception e){
                    System.out.println(ORANGE+ Thread.currentThread().getId() +" code : " + 9999L + " - ERROR    : " + e.getMessage());
                }
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try{
            allOf.get();
            System.out.println("-=-----------------");
            System.out.println("All tasks completed.");
        } catch (Exception e) {
            e.printStackTrace();
//            assert
        }

        ReservationCommand command = new ReservationCommand(9999L, 1L, 2L, 5);
        try {
            ReservationResult reserve = concertService.reserve(command);
            System.out.println(GREEN+ Thread.currentThread().getId() +" code : " + 0000L + " - reservae : " + reserve.toString());
        }catch (CustomException e){
            System.out.println(ORANGE+ Thread.currentThread().getId() +" code : " + e.getCode() + " - ERROR    : " + e.getMessage());
//                    e.printStackTrace();
        }catch (Exception e){
            System.out.println(ORANGE+ Thread.currentThread().getId() +" code : " + 9999L + " - ERROR    : " + e.getMessage());
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("소요시간 : "+ (endTime - startTime));



    }
}
