import http from 'k6/http';
import { check, sleep, fail } from 'k6';

const BASE_URL = 'http://localhost:8082';
const username = 'gabriel';
const password = '1234';

export let options = {
  vus: 1000,
  stages: [
    { duration: '10s', target: 1000 },
    { duration: '10s', target: 1000 },
    { duration: '10s', target: 1000 },
    { duration: '10s', target: 1000 },
  ],
};

export default function () {
  // 1. LOGIN
  const loginHeaders = { 'Content-Type': 'application/json' };
  const loginPayload = JSON.stringify({
    codigo: 'gabriel',
    password: '1234',
  });
  const loginRes = http.post(`${BASE_URL}/api/v1/users/login`, loginPayload, { headers: loginHeaders });

  const loginOk = check(loginRes, {
    'login status 200': (r) => r.status === 200,
    'token exists': (r) => !!r.json('token'),
  });

  if (!loginOk) {
    fail('Login failed');
  }

  const token = loginRes.json('token');
  const userId = loginRes.json('user.id');
  const headers = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${token}`,
  };

  // 2. CREATE CART
    const createCartPayload = JSON.stringify({ userId });

    const cartRes = http.post(`${BASE_URL}/api/v1/cart/create`, createCartPayload, {
    headers,
    });

  const cartOk = check(cartRes, {
    'cart created': (r) => r.status === 200 || r.status === 201,
  });

  if (!cartOk) {
    fail('Cart creation failed');
  }

  const cartId = cartRes.json('cartId');
  if (!cartId) fail('No cartId returned');

  // 3. ADD PRODUCT
  const addRes = http.post(
    `${BASE_URL}/api/v1/cart/add`,
    JSON.stringify({ cartId: cartId, productId: 3, quantity: 2 }),
    { headers }
  );

  check(addRes, {
    'product added': (r) => r.status === 200,
  });

  // 4. CHECKOUT
  const checkoutRes = http.post(
    `${BASE_URL}/api/v1/cart/checkout`,
    JSON.stringify({
      cartId: cartId,
      paymentMethod: 'CREDIT_CARD',
      shippingAddress: 'Av. Siempre Viva 742',
    }),
    { headers }
  );

  check(checkoutRes, {
    'checkout complete': (r) => r.status === 200,
  });

  // Controlar tiempo entre fases si es necesario
  sleep(1);
}
