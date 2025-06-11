package ar.com.gabriel.cart.event;

/**
 * Evento que representa un checkout.
 * Este evento se activa cuando un usuario inicia el proceso de checkout.
 * Se utiliza para notificar a otros componentes sobre la request de heckout.
 *
 * @param cartId El ID del carrito.
 * @param paymentMethod El método de pago seleccionado para el checkout.
 * @param shippingAddress La dirección donde se enviarán los artículos.
 */
public record CheckoutRequestedEvent(String cartId,
                                     String paymentMethod,
                                     String shippingAddress) {}