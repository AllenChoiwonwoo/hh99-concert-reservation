package com.hh99.hh5concertreservation.concert.interfaces.presentation;

import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.dto.*;
import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.ConcertService;
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

    @GetMapping
    public ResponseEntity addConcert(@RequestParam String name){
        Long concertId = concertService.addConcert(name);
        return ResponseEntity.ok(concertId);
    }

    @PostMapping("/options")
    public ResponseEntity addConcertOption(@RequestBody CreateConcertOptionReq req){
        ConcertOptionCommand command = req.toCommand();
        Long concertOptionId = concertService.addConcertOption(command);
        return ResponseEntity.ok(concertOptionId);
    }

    @GetMapping("/schedules")
    public ResponseEntity findSchedule(@RequestParam Long concertId){
        List<ConcertScheduleInfo> schedules = concertService.findSchedules(concertId);
        return ResponseEntity.ok(new ConcertSchedulesResponse(concertId, schedules));
    }

    @GetMapping("/seats/state")
    public ResponseEntity findSeatsStates(@RequestParam("concert_schedule_id") Long concertScheduleId) {
        Map<Integer, Integer> seatsInfos = concertService.findSeatsStatesBySchedule(concertScheduleId);
        return ResponseEntity.ok(new ReftSeatsResponse(concertScheduleId, seatsInfos));
    }

    @PostMapping("/reservation")
    public ResponseEntity reservate(@RequestBody ReservationRequest request){
        ReservationResult result = concertService.reserve(request.toCommand());
        return ResponseEntity.ok(new ReservationResponse(result));
    }
}
