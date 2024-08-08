package com.hh99.hh5concertreservation.concert.infra;

import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long>{

    @Query(value = """
    SELECT new com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo(r.concertOptionId, r.seatNo, r.status )
    FROM ReservationEntity r
    WHERE r.concertOptionId = :concertScheduleId
    and r.status > 0
    """)
    List<SeatsInfo> findReveredSeats(@Param("concertScheduleId") Long concertScheduleId);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ReservationEntity> findByConcertOptionIdAndSeatNo(Long concertDescId, Integer seatNo);

    Optional<ReservationEntity> findByConcertOptionIdAndSeatNoAndStatus(Long concertDescId, Integer seatNo, Integer status);

    List<ReservationEntity> findAllByStatus(int status);

    Optional<ReservationEntity> findByConcertIdAndConcertOptionIdAndSeatNo(Long concertId, Long concertOptionId, Integer seatNo);
}
