package ar.com.gabriel.cart.service.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;

public final class PaymentGatewaySimulator {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewaySimulator.class);
    private static final Random RANDOM = new Random();
    
    private PaymentGatewaySimulator() {
    }

    /**
     * Simula el cobro de un pago.
     *
     * @param paymentMethod Identificador del medio de pago (ej: "TC", "EF", "MP")
     * @param amount        Importe total a debitar
     * @return {@code true} si la transacción fue aprobada; {@code false} en caso contrario
     */
    @Timed(value = "api.payment.charge", description = "Tiempo de cobro de pago (hardcoded)")
    public static boolean charge(String paymentMethod, double amount) {
        try {
            Thread.sleep(100 + RANDOM.nextInt(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Payment interrupted", e);
            return false;
        }

        boolean approved;
        if (amount <= 1000) {
            approved = true;          
        } else {
            approved = RANDOM.nextDouble() < 0.8;
        }

        if (approved) {
            log.info("Payment approved | method={} | amount={}", paymentMethod, amount);
        } else {
            log.warn("Payment declined | method={} | amount={}", paymentMethod, amount);
        }

        return approved;
    }
}
