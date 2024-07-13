package com.hh99.hh5concertreservation.concert.domain;

import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConcertService {
    private final IConcertRepository concertRepository;
    private final Map<Integer, Integer> seatState;

    public ConcertService(IConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
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
        List<SeatsInfo> reftSeats = concertRepository.findReftSeats(concertScheduleId);
        reftSeats.forEach(i -> {
            if (i.getState() > 0) seatState.put(i.getSeatNo(), 1);
        });
        return seatState;
    }


}
