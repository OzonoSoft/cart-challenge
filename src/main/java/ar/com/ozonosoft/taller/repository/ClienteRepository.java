package ar.com.ozonosoft.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ozonosoft.taller.domain.model.Cliente;

import java.util.List;

/**
 * @author OzonoSoft
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT cli FROM Cliente cli WHERE cli.id = ?1")
    Cliente findByClienteId(String id);

    @Query("SELECT cli FROM Cliente cli WHERE cli.tallerId = ?1")
    List<Cliente> findByTallereId(String id);

    @Query("SELECT cli FROM Cliente cli WHERE cli.nroDoc = ?1")
    Cliente findByNroDoc(Long nroDoc);

}
