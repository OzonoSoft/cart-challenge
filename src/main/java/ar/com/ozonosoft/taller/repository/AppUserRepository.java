package ar.com.ozonosoft.taller.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.AppUser;

/**
 * @author Gabriel Gonzalez
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
}