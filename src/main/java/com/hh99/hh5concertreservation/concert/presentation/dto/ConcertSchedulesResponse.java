package com.hh99.hh5concertreservation.concert.presentation.dto;

import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ConcertSchedulesResponse {

    private Long concertId;
    private List<ConcertOptionInfo> schedules;

    public ConcertSchedulesResponse(Long concertId, List<ConcertScheduleInfo> schedulesInfo) {
        this.concertId = concertId;
        this.schedules = schedulesInfo.stream().map(i -> new ConcertOptionInfo(i)).collect(Collectors.toList());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class ConcertOptionInfo {
        private Long concertDescId;
        private String datetime;

        public ConcertOptionInfo(ConcertScheduleInfo info) {
            this.concertDescId = info.getConcertOptionId();
            this.datetime = info.getDatetime();
        }
    }
}
