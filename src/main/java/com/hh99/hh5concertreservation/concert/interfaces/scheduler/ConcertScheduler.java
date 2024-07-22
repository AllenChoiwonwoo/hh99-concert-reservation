package com.hh99.hh5concertreservation.concert.interfaces.scheduler;


import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertScheduler {
    private ConcertService concertService;

    @Scheduled(fixedDelay = 30000)
    public void expireTemporaryReservation(){
        concertService.expireReservation();
    }
}
