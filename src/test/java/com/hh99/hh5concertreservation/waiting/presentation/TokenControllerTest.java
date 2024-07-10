package com.hh99.hh5concertreservation.waiting.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenResult;
import com.hh99.hh5concertreservation.waiting.application.usecase.QueueUsecase;
import com.hh99.hh5concertreservation.waiting.presentation.dto.TokenRequest;
import com.hh99.hh5concertreservation.waiting.presentation.dto.TokenResponse;
import com.hh99.hh5concertreservation.waiting.presentation.dto.WaitingOrderRequest;
import com.hh99.hh5concertreservation.waiting.presentation.dto.WaitingOrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TokenController.class})
@AutoConfigureMockMvc
class TokenControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    QueueUsecase queueUsecase;
    ObjectMapper mapper = new ObjectMapper();

    private Long userId = 10l;

    @DisplayName("success : 대기열 추가 성공")
    @Test
    void createToken() throws Exception {
        //given
        CreateTokenResult result = CreateTokenResult.builder().userId(userId).token("token1").build();
        TokenRequest tokenRequest = new TokenRequest(userId);
        given(queueUsecase.addWaitlist(any())).willReturn(result);
        //when
        ResultActions resultActions = mvc.perform(post("/token")
                .content(mapper.writeValueAsString(tokenRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        TokenResponse response = mapper.readValue(contentAsString, TokenResponse.class);
        assert response.getToken().equals(result.getToken());
    }

    @DisplayName("success : 대기열 순번 확인 - 대기번호 200번인 유저의 요청에, 아직 100번째 라고 응답")
    @Test
    void checkState() throws Exception {
        Long watingCount = 100l;
        WaitingOrderRequest request = new WaitingOrderRequest(userId, 200l, "token1", 0);
//        CheckStateCommand command = CheckStateCommand.builder().build();
        CheckStateResult result = CheckStateResult.builder()
                .waitingCount(watingCount)
                .status(0)
                .build();

        given(queueUsecase.checkWaitingStatus(any())).willReturn(result);
        //when
        ResultActions resultActions = mvc.perform(post("/token/check")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        WaitingOrderResponse response = mapper.readValue(contentAsString, WaitingOrderResponse.class);
        assert watingCount == response.getWaitingCount();
    }
}