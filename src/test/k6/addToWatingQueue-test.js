import http from 'k6/http';
import { check } from 'k6';

export let options = {
    vus: 1, // 가상 유저 수
    iterations: 1, // 총 요청 수
};

export default function () {
    let url = 'http://localhost:8080/token';

    // 각 유저에게 고유한 userId 할당 (__VU 사용)
    let userId = __VU; // 각 가상 유저의 ID (__VU 값은 1부터 VUs의 수만큼 증가)

    let data = JSON.stringify({
        "userId": userId
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // HTTP POST 요청
    let response = http.post(url, data, params);

    // 응답의 상태 코드와 본문을 출력
    if (response.status !== 200) {
    } else {
        let token = JSON.parse(response.body).token;
        let reservingData = JSON.stringify({
            "userId": userId,
            "concertId": 1,
            "concertDescId": 2,
            "seatNo": 16

        });
        let params = {
            headers: {
                'Content-Type': 'application/json',
                'Token': token
            },
        };
        let reserve_response = http.post("http://localhost:8080/concert/reservation", reservingData, params);
        console.log('reserve_response : ', reserve_response.body);
        check(reserve_response, { 'status is 200': (r) => r.status === 200 });
    }

    // 응답의 상태 코드가 200인지 확인
    check(response, { 'status is 200': (r) => r.status === 200 });
}