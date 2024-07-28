package com.hh99.hh5concertreservation.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.ConcertController;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.dto.ConcertSchedulesResponse;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.dto.ReftSeatsResponse;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.dto.ReservationRequest;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.dto.ReservationResponse;
import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ConcertController.class)
@ExtendWith(SpringExtension.class)
class ConcertControllerTest {

    @MockBean
    private ConcertService concertService;
    @MockBean
    private QueueService queueService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    
    Long userId = 1L;
    Long concertId = 10L;
    Long concertDescId = 11L;
    Integer seatNo = 5;
    Integer reservationState = 1;

    @DisplayName("success : 콘서트 스케줄 조회")
    @Test
    void findSchedule() throws Exception {
        //given
        Long concertId = 10L;
        List<ConcertScheduleInfo> schedules = new ArrayList<>();
        schedules.add(new ConcertScheduleInfo(11L, "2024-08-12 18:00:00"));
        given(concertService.findSchedules(concertId)).willReturn(schedules);
        given(queueService.isValidate(anyString())).willReturn(false);

        //when
        ResultActions resultActions = mvc.perform(get("/concert/schedules")
//                        .header("Token", "token1")
//                        .header("UserId", "100")
                .param("concertId", concertId.toString())
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ConcertSchedulesResponse response = mapper.readValue(contentAsString, ConcertSchedulesResponse.class);
        assert concertId == response.getConcertId();
        response.getSchedules().forEach(i -> System.out.println(i.toString()));
        assert schedules.size() == response.getSchedules().size();
    }

    @DisplayName("success : 특정 콘서트 일자의 좌석 현황 조회")
    @Test
    void findSeatsState() throws Exception {
        //given
        Long concertDescId = 15L;
        List<ConcertScheduleInfo> schedules = new ArrayList<>();
        Map<Integer, Integer> reservationState = new HashMap<>(50);
        for (int i = 1; i <= 50; i++) {
            reservationState.put(i,0);
        }
        reservationState.put(1, 1);
        reservationState.put(2, 1);
        reservationState.put(3, 1);
        reservationState.put(5, 1);

        given(concertService.findLeftSeats(concertDescId)).willReturn(reservationState);

        //when
        ResultActions resultActions = mvc.perform(get("/concert/seats/state")
//                        .header("Token", "token1")
//                        .header("UserId", "100")
                        .param("concertScheduleId", concertDescId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ReftSeatsResponse response = mapper.readValue(contentAsString, ReftSeatsResponse.class);
        assert concertDescId == response.getConcertDescId();
        assert reservationState.equals(response.getSeatStates());
    }

    @DisplayName("success : 콘서트 자리 예약 성공")
    @Test
    void reserve() throws Exception {
        //given
        ReservationRequest request = new ReservationRequest(userId, concertId, concertDescId, seatNo);
        ReservationResult result = new ReservationResult(1L,concertId, concertDescId, seatNo, reservationState);
        given(concertService.reserve(any())).willReturn(result);
        //when
        ResultActions resultActions = mvc.perform(post("/concert/reservation")
//                        .header("Token", "token1")
//                        .header("UserId", "100")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ReservationResponse response = mapper.readValue(contentAsString, ReservationResponse.class);
        assert concertDescId == response.getConcertDescId();
        assert reservationState == response.getReservationState();

    }
//    @DisplayName("success : 결제 성공")
//    @Test
//    void payment() throws Exception {
//        //given
//        ReservationRequest request = new ReservationRequest(userId, concertId, concertDescId, seatNo);
////        PaymentResponse result = new PaymentResponse(userId, concertId, concertDescId, seatNo, reservationState);
//        given(concertService.payment(any())).willReturn(null);
//
//
//
//        //when
//        ResultActions resultActions = mvc.perform(post("/concert/payment")
//                //                        .header("Token", "token1")
//                //                        .header("UserId", "100")
//                .content(mapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON)
//        );
//        // then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//        PaymentResponse response = mapper.readValue(contentAsString, PaymentResponse.class);
//    }
}
