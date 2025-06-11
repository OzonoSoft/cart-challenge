package ar.com.gabriel.cart.helpers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.CartItem;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.dto.response.CartItemDTO;
import ar.com.gabriel.cart.dto.response.ProductDTO;

/**
 * @author Gabriel Gonzalez
 */
@Component
public class CartMapper {

    public CartDTO toCartDTO(Cart cart) {
        List<CartItemDTO> items = cart.getItems()
                                      .stream()
                                      .map(this::toCartItemDTO)
                                      .collect(Collectors.toList());

        double total = items.stream()
                            .mapToDouble(i -> i.getProduct().getPrice() * i.getQty())
                            .sum();

        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getId().toString());
        dto.setItems(items);
        dto.setStatus(cart.getStatus());
        dto.setTotal(total);
        return dto;
    }

    public CartItemDTO toCartItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setCartId(item.getCart().getId().toString());
         dto.setProduct(Mapper.map(item.getProduct(), ProductDTO.class));
        dto.setQty(item.getQty());
        return dto;
    }
}