package ar.com.ozonosoft.taller.repository;

import ar.com.ozonosoft.taller.domain.model.TallerEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
     * @author OzonoSoft
 */
@Repository
public interface TallerEmpresaRepository extends JpaRepository<TallerEmpresa, String> {


}
