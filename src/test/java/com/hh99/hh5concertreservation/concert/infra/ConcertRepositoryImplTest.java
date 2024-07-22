package com.hh99.hh5concertreservation.concert.infra;

import com.hh99.hh5concertreservation.concert.domain.entity.ConcertEntity;
import com.hh99.hh5concertreservation.concert.domain.entity.ConcertOption;
import com.hh99.hh5concertreservation.concert.domain.repositoryInterface.IConcertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConcertRepositoryImplTest {

    @Autowired
    private IConcertRepository concertRepository;

    String concertName = "테스트 콘서트";
    int price = 10000;

//    @BeforeEach
//    void setUp() {
//        ConcertEntity concert = ConcertEntity.builder()
//                        .name("테스트 콘서트")
//                                .build();
//        ConcertEntity result =  concertRepository.save(concert);
//        ConcertOption concertOption = ConcertOption.builder()
//                .concert(result)
//                .price(10000)
//                .schedule("2024-07-22 18:00:00")
//                .ticketAmount(50)
//                .reservationOpenAt(Long.valueOf("2024-07-01 00:00:00"))
//                .build();
//        ConcertOption concertOptionResult = concertRepository.save(concertOption);
//    }

    @DisplayName("콘서트 & 콘서트 옵션의 저장 및 관계 맵핑 테스트")
    @Test
    void saveConcert() {

        ConcertEntity concert = ConcertEntity.builder()
                .name(concertName)
                .build();

        ConcertOption concertOption = ConcertOption.builder()
        .concert(concert)
        .price(price)
        .schedule("2024-07-22 18:00:00")
        .ticketAmount(50)
        .reservationOpenAt(System.currentTimeMillis())
        .build();

        concert.setConcertDescs(List.of(concertOption));
        ConcertEntity result =  concertRepository.save(concert);


        //when
        List<ConcertEntity> concerts = concertRepository.findConcerts();
        System.out.println(concerts.get(0).getConcertDescs());

        //then
        assert concertName.equals(result.getName());
        assertEquals(concertName, concerts.get(0).getConcertDescs().get(0).getConcert().getName());
        assert concerts.get(0).getConcertDescs().get(0).getPrice() == price;
    }

//    @Test
//    void findSchedules() {
//        concertRepository.findSchedules(1L);
//    }
}