package ar.com.ozonosoft.taller.repository;

import ar.com.ozonosoft.taller.domain.model.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author OzonoSoft
 */
@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer> {

    @Query("SELECT tcli FROM TipoCliente tcli WHERE tcli.id = ?1")
    TipoCliente findByTipoClienteId(Integer id);

    @Query("SELECT tcli FROM TipoCliente tcli WHERE tcli.descripcion = ?1")
    TipoCliente findByDescripcion(String descripcion);


}
