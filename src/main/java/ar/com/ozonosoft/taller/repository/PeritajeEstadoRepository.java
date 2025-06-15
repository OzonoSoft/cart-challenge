package ar.com.ozonosoft.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.PeritajeEstado;

/**
     * @author OzonoSoft
 */
@Repository
public interface  PeritajeEstadoRepository extends JpaRepository<PeritajeEstado, Integer> {

    @Query("SELECT pe FROM PeritajeEstado pe WHERE pe.id = ?1")
    PeritajeEstado findByEstadoId(Integer id);

}
