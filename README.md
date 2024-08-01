# hh99-concert-reservation

<details>
<summary>마일드스톤</summary>

<img width="1681" alt="image" src="https://github.com/AllenChoiwonwoo/hh99-concert-reservation/assets/54317334/9634e767-19a9-49b7-b792-0aa9e2c0cda4">


</details>


<details>
<summary>ERD</summary>

![image](https://github.com/AllenChoiwonwoo/hh99-concert-reservation/assets/54317334/dd68ce3d-9816-454f-96a7-9f997c0db0cc)

</details>

<details>
<summary>시퀀스 다이어그램</summary>

![image](https://github.com/AllenChoiwonwoo/hh99-concert-reservation/assets/54317334/f3e091c9-9caf-4705-b7be-ceeb6a26110e)
![image](https://github.com/AllenChoiwonwoo/hh99-concert-reservation/assets/54317334/c8455153-a850-43be-b79c-0ae5c02f22b6)
![image](https://github.com/AllenChoiwonwoo/hh99-concert-reservation/assets/54317334/d6efd208-7897-421f-9757-f536c982f6e1)

</details>


<details>
<summary>캐시 사용가능 로직(쿼리) 분석
</summary>

## Redis 를 통한 성능 개선가능 API

### 콘서트 정보 조회

- **조회 주기** : 높음
- **변경 주기** : 낮음
- **실시간 성** : 중요
- **결론(Caching &** Strategy**)** : 1. `Redis`  & `데이터 변경시 즉시 캐시 업데이트`
    - **케이스별 고민** :
        1. `Redis`  & `데이터 변경시 즉시 캐시 업데이트`
            1. 레디스에 **콘서트일정**을 저장해놓고 사용한다.
               ”admin이 콘서트일정 정보 변경 시” 레디스 캐시를 업데이트
            2. “콘서트 정보 조회”는 스파이크에서 DB를 사용조차 하지 않게 보호한다.
               또한, 콘서트의 정보가 변경되어 Redis의 데이터가 변경되어야할 일은 스파이크 기간엔 상식적으로 없어야한다.

        2. `Redis` + `로컬캐시` & 유효성 검증 →`DB select & caching & 응답`
            1. 레디스에는 콘서트 일정 변경 플래그(timemile)만 저장
               콘서트 일정을 서버의 로컬 캐시에 담아놓는다.
                1. redis의 데이터를 spring으로 가져와서 ObjectMapping 하는 시간을 줄인다 & 통신비용 을 줄인다.
            2. 콘서트 일정 조회요청이 오면 레디스의 플래그 체크 후
               if: 유효하면 바로 응답
               else: 유효하지 않으면 DB select & caching 업데이트 & 응답
        3. `Read DB (+DB의 캐싱)` & `로컬 캐싱 2초`
            - 로컬 캐싱 2초 : “정보를 잘못 등록해놔서 급하게 콘서트 정보를 업데이트” 했다고 가정했을때 2초 때문에 문제가 커질일은 없을것으로 예상된다. ( 1시간 이슈 == 1시간 2초 이슈 )
            - DB 캐싱 : ‘콘서트 정보 조회’ api 는 조회해야할 데이터가 계속 동일하다.  DB 의 값이 변경되기 전까지는 DB에 캐싱이 잘 동작할 것이다.
            - Active 유저는 제한이 되어있으니 DB가 견딜 수 있다.
            - 레디스도 매우 중요한 자원이기에, 레디스를 위해 Read DB가 좀 더 일하는 것도 방법이라고 생각한다.

### 콘서트 전체 좌석 현황 조회

- **조회 주기** : 매우 높음
- **변경 주기** : 높음
- **실시간 성** : 중상
- **결론** :  4. Redis - List<`SeatsInfo`: String>사용 & 10초마다 업데이트
    - **케이스별 고민** :
        1. `EhCache` - expiration 2초
            1. DB select + ehCache 하게 해서 좌석상태를 최대한 가져가게 한다.
               다음조회하는 사람은 캐시된 좌석상태를 가져간다. (어짜피 유저의 무한 새로고침이 있을 것이기에 정확한 실시간성은 포기)
               2초에 한번 정도면 DB도 견딜 수 있을 것이다.
        2. Redis `Set` 을 사용한다.
            1. 캐시를 쓰더라도 변경주기가 높아서 계속 evict 가 발생해야 할 것이다.
        3. 좌석별 상태를 Redis 에 개별 `key:value` 로 저장한다.
            1. 좌석이 몇개나 되겠는가
               잠실주경기장이 약 7만석, 7일간 해도 49만석, 49만개의 키만 있으면 된다.
               개별 좌석의 예약 상태(true/false) 조회면 데이터 양도 적다.
        4. Redis - List<`SeatsInfo`: String>사용 & 10초마다 업데이트
            1. Active유저들이 “전체 좌석 예매 현황”을 지속적으로 요청할 것으로 예상되므로 캐싱을 걸지만, 10초정도 버퍼를 두어 DB의 부하를 줄인다.
            2. 10초 동안은 DB 커넥션도 일어나지 않을 것이다.

### 개별 좌석 현황 조회

- **조회 주기** : 중(하지만 요청이 몰릴 수 있음)
- **변경 주기** : 낮음
- **실시간 성** : 중상
- **결론** : 1.  Redis - `Key:Value`  & 실시간 캐시 업데이트
    - **케이스별 고민** :
        1. Redis - `Key:Value`  & 실시간 캐시 업데이트
            1. 좌석의 예약 상태를 관리하는 주체를 Redis 로 사용한다.
            2. 좌석이 예약되기 전까지만 몰리는 트레픽에 대해서 처리하면 되므로 Redis 로 빠르게 처리한다.
            3. 좌석이 몇개나 되겠는가
               잠실주경기장이 약 7만석, 7일간 해도 49만석, 49만개의 키만 있으면 된다.
               개별 좌석의 예약 상태(true/false) 조회면 데이터 양도 적다.
        2. DB+로컬캐시 & Expire 1초
            1. 좌석 예약시에 DB검증이 필수로 필요하기에 2초 정도 버퍼를 둔다.
            2. active유저들의 자리 예약을 위한 스파이크는 찰나의 순간이다.
               이때 동시요청은 레디스를 쓰더라도 크게 줄여주지는 못할것 같다.
               핸들링 가능한 수준이라면 DB와 1초 로컬 캐시로 커버하고 레디스는 다른일을 집중하는게 어떨까
            3. DB 조회시 인덱스를 사용해 단건 조회를 하므로 매우 빠를 것이다.

</details>
