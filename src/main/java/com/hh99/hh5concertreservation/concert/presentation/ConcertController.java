package com.hh99.hh5concertreservation.concert.presentation;

import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.presentation.dto.ReftSeatsResponse;
import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.presentation.dto.ConcertSchedulesResponse;
import com.hh99.hh5concertreservation.concert.presentation.dto.ReservationRequest;
import com.hh99.hh5concertreservation.concert.presentation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/schedules")
    public ResponseEntity findSchedule(@RequestParam Long concertId){
        List<ConcertScheduleInfo> schedules = concertService.findSchedules(concertId);
        return ResponseEntity.ok(new ConcertSchedulesResponse(concertId, schedules));
    }

    @GetMapping("/seats/state")
    public ResponseEntity findLeftSeat(@RequestParam Long concertScheduleId){
        Map<Integer, Integer> seatsInfos = concertService.findLeftSeats(concertScheduleId);
        return ResponseEntity.ok(new ReftSeatsResponse(concertScheduleId, seatsInfos));
    }

    @PostMapping("/reservation")
    public ResponseEntity reservate(@RequestBody ReservationRequest request){
        ReservationResult result = concertService.reservation(request.toCommand());
        return ResponseEntity.ok(new ReservationResponse(result));
    }
}
