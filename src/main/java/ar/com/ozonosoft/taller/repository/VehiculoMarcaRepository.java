package ar.com.ozonosoft.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.VehiculoMarca;

/**
     * @author OzonoSoft
 */
@Repository
public interface  VehiculoMarcaRepository extends JpaRepository<VehiculoMarca, Integer> {

    @Query("SELECT vm FROM VehiculoMarca vm WHERE vm.id = ?1")
    VehiculoMarca findByMarcaId(Integer id);

    @Query("SELECT vm FROM VehiculoMarca vm WHERE vm.descripcion = ?1")
    VehiculoMarca findByDescripcion(String descripcion);

}
