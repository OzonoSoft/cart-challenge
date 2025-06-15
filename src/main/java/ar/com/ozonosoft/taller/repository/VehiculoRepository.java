package ar.com.ozonosoft.taller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.Vehiculo;

/**
     * @author OzonoSoft
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    @Query("SELECT v FROM Vehiculo v WHERE v.id = ?1")
    Vehiculo findByVehiculoId(String id);

    @Query("SELECT v FROM Vehiculo v WHERE v.patente = ?1")
    Vehiculo findByPatente(String patente);

    @Query("SELECT v FROM Vehiculo v WHERE v.tallerId = ?1")
    List<Vehiculo> findByTallerId(String id);

    @Query("SELECT v FROM Vehiculo v WHERE v.marca.id = ?1")
    List<Vehiculo> findByMarcaId(Integer marcaId);

}
