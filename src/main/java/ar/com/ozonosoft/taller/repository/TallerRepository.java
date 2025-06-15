package ar.com.ozonosoft.taller.repository;

import ar.com.ozonosoft.taller.domain.model.Taller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
     * @author OzonoSoft
 */
@Repository
public interface TallerRepository extends JpaRepository<Taller, String> {

    @Query("SELECT t FROM Taller t WHERE t.id = ?1")
    Taller findByTallerId(String id);

    @Query("SELECT t FROM Taller t WHERE t.codigo = ?1")
    Taller findByCodigo(String codigo);

}
