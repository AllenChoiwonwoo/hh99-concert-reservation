import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    scenarios: {
        constant_request_rate: {
            executor: 'constant-arrival-rate',
            rate: 1000, // 초당 200개의 요청을 보냅니다.
            timeUnit: '1s', // 1초 동안
            duration: '10s', // 10초 동안 테스트를 실행합니다.
            preAllocatedVUs: 100, // 미리 할당된 가상 유저 수
            maxVUs: 4000, // 최대 가상 유저 수
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.01'],  // 에러율이 1% 이하
        http_req_duration: ['p(95)<200'], // 95%의 요청이 200ms 이하
    },
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
    sleep(1);

    // 응답의 상태 코드와 본문을 출력
    // if (response.status !== 200) {
    //     console.log('Error: Unexpected response status ' + response.body);
    // } else {
    //     console.log('Response body: ' + response.body);
    // }
}