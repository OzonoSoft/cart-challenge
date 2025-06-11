package ar.com.gabriel.cart.event.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import ar.com.gabriel.cart.event.CheckoutRequestedEvent;
import ar.com.gabriel.cart.service.job.CheckoutJobService;
import lombok.RequiredArgsConstructor;

/**
 * @author Gabriel Gonzalez
 */
@Component
@RequiredArgsConstructor
public class CheckoutListener {

    private final CheckoutJobService checkoutJobService;

    /**
     * Listener para CheckoutRequestedEvent.
     * Este método se ejecuta de forma asíncrona después de que la transacción se haya confirmado.
     * Se trata de respetar el principio de Single Responsibility, solo escucha y delega.
     * @param evt el evento que contiene los detalles del checkout
     */
    @Async("checkoutExecutor")  
    @TransactionalEventListener(
            classes = CheckoutRequestedEvent.class,
            phase   = TransactionPhase.AFTER_COMMIT)           
    public void onCheckoutRequested(CheckoutRequestedEvent evt) {
        checkoutJobService.process(evt);
    }
}