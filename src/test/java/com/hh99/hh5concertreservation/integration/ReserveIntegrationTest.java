package com.hh99.hh5concertreservation.integration;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IReservationRepository;
import com.hh99.hh5concertreservation.concert.infra.ReservationJpaRepository;
import com.hh99.hh5concertreservation.test.application.DumyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReserveIntegrationTest {
    @Autowired
    private ConcertService concertService;
    @Autowired
    private IReservationRepository repository;
    @Autowired
    private DumyService dumyService;
    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // ANSI escape codes
    private static final String RESET = "\033[0m"; // Reset color
    private static final String GREEN = "\033[0;32m"; // Green
    private static final String ORANGE = "\033[0;33m"; // Orange (Note: Standard ANSI does not support orange, but yellow is commonly used)

    private static final String RED = "\033[0;31m";

    Logger logger = LoggerFactory.getLogger(ReserveIntegrationTest.class);



    ReservationCommand command = new ReservationCommand(1L, 1L, 2L, 5);
    Random random = new Random();




    @DisplayName("success 콘서트 예약 통합 테스트")
    @Test
    void reserveConcert() {
        // when
        ReservationResult reserve = concertService.reserve(command);

        // then
        assert Objects.nonNull(reserve);
        assert command.getSeatNo() == reserve.getSeatNo();
    }

    @DisplayName("success 콘서트 예약 통합 테스트2")
    @Test
    void reserveConcert2() {
        //given
        ReservationCommand command = ReservationCommand
                .builder()
                .userId(11L)
                .concertId(5L)
                .concertDescId(13L)
                .seatNo(8)
                .price(100000)
                .build();
//        ReservationEntity entity = ReservationEntity.builder()
//                .userId(11L)
//                .concertId(5L)
//                .concertOptionId(1L)
//                .seatNo(8)
//                .expiredAt(System.currentTimeMillis() - (6 * 60 * 1000))
//                .price(100000)
//                .status(0)
//                .build();

        // when
        ReservationResult reserve = concertService.reserve(command);

        // then
        assert Objects.nonNull(reserve);
        assert command.getSeatNo() == reserve.getSeatNo();
    }

    @DisplayName("fail 이. 선. 좌")
    @Test
    void failTest() {
        //given
        concertService.reserve(command);
        //when, then
        ReservationCommand command2 = new ReservationCommand(2L, 1L, 2L, 5);
        CustomException exception = assertThrows(CustomException.class, () -> concertService.reserve(command2));
        assertEquals("2003", exception.getCode());
    }

    @DisplayName("success 예약 동시성 테스트")
    @Test
    void success_synchronizeReserveTest() {
        System.out.println("시작  ( 트렌젝션 범외 : 전체 , 락 : - )");
        Long startTime = System.currentTimeMillis();


        ReservationEntity entity = ReservationEntity.builder()
                .userId(10L)
                .concertId(1L)
                .concertOptionId(1L)
                .seatNo(5)
                .expiredAt(System.currentTimeMillis() - (6 * 60 * 1000))
                .price(100000)
                .status(0)
                .build();

        ReservationEntity tempRevervation;
//        try {
//            tempRevervation = concertService.findTempRevervationOrThrow(2L, 5, 1);
//            tempRevervation.setStatus(0);
//            repository.save(tempRevervation);
//        }catch (CustomException e){
//            System.out.println("������생 : " + e.getMessage());
//            tempRevervation = repository.save(entity);
//        }
//        tempRevervation = repository.save(entity);

        List<ReservationCommand> commands = new ArrayList<>();
        AtomicInteger successCount = new AtomicInteger(0);


        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (long i = 1; i <= 3000L; i++) {
            long userId = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                int randomSeatNo = (int) (Math.random() * 3000) + 1;
                long randomUserId = (long) (Math.random() * 6000) + 1;
                ReservationCommand command = new ReservationCommand(randomUserId, 1L, 1L, randomSeatNo);
                try {
                    ReservationResult reserve = concertService.reserve(command);
                    successCount.incrementAndGet();
                    logger.info(GREEN +"++++ " + Thread.currentThread().getId()+ " code : " +0000L +" - user "+command.getUserId()+" reservae : " + reserve.toString());
                }catch (CustomException e){
                    logger.info(RED +"++++ "+ Thread.currentThread().getId() +" code : " + e.getCode() + " - ERROR    : " + e.getMessage());
                }catch (Exception e){
                    logger.info(ORANGE +"++++ "+ Thread.currentThread().getId() +" code : " + 9999L + " - ERROR    : " + e.getMessage());
                }
            }).exceptionally(ex -> {
                logger.info(ORANGE+ Thread.currentThread().getId() +" code : " + 66666L + " - ERROR RRRRR    : " + ex.getMessage());
                return null;
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try{
            allOf.get();
            System.out.println("-=-----------------");
            System.out.println("All tasks completed. , success = "+successCount.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Optional<ReservationEntity> reserveInfo = repository.findReserveInfo(tempRevervation.getConcertOptionId(), tempRevervation.getSeatNo(), 1);
//        logger.info("성공한 예약 정보  : "+ reserveInfo.get().toString());
        Long endTime = System.currentTimeMillis();
        System.out.println("끝 : 소요시간 : "+ (endTime - startTime));

//        assert tempRevervation.getUserId() != reserveInfo.get().getUserId();
    }

    @DisplayName("더미 예약 데이터 만들기")
    @Test
    public void makeTempReservation() throws InterruptedException {
        for (long i = 11L; i <= 27L; i++) {
            /*
            concertOptionId => concertId
            1 => 1
            2 => 1
            3 => 1
            4 => 2
            5 => 2
            6 => 2
             */
            long concertOptionId = i;
            long concertId = ((concertOptionId-1) / 3 ) + 1;
            logger.info("concertId : {}, concertOptionId: {}", concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
            concurrentReserveFunction(concertId, concertOptionId);
        }
//        for (long i = 1L; i <= 27L; i++) {
//            /*
//            concertOptionId => concertId
//            1 => 1
//            2 => 1
//            3 => 1
//            4 => 2
//            5 => 2
//            6 => 2
//             */
//            long concertOptionId = i;
//            long concertId = ((concertOptionId-1) / 3 ) + 1;
//            logger.info("concertId : {}, concertOptionId: {}", concertId, concertOptionId);
//            concurrentReserveFunction(concertId, concertOptionId);
//        }
    }


    public void concurrentReserveFunction(Long concertId, Long optionId) {
        logger.info("시작  ( 트렌젝션 범외 : 전체 , 락 : - ) concertId :{}, concertOptionId: {}",concertId, optionId);
        Long startTime = System.currentTimeMillis();

        List<ReservationCommand> commands = new ArrayList<>();
        AtomicInteger successCount = new AtomicInteger(0);


        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (long i = 1; i <= 200L; i++) {
            long userId = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                int randomSeatNo = (int) (Math.random() * 50000) + 1;
                long randomUserId = (long) (Math.random() * 60000) + 1;
//                logger.warn("concert_id: {}, option_Id: {}, seat_no: {}, user_id: {} ", concertId, optionId, randomSeatNo, randomUserId);
                ReservationCommand command = new ReservationCommand(randomUserId, concertId, optionId, randomSeatNo);
                try {
                    ReservationResult reserve = concertService.reserve(command);
                    successCount.incrementAndGet();
                    logger.info(GREEN +"++++ " + Thread.currentThread().getId()+ " code : " +0000L +" - user "+command.getUserId()+" reservae : " + reserve.toString());
                }catch (CustomException e){
                    logger.info(RED +"++++ "+ Thread.currentThread().getId() +" code : " + e.getCode() + " - ERROR    : " + e.getMessage());
                }catch (Exception e){
                    logger.info(ORANGE +"++++ "+ Thread.currentThread().getId() +" code : " + 9999L + " - ERROR    : " + e.getMessage());
                }
            }).exceptionally(ex -> {
//                logger.info(ORANGE+ Thread.currentThread().getId() +" code : " + 66666L + " - ERROR RRRRR    : " + ex.getMessage());
                return null;
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try{
            allOf.get();
            System.out.println("-=-----------------");
            System.out.println("All tasks completed. , success = "+successCount.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("끝 : 소요시간 : "+ (endTime - startTime));

    }
    @Test
    public void testGenerateDummyData() {

        Long startTime = System.currentTimeMillis();

        AtomicInteger successCount = new AtomicInteger(0);


        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (long i = 1; i <= 50L; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

                for (int j = 1; j < 60000; j++) {

                    Long concertOptionId = (long) (j + 1);
                    long concertId = ((concertOptionId - 1) / 5) + 1;

                    Integer[] prices = {100000, 150000, 200000, 250000, 300000};

                    ReservationEntity reservation = ReservationEntity.builder()
                            .userId((long) (random.nextInt(60000) + 1))
                            .concertId(concertId)
                            .concertOptionId(concertOptionId)
                            .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                            .price(prices[random.nextInt(prices.length)])
                            .seatNo(j)
                            .status(random.nextInt(3))
                            .build();

                    successCount.incrementAndGet();
                }
            }).exceptionally(ex -> {
                return null;
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try{
            allOf.get();
            System.out.println("-=-----------------");
            System.out.println("All tasks completed. , success = "+successCount.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("끝 : 소요시간 : "+ (endTime - startTime));

//        Runnable dataGenerator = () -> {
//            dumyService.generateDummyData(1000, 10_000_000); // 1000만 건의 데이터 생성
//        };

//        Thread dataGenThread = new Thread(dataGenerator);
//        dataGenThread.start();

//        try {
//            dataGenThread.join(); // 데이터 생성이 완료될 때까지 기다림
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("Data generation completed.");
    }

    @Test
//    @Transactional
    @Commit
    void makeDumy2(){
        int batchSize = 1000;
        List<ReservationEntity> batchList = new ArrayList<>(batchSize);
        Integer[] prices = {100000, 150000, 200000, 250000, 300000};



        for (int i = 1; i <= 3_000_000; i++) {

            int seatNo = i;
            Long concertOptionId = (long) (i / 100_000) + 1;
            long concertId = ((concertOptionId) / 10) + 1;

            ReservationEntity reservation = ReservationEntity.builder()
                    .userId((long) (random.nextInt(10_000_000) + 1))
                    .concertId(concertId)
                    .concertOptionId(concertOptionId)
                    .seatNo(seatNo)
                    .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                    .price(prices[random.nextInt(prices.length)])
                    .status(random.nextInt(3))
                    .build();

            batchList.add(reservation);

            if (i % batchSize == 0) {
                System.out.println(i % batchSize);
                reservationJpaRepository.saveAll(batchList);
//                entityManager.flush();
//                entityManager.clear();
                batchList.clear();
                System.out.println("Inserted " + i + " records.");
            }
        }

        // Remaining records
        if (!batchList.isEmpty()) {
            reservationJpaRepository.saveAll(batchList);
//            entityManager.flush();
//            entityManager.clear();
        }
    }

    @Test
    void csvmakeer() {
        try (FileWriter writer = new FileWriter("allen.csv")) {
            // CSV Header (Optional)
            writer.append("concert_id,concert_option_id,expired_at,price,seat_no,status,user_id,version\n");

            int batchSize = 10000;
            List<ReservationEntity> batchList = new ArrayList<>(batchSize);
            Integer[] prices = {100000, 150000, 200000, 250000, 300000};



            for (int i = 1; i <= 100; i++) {

                int seatNo = i;
                Long concertOptionId = (long) (i / 100_000) + 1;
                long concertId = ((concertOptionId) / 10) + 1;

                ReservationEntity reservation = ReservationEntity.builder()
                        .userId((long) (random.nextInt(1_000_000) + 1))
                        .concertId(concertId)
                        .concertOptionId(concertOptionId)
                        .seatNo(seatNo)
                        .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                        .price(prices[random.nextInt(prices.length)])
                        .status(random.nextInt(3))
                        .version(0l)
                        .build();
                writer.append(reservation.getConcertId() + ",")
                        .append(reservation.getConcertOptionId() + ",")
                        .append(reservation.getExpiredAt() + ",")
                        .append(reservation.getPrice() + ",")
                        .append(reservation.getSeatNo() + ",")
                        .append(reservation.getStatus() + ",")
                        .append(reservation.getUserId() + ",")
                        .append(reservation.getVersion() +"\n")
                ;



            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("CSV file created successfully.");
    }

    @Test
    @Transactional
    @Commit
    void makeDumy3(){
        int batchSize = 1000;
        List<ReservationEntity> batchList = new ArrayList<>(batchSize);
        Integer[] prices = {100000, 150000, 200000, 250000, 300000};



        for (int i = 3_000_001; i <= 6_000_000; i++) {

            int seatNo = i;
            Long concertOptionId = (long) (i / 100_000) + 1;
            long concertId = ((concertOptionId) / 10) + 1;

            ReservationEntity reservation = ReservationEntity.builder()
                    .userId((long) (random.nextInt(10_000_000) + 1))
                    .concertId(concertId)
                    .concertOptionId(concertOptionId)
                    .seatNo(seatNo)
                    .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                    .price(prices[random.nextInt(prices.length)])
                    .status(random.nextInt(3))
                    .build();

            batchList.add(reservation);

            if (i % batchSize == 0) {
                reservationJpaRepository.saveAll(batchList);
                entityManager.flush();
                entityManager.clear();
                batchList.clear();
                System.out.println("Inserted " + i + " records.");
            }
        }

        // Remaining records
        if (!batchList.isEmpty()) {
            reservationJpaRepository.saveAll(batchList);
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Test
    @Transactional
    @Commit
    void makeDumy4(){
        int batchSize = 1000;
        List<ReservationEntity> batchList = new ArrayList<>(batchSize);
        Integer[] prices = {100000, 150000, 200000, 250000, 300000};



        for (int i = 6_000_001; i <= 10_000_000; i++) {

            int seatNo = i;
            Long concertOptionId = (long) (i / 100_000) + 1;
            long concertId = ((concertOptionId) / 10) + 1;

            ReservationEntity reservation = ReservationEntity.builder()
                    .userId((long) (random.nextInt(10_000_000) + 1))
                    .concertId(concertId)
                    .concertOptionId(concertOptionId)
                    .seatNo(seatNo)
                    .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                    .price(prices[random.nextInt(prices.length)])
                    .status(random.nextInt(3))
                    .build();

            batchList.add(reservation);

            if (i % batchSize == 0) {
                reservationJpaRepository.saveAll(batchList);
                entityManager.flush();
                entityManager.clear();
                batchList.clear();
                System.out.println("Inserted " + i + " records.");
            }
        }

        // Remaining records
        if (!batchList.isEmpty()) {
            reservationJpaRepository.saveAll(batchList);
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Test
    @Transactional
    @Commit
    void makeDummyData() throws InterruptedException {
        int totalRecords = 10_000_000;
        int batchSize = 500;
        int threadCount = 10;  // 병렬 처리할 스레드 수

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);  // 모든 스레드가 끝날 때까지 기다리기 위해 사용

        int recordsPerThread = totalRecords / threadCount;

        for (int t = 0; t < threadCount; t++) {
            final int startIdx = t * recordsPerThread;

            executorService.submit(() -> {
                List<ReservationEntity> batchList = new ArrayList<>(batchSize);
                Integer[] prices = {100000, 150000, 200000, 250000, 300000};

                for (int i = startIdx; i < startIdx + recordsPerThread; i++) {
                    int seatNo = i + 1; // seatNo는 1부터 시작하므로 +1을 더함
                    Long concertOptionId = (long) (seatNo / 100_000) + 1;
                    long concertId = ((concertOptionId) / 10) + 1;

                    ReservationEntity reservation = ReservationEntity.builder()
                            .userId((long) (random.nextInt(10_000_000) + 1))
                            .concertId(concertId)
                            .concertOptionId(concertOptionId)
                            .seatNo(seatNo)
                            .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                            .price(prices[random.nextInt(prices.length)])
                            .status(random.nextInt(3))
                            .build();

                    batchList.add(reservation);

                    if (batchList.size() == batchSize) {
                        reservationJpaRepository.saveAll(batchList);
                        entityManager.flush();
                        entityManager.clear();
                        batchList.clear();
                    }
                    logger.info(""+startIdx+ " ~ " + (startIdx + recordsPerThread));
                }

                // 남은 데이터 저장
                if (!batchList.isEmpty()) {
                    reservationJpaRepository.saveAll(batchList);
                    entityManager.flush();
                    entityManager.clear();
                }

                latch.countDown();  // 현재 스레드가 종료되었음을 알림
            });
        }

        latch.await();  // 모든 스레드가 종료될 때까지 대기
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS); // 적당한 타임아웃 설정
        System.out.println("All tasks completed.");
    }



    @Test
    @Transactional
    @Commit
    void insertOne() {
        ReservationEntity reservation = ReservationEntity.builder()
                .userId((long) (random.nextInt(10_000_000) + 1))
                .concertId(1L)
                .concertOptionId(10L)
                .seatNo(100)
                .expiredAt(System.currentTimeMillis() + random.nextLong(7 * 24 * 60 * 60 * 1000))
                .price(100000)
                .status(random.nextInt(3))
                .build();

        reservationJpaRepository.save(reservation);
    }
}