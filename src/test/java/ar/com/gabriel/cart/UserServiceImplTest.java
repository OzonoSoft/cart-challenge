package ar.com.gabriel.cart;

import ar.com.gabriel.cart.domain.model.AppUser;
import ar.com.gabriel.cart.dto.request.UserRequest;
import ar.com.gabriel.cart.dto.response.LoginResponseDTO;
import ar.com.gabriel.cart.dto.response.UserDTO;
import ar.com.gabriel.cart.exception.ResourceValidationException;
import ar.com.gabriel.cart.repository.AppUserRepository;
import ar.com.gabriel.cart.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.com.gabriel.cart.service.impl.UserServiceImpl;

public class UserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private AppUser mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new AppUser();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.loadUserByUsername("unknown"));
    }

    @Test
    public void testLoginSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken("testuser")).thenReturn("token");

        LoginResponseDTO response = userService.login("password123", "testuser");

        assertEquals("token", response.getToken());
        assertEquals("testuser", response.getUser().getUsername());
    }

    @Test
    public void testLoginInvalidPassword() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        assertThrows(ResourceValidationException.class, () -> userService.login("wrongpass", "testuser"));
    }

    @Test
    public void testFindByUsernameSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        UserDTO dto = userService.findByUsername("testuser");

        assertEquals("testuser", dto.getUsername());
    }

    @Test
    public void testCreateUserSuccess() {
        UserRequest req = new UserRequest();
        req.setUserName("newuser");
        req.setPassword("newpass");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(AppUser.class))).thenReturn(mockUser);

        UserDTO dto = userService.create(req);

        assertEquals("testuser", dto.getUsername());
    }

    @Test
    public void testCreateUserAlreadyExists() {
        UserRequest req = new UserRequest();
        req.setUserName("testuser");
        req.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        assertThrows(ResourceValidationException.class, () -> userService.create(req));
    }

    @Test
    public void testFindUserByIdSuccess() {
        UUID id = UUID.randomUUID();
        mockUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        UserDTO dto = userService.findUserById(id.toString());

        assertEquals("testuser", dto.getUsername());
    }

    @Test
    public void testFindUserByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceValidationException.class, () -> userService.findUserById(id.toString()));
    }
}
