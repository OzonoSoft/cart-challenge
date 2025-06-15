package ar.com.ozonosoft.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.Empresa;

/**
     * @author OzonoSoft
 */
@Repository
public interface  EmpresaRepository extends JpaRepository<Empresa, String> {

    @Query("SELECT emp FROM Empresa emp WHERE emp.id = ?1")
    Empresa findByEmpresaId(String id);

    @Query("SELECT emp FROM Empresa emp WHERE emp.codigo = ?1")
    Empresa findByCodigo(String codigo);

}
