package com.hh99.hh5concertreservation.concert.domain;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.concert.domain.dto.ConcertScheduleInfo;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertEntity;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertOption;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IReservationRepository;
import com.hh99.hh5concertreservation.concert.infra.ConcertRedisRepository;
import com.hh99.hh5concertreservation.concert.interfaces.presentation.ConcertOptionCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Slf4j
@Component
public class ConcertService {
    private final IConcertRepository concertRepository;
    private final IReservationRepository reservationRepository;
//    private final Map<Integer, Integer> seatState;
    private final int AVAIL = 0;
    private final int TEMP_RESERVED = 1;
    private final int RESERVED = 2;

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ConcertRedisRepository redis;


    public ConcertService(IConcertRepository concertRepository, IReservationRepository reservationRepository) {
        this.concertRepository = concertRepository;
        this.reservationRepository = reservationRepository;
//        this.seatState = new HashMap<>(50);
//        for (int i = 1; i <= 50; i++) {
//            seatState.put(i,AVAIL);
//        }
    }
    @Cacheable(value = "concert_info", key = "#concertId")
    public List<ConcertScheduleInfo> findSchedules(Long concertId) {
        List<ConcertScheduleInfo> concert = concertRepository.findSchedules(concertId);
        return concert;
    }
    public Map<Integer, Integer> findSeatsStatesBySchedule(Long concertScheduleId) {
        Optional<Map<Integer, Integer>> cache = redis.findCachedReservedSeatsByScheduleId(concertScheduleId);
        if (cache.isPresent()) return cache.get();

        ConcertOption concertOption = concertRepository.findConcertOptionById(concertScheduleId);
        Map<Integer, Integer> newSeatStatus = new HashMap<>(concertOption.getTicketAmount());
        List<SeatsInfo> reservedSeat = reservationRepository.findReveredSeats(concertScheduleId);
        reservedSeat.forEach(i -> {
            if (i.getState() > AVAIL) newSeatStatus.put(i.getSeatNo(), TEMP_RESERVED);
        });
        redis.putCacheReservedSeats(concertScheduleId, newSeatStatus);
        return newSeatStatus;
    }
    private static final String YELLOW = "\033[0;33m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String WHITE = "\033[0;37m";
    private static final String BLACK = "\033[0;30m";

    static String[] colors = {YELLOW, PURPLE, CYAN, WHITE, BLACK};

    public static void logTransactionStatus(String message) {
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        int index = (int) (Thread.currentThread().getId() % 3);
        String newColor = colors[index];
        log.info(newColor + " thread: "+Thread.currentThread().getId()+" TxActive: " + isActive + ", message: " + message);
    }

    public ReservationResult reserve(ReservationCommand command) {

        return transactionTemplate.execute(status -> {
            try {
                // 트랜잭션 안에서 수행할 작업
//                logTransactionStatus("1 reservation. start");
                ReservationEntity reservation = validateSeat(command);

                try { // FIXME : 이 중복 예약 방지 예외처리는 service 에서 하는게 맞나? ,repository 에서 하는게 맞나? -> 일단 내 생각은 비즈니스 로직에서 처리하는게 맞는거 같아요.
//                    logTransactionStatus("3 reservation. save reservation start");
                    ReservationEntity saved = reservationRepository.save(reservation.update(command));
//                    logTransactionStatus("4 reservation. save reservation end");
                    redis.putCachedSeatReservationState(command.getConcertDescId(), command.getSeatNo());

                    return new ReservationResult(command.getConcertId(), saved);
                } catch (DataIntegrityViolationException e) {
//                    logTransactionStatus("5. reserve. throw DataIntegrityViolationException");
                    throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT3);
                } catch (ObjectOptimisticLockingFailureException e) {
//                    logTransactionStatus("5. reserve. throw OptimisticLockException");
                    throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT4);
                }
            } catch (Exception e) {
                // 예외가 발생하면 롤백
                status.setRollbackOnly();
                throw e; // 예외를 다시 던져서 상위에서 처리할 수 있게 함
            }
        });
    }

    //FIXME : 로직이 뭔가 별로이다.
    public ReservationEntity validateSeat(ReservationCommand command) {
//        logTransactionStatus( "1-1 validateSeat. right seat check");

//        if(seatState.containsKey(command.getSeatNo()) == false){
//            logTransactionStatus( "1-1-1 validateSeat. throw NO_SEAT");
//            throw new CustomException(CustomException.ErrorEnum.NO_SEAT);
//        }
        if(redis.findCachedSeatReservationState(command.getConcertDescId(), command.getSeatNo())){
//            logTransactionStatus( "1-1-2 validateSeat. throw RESERVED_SEAT");
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT);
        }
//        logTransactionStatus( "1-2 validateSeat. reservationRepository.findReserveInfo #LOCK###");
        Optional<ReservationEntity> reservation = reservationRepository.findReserveInfo(command.getConcertDescId(), command.getSeatNo());

//        logTransactionStatus( "1-5 validateSeat. check available seat ");

        if (reservation.isEmpty()){
//            logTransactionStatus( "1-5-1 validateSeat. available");
            return ReservationEntity.builder().build();
        }
//        logTransactionStatus( "1-6 validateSeat. check reserved seat");
        ReservationEntity entity = reservation.get();
        if (entity.getStatus() == TEMP_RESERVED || entity.getStatus() == RESERVED) { // 임시예약 || 예약확정
//            logTransactionStatus( "1-6-1 validateSeat. throw reserved seat");
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT2);
        }
//        logTransactionStatus( "1-7 validateSeat. available");
        return reservation.get();
    }

    public ReservationEntity findTempRevervationOrThrow(Long concertDescId, Integer seatNo, Integer status) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findReserveInfo(concertDescId, seatNo, status);
        if (reservationEntity.isEmpty()) {
            throw new CustomException(CustomException.ErrorEnum.RESERVED_SEAT);
        }
        return reservationEntity.get();
    }

    public Long findPrice(Long concertDescId) {
        return concertRepository.findConcertOptionPrice(concertDescId);
    }

    public ReservationEntity confirmReservation(ReservationEntity reservation) {
        reservation.setConfirm();
        return reservationRepository.save(reservation);
    }

    public void expireReservation() {
        List<ReservationEntity> list = reservationRepository.findRevervationsByStatus(TEMP_RESERVED);
        list.stream().filter(i -> i.checkExpired()).forEach(i -> reservationRepository.save(i));
    }

    public Long addConcert(String name) {
        ConcertEntity concert = new ConcertEntity(name);
        ConcertEntity save = concertRepository.save(concert);
        return save.getId();
    }

    public Long addConcertOption(ConcertOptionCommand command) {
        ConcertEntity concert = concertRepository.findConcertById(command.getConcertId()).orElseThrow(() -> new CustomException(CustomException.ErrorEnum.NO_CONCERT));
        ConcertOption concertOption = new ConcertOption(concert, command);
        ConcertOption save = concertRepository.save(concertOption);
        return save.getId();
    }
}