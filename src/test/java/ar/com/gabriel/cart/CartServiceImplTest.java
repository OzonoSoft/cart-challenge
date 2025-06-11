package ar.com.gabriel.cart;

import ar.com.gabriel.cart.domain.model.AppUser;
import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import ar.com.gabriel.cart.dto.request.CreateCartRequestDTO;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.helpers.CartMapper;
import ar.com.gabriel.cart.repository.AppUserRepository;
import ar.com.gabriel.cart.repository.CartRepository;
import ar.com.gabriel.cart.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.com.gabriel.cart.service.impl.CartServiceImpl;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private CartServiceImpl cartService;

    private final String username = "testuser";
    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCartSuccess() {
        CreateCartRequestDTO req = new CreateCartRequestDTO();

        AppUser user = new AppUser();
        user.setId(userId);
        user.setUsername(username);

        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setUser(user);
        cart.setStatus(CartStatus.CART_STATUS_PENDING);

        CartDTO cartDTO = new CartDTO();

        when(jwtUtil.getUserName()).thenReturn(username);
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser_IdAndStatus(userId, CartStatus.CART_STATUS_PENDING)).thenReturn(Collections.emptyList());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toCartDTO(any(Cart.class))).thenReturn(cartDTO);

        CartDTO result = cartService.createCart(req);
        assertNotNull(result);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    public void testCreateCartReturnsExisting() {
        CreateCartRequestDTO req = new CreateCartRequestDTO();

        AppUser user = new AppUser();
        user.setId(userId);
        user.setUsername(username);

        Cart existingCart = new Cart();
        existingCart.setId(UUID.randomUUID());
        existingCart.setUser(user);
        existingCart.setStatus(CartStatus.CART_STATUS_PENDING);

        CartDTO cartDTO = new CartDTO();

        when(jwtUtil.getUserName()).thenReturn(username);
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser_IdAndStatus(userId, CartStatus.CART_STATUS_PENDING)).thenReturn(Collections.singletonList(existingCart));
        when(cartMapper.toCartDTO(existingCart)).thenReturn(cartDTO);

        CartDTO result = cartService.createCart(req);
        assertNotNull(result);
        verify(cartRepository, never()).save(any());
    }

    @Test
    public void testCreateCartFallbackOnIntegrityViolation() {
        CreateCartRequestDTO req = new CreateCartRequestDTO();

        AppUser user = new AppUser();
        user.setId(userId);
        user.setUsername(username);

        Cart fallbackCart = new Cart();
        fallbackCart.setId(UUID.randomUUID());
        fallbackCart.setUser(user);
        fallbackCart.setStatus(CartStatus.CART_STATUS_PENDING);

        CartDTO cartDTO = new CartDTO();

        when(jwtUtil.getUserName()).thenReturn(username);
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser_IdAndStatus(userId, CartStatus.CART_STATUS_PENDING))
                .thenReturn(Collections.emptyList()) // first call inside try
                .thenReturn(Collections.singletonList(fallbackCart)); // fallback inside catch
        when(cartRepository.save(any(Cart.class))).thenThrow(new org.springframework.dao.DataIntegrityViolationException("conflict"));
        when(cartMapper.toCartDTO(fallbackCart)).thenReturn(cartDTO);

        CartDTO result = cartService.createCart(req);
        assertNotNull(result);
        verify(cartRepository, times(1)).save(any());
    }
}
