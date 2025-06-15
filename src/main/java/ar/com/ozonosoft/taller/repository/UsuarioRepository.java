package ar.com.ozonosoft.taller.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.Usuario;

/**
 * @author OzonoSoft
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("SELECT usu FROM Usuario usu WHERE usu.id = ?1")
    Usuario findByUsuarioId(String id);

    Optional<Usuario> findByUsername(String username);


}
