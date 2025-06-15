import http from 'k6/http';
import { check, fail, sleep } from 'k6';

const BASE_URL = 'http://localhost:8082';

export let options = {
  scenarios: {
    create_cart: {
      executor: 'constant-vus',
      exec:     'scenarioCreateCart',
      vus:      100,
      duration: '10s',
    },
    add_item: {
      executor: 'constant-vus',
      exec:     'scenarioAddItem',
      vus:      100,
      duration: '10s',
      startTime:'12s',
    },
    checkout_cart: {
      executor: 'constant-vus',
      exec:     'scenarioCheckout',
      vus:      100,
      duration: '10s',
      startTime:'24s',
    },
  },
  thresholds: {
    'checks':            ['rate>0.95'],
    'http_req_duration': ['p(95)<800'],
  },
};

function login(username, password) {
  const res = http.post(
    `${BASE_URL}/api/v1/users/login`,
    JSON.stringify({ codigo: username, password: password }),
    { headers: { 'Content-Type': 'application/json' } }
  );
  check(res, {
    'login 200':      r => r.status === 200,
    'token exists':   r => !!r.json('token'),
    'userId exists':  r => !!r.json('user.id'),
  }) || fail(`Login failed for ${username}`);
  return {
    token:  res.json('token'),
    userId: res.json('user.id'),
  };
}

export function scenarioCreateCart() {
  const idx      = (__VU - 1) % 100;
  const username = `user${idx}`;
  const password = `password${idx}`;
  const { token, userId } = login(username, password);

  const res = http.post(
    `${BASE_URL}/api/v1/carts/create`,
    JSON.stringify({ userId }),
    { headers: {
        'Content-Type': 'application/json',
        Authorization:  `Bearer ${token}`,
      }}
  );
  check(res, {
    'createCart 200|201': r => r.status === 200 || r.status === 201,
    'has cartId':        r => !!r.json('cartId'),
  }) || fail(`createCart failed: ${res.status} ${res.body}`);

  sleep(1);
}

export function scenarioAddItem() {
  const idx      = (__VU - 1) % 100;
  const username = `user${idx}`;
  const password = `password${idx}`;
  const { token, userId } = login(username, password);

  // Creamos un carrito nuevo para este usuario
  const createRes = http.post(
    `${BASE_URL}/api/v1/carts/create`,
    JSON.stringify({ userId }),
    { headers: {
        'Content-Type': 'application/json',
        Authorization:  `Bearer ${token}`,
      }}
  );
  const cartId = createRes.json('cartId');
  if (!cartId) fail(`No cartId in add_item for ${username}`);

  const res = http.post(
    `${BASE_URL}/api/v1/carts/add`,
    JSON.stringify({ cartId, productId: 3, quantity: 2 }),
    { headers: {
        'Content-Type': 'application/json',
        Authorization:  `Bearer ${token}`,
      }}
  );
  check(res, {
    'addItem 200': r => r.status === 200,
  }) || fail(`addItem failed: ${res.status} ${res.body}`);

  sleep(1);
}

export function scenarioCheckout() {
  const idx      = (__VU - 1) % 100;
  const username = `user${idx}`;
  const password = `password${idx}`;
  const { token, userId } = login(username, password);

  // Crear y poblar carrito
  const createRes = http.post(
    `${BASE_URL}/api/v1/carts/create`,
    JSON.stringify({ userId }),
    { headers: {
        'Content-Type': 'application/json',
        Authorization:  `Bearer ${token}`,
      }}
  );
  const cartId = createRes.json('cartId');
  if (!cartId) fail(`No cartId in checkout for ${username}`);

  http.post(
    `${BASE_URL}/api/v1/carts/add`,
    JSON.stringify({ cartId, productId: 3, quantity: 2 }),
    { headers: {
        'Content-Type': 'application/json',
        Authorization:  `Bearer ${token}`,
      }}
  );

  const res = http.post(
    `${BASE_URL}/api/v1/carts/checkout`,
    JSON.stringify({
      cartId,
      paymentMethod:   'CREDIT_CARD',
      shippingAddress: 'Av. Siempre Viva 742',
    }),
    { headers: {
        'Content-Type': 'application/json',
        Authorization:  `Bearer ${token}`,
      }}
  );
  check(res, {
    'checkout 200|201': r => r.status === 200 || r.status === 201,
  }) || fail(`checkout failed: ${res.status} ${res.body}`);

  sleep(1);
}
