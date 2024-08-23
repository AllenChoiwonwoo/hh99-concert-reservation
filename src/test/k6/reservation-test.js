import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        constant_request_rate: {
            executor: 'constant-arrival-rate',
            rate: 1000,                // 초당 1000개의 요청을 보냅니다.
            timeUnit: '1s',            // 1초 단위로 요청 비율을 유지합니다.
            duration: '10s',           // 10초 동안 테스트를 실행합니다.
            preAllocatedVUs: 100,      // 미리 할당된 가상 유저 수
            maxVUs: 4000,              // 최대 가상 유저 수
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.01'],      // 에러율이 1% 이하이어야 합니다.
        http_req_duration: ['p(95)<200'],    // 95%의 요청이 200ms 이하이어야 합니다.
    },
};

export default function () {
    const token = getToken();           // 첫 번째 API 요청: 토큰을 받습니다.
    reserveSeat(token);                 // 두 번째 API 요청: 좌석을 예약합니다.
}

// 첫 번째 API 요청: 토큰 받기
function getToken() {
    const url = 'http://localhost:8080/token';
    const userId = __VU; // 각 가상 유저의 고유 ID

    const data = JSON.stringify({
        userId: userId
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(url, data, params);

    check(response, { 'status is 200': (r) => r.status === 200 });

    if (response.status === 200) {
        return JSON.parse(response.body).token; // 토큰 반환
    } else {
        return null;
    }
}

// 두 번째 API 요청: 좌석 예약하기
function reserveSeat(token) {
    if (!token) {
        return; // 토큰이 없으면 좌석 예약을 시도하지 않습니다.
    }

    const url = "http://localhost:8080/concert/reservation";
    const userId = __VU;

    const reservingData = JSON.stringify({
        userId: userId,
        concertId: 1,
        concertDescId: 2,
        seatNo: Math.floor(Math.random() * 20) + 1  // 1에서 20 사이의 무작위 좌석 번호
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Token': token,
        },
    };

    const reserveResponse = http.post(url, reservingData, params);

    check(reserveResponse, { 'status is 200': (r) => r.status === 200 });
}
