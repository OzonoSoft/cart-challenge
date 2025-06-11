package ar.com.gabriel.cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.CartItem;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import jakarta.persistence.LockModeType;

/**
 * @author Gabriel Gonzalez
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByUser_Id(UUID userId);

    Optional<Cart> findByIdAndUser_Id(UUID id, UUID userId);

    List<Cart> findByUser_IdAndStatus(UUID userId, CartStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Optional<Cart> findByIdForUpdate(@Param("id") String id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Optional<Cart> findByIdForCheckout(@Param("id") UUID id);

    @Query("SELECT c.items FROM Cart c WHERE c.id = :cartId")
    List<CartItem> getCartItems(@Param("cartId") UUID cartId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> getCartByUserId(@Param("userId") UUID userId);
}
