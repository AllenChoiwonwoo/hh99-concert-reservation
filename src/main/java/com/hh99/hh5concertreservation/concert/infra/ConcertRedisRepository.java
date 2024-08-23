package com.hh99.hh5concertreservation.concert.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ConcertRedisRepository {
    private final RedissonClient redissonClient;
    private final ObjectMapper mapper;

    private final String SEATS_STATE_MAP_KEY = "seatsStateMap";
    private final String SEAT_STATE_KEY = "seatsState";

    private final int SEATS_STATE_TTL = 10;
    private final int SEAT_RESERVATION_STATE_TTL = 1;


    public Optional<Map<Integer, Integer>> findCachedReservedSeatsByScheduleId(Long concertScheduleId) {
        RMap<Integer, Integer> map = redissonClient.getMap(SEATS_STATE_MAP_KEY + ":" + concertScheduleId);
        if (map != null) return Optional.of(map);

        return Optional.empty();
    }

    public void putCacheReservedSeats(Long concertScheduleId, Map<Integer, Integer> seatsStateMap) {
        RBucket<String> bucket = redissonClient.getBucket(SEATS_STATE_MAP_KEY + ":" + concertScheduleId);
        try {
            bucket.set(mapper.writeValueAsString(seatsStateMap), SEATS_STATE_TTL, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("redis exception");
        }
    }

    public Boolean findCachedSeatReservationState(Long concertDescId, Integer seatNo) {
        RBucket<Boolean> bucket = redissonClient.getBucket(SEAT_STATE_KEY + ":" + concertDescId + ":" + seatNo);
        if (bucket != null) return false;
        return bucket.get();
    }

    public void putCachedSeatReservationState(Long concertDescId, Integer seatNo) {
        RBucket<Boolean> bucket = redissonClient.getBucket(SEAT_STATE_KEY + ":" + concertDescId + ":" + seatNo);
        bucket.set(true, SEAT_RESERVATION_STATE_TTL, TimeUnit.DAYS);
    }

    public void putCachedSeatReservationStateExpired(Long concertDescId, Integer seatNo) {
        RBucket<Boolean> bucket = redissonClient.getBucket(SEAT_STATE_KEY + ":" + concertDescId + ":" + seatNo);
        bucket.set(false, SEAT_RESERVATION_STATE_TTL, TimeUnit.DAYS);
    }
}
