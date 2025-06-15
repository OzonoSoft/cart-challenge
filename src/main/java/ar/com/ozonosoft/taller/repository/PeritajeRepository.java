package ar.com.ozonosoft.taller.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.Peritaje;


/**
 * @author OzonoSoft
 *
 */

@Repository
public interface PeritajeRepository extends JpaRepository<Peritaje, String> {
	
	Page<Peritaje> findAll(Specification<Peritaje> spec, Pageable pageable);

	@Query("SELECT p FROM Peritaje p WHERE p.id = ?1")
    Peritaje findByPeritajeId(String id);

	@Query("SELECT p FROM Peritaje p WHERE p.empresa.id = ?1")
    List<Peritaje> findByEmpresaId(String emrpesaId);

	@Query("SELECT p FROM Peritaje p WHERE p.empresa.id = ?1 and p.estado.id = ?2 ")
    List<Peritaje> findByEmpresaIdAndEstadoId(String emrpesaId, Integer estadoId);

	@Query("SELECT p FROM Peritaje p WHERE p.estado.id = ?1")
    List<Peritaje> findByEstadoId(Integer estadoId);

	@Query("SELECT p FROM Peritaje p WHERE p.taller.id = ?1")
	List<Peritaje> findByTallerId(String tallerId);

	@Query("SELECT p FROM Peritaje p WHERE p.nroSiniestro = ?1")
    Optional<Peritaje> findByNroSiniestroId(Integer nroSiniestro);

	@Query("SELECT p FROM Peritaje p WHERE p.cliente.id = ?1")
    List<Peritaje> findByClienteId(String clienteId);
}