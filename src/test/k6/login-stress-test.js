import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '0s', target: 0 },
        { duration: '1s', target: 1000 },
        { duration: '10s', target: 0 },

        { duration: '1s', target: 1000 }, 
        { duration: '10s', target: 0 },

        { duration: '1s', target: 1000 }, 
        { duration: '10s', target: 0 },

        { duration: '1s', target: 1000 }, 
        { duration: '10s', target: 0 },

        { duration: '1s', target: 1000 }, 
        { duration: '10s', target: 0 },
    ]
};

export default function () {
    const userIndex = Math.floor(Math.random() * 100);
    const payload = JSON.stringify({
        codigo: `user${userIndex}`,
        password: `password${userIndex}`
    });

    const headers = { 'Content-Type': 'application/json' };
    let res = http.post('http://localhost:8082/api/v1/users/login', payload, { headers });

    check(res, {
        'status is 200': (r) => r.status === 200,
        'token is returned': (r) => r.json('token') !== undefined,
    });

    sleep(0.1);
}
