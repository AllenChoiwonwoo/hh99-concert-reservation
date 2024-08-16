package com.hh99.hh5concertreservation.payments.domain.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.PaymentRepository;
import com.hh99.hh5concertreservation.payments.infra.PaymentCompleteEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentCompleteOutboxManager {
    private final ObjectMapper mapper;
    private final PaymentRepository repository;
    private final PaymentEventPublisher publisher;

    public void setPublished(String message) {
        try {
            PaymentCompleteEvent event = mapper.readValue(message, PaymentCompleteEvent.class);
            PaymentCompleteEventEntity entity = repository.findEventByPaymentId(event.getPaymentId());
            repository.updateEventState(entity.published());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String setInit(PaymentCompleteEvent event) {
        try {
            String message = mapper.writeValueAsString(event);
            repository.saveInitEvent(event.getPaymentId(), message);
            return message;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void retry() {
        List<PaymentCompleteEventEntity> events = repository.findAllInitEvents();
        events.stream()
                .map(entity -> new PaymentCompleteEvent(entity, mapper))
                .forEach(event -> publisher.success(event));
    }

    public void removeExpired() {
        List<PaymentCompleteEventEntity> allInitEvents = repository.findAllEvent();
        long yesterday = System.currentTimeMillis() - (1 * 60 * 60 * 1000);
        List<PaymentCompleteEventEntity> expiredEvents = allInitEvents.stream().filter(i -> i.getCreatedAt() < yesterday)
                .map(i -> i.setDelete())
                .collect(Collectors.toList());
        repository.saveAll(expiredEvents);
    }
}
