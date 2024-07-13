package com.hh99.hh5concertreservation.concert.domain;

import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;
    @Mock
    private IConcertRepository concertRepository;

    @Test
    void findLeftSeats() {
        //given
        Long concertDescId = 15L;
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
}