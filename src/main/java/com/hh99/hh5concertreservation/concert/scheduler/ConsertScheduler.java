package com.hh99.hh5concertreservation.concert.scheduler;


import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsertScheduler {
    private ConcertService concertService;

    @Scheduled(fixedDelay = 30000)
    public void expireTemporaryReservation(){
        concertService.expireReservation();
    }
}
