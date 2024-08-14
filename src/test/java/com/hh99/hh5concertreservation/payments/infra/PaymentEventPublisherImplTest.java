package com.hh99.hh5concertreservation.payments.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PaymentEventPublisherImplTest {

    @Autowired
    private PaymentEventPublisherImpl publisher;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void success() throws ExecutionException, InterruptedException, JsonProcessingException {
        //given
        PaymentCompleteEvent event = PaymentCompleteEvent.builder()
                .paymentId(2L)
                .token("token")
                .reservationId(2L)
                .status(2)
                .amount(20000L)
                .userId(2L)
                .build();

        // When
        CompletableFuture<SendResult<String, String>> future = publisher.success(event);
        SendResult<String, String> result = future.get(); // CompletableFuture 완료 대기

        // Then
        // 1. 이벤트가 성공적으로 발행되었는지 확인
        assertThat(result.getRecordMetadata().topic()).isEqualTo("payment-complete");
        PaymentCompleteEvent paymentCompleteEvent = mapper.readValue(result.getProducerRecord().value(), PaymentCompleteEvent.class);
        assertThat(event.getPaymentId()).isEqualTo(paymentCompleteEvent.getPaymentId()); // toString 또는 JSON 직렬화된 값 확인
    }


}