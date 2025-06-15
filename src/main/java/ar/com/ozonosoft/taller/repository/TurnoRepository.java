package ar.com.ozonosoft.taller.repository;

import ar.com.ozonosoft.taller.domain.model.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author OzonoSoft
 *
 */

@Repository
public interface TurnoRepository extends JpaRepository<Turno, String> {
	
	Page<Turno> findAll(Specification<Turno> spec, Pageable pageable);

	@Query("SELECT t FROM Turno t WHERE t.id = ?1")
    Turno findByTurnoId(String id);

	@Query("SELECT t FROM Turno t WHERE t.estado.id = ?1 and t.tallerId = ?2")
    List<Turno> findByEstadoAndTallerId(Integer estadoId, String tallerId);

	@Query("SELECT t FROM Turno t WHERE t.tallerId = ?1")
	List<Turno> findByTallerId(String tallerId);

	@Query("SELECT t FROM Turno t WHERE t.cliente.id = ?1")
    List<Turno> findByClienteId(String clienteId);

	
}