package ar.com.ozonosoft.taller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import ar.com.ozonosoft.taller.domain.model.Peritaje;
import ar.com.ozonosoft.taller.dto.request.PeritajeRequest;
import ar.com.ozonosoft.taller.dto.response.PeritajeEmpresaResponseDTO;
import ar.com.ozonosoft.taller.dto.response.PeritajeResponseDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface PeritajeService {
	
	Page<PeritajeResponseDTO> findAll(Specification<Peritaje> spec, Pageable pageable);

    List<PeritajeResponseDTO> findAllRegs();

    PeritajeResponseDTO findById(String id);

    List<PeritajeEmpresaResponseDTO> findByEmpresaId(String emrpesaId);

    List<PeritajeEmpresaResponseDTO> findByEmpresaIdAndEstadoId(String emrpesaId, Integer estadoId);

    List<PeritajeResponseDTO> findByEstadoId(Integer estadoId);

    List<PeritajeResponseDTO> findByTallerId(String tallerId);

    Optional<Peritaje> findByNroSiniestroId(Integer nroSiniestro);

    List<Peritaje> findByClienteId(String clienteId);

	Message create(PeritajeRequest peritajeRequest);
}
