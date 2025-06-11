package ar.com.gabriel.cart.domain.model;

import java.util.UUID;

import ar.com.gabriel.cart.domain.model.emun.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gabriel Gonzalez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private UserStatus status;

}
