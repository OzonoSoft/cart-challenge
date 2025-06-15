package ar.com.ozonosoft.taller.repository;

import ar.com.ozonosoft.taller.domain.model.TurnoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
     * @author OzonoSoft
 */
@Repository
public interface TurnoEstadoRepository extends JpaRepository<TurnoEstado, Integer> {

    @Query("SELECT te FROM TurnoEstado te WHERE te.id = ?1")
    TurnoEstado findByEstadoId(Integer id);

}
