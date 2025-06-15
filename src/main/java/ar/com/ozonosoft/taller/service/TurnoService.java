package ar.com.ozonosoft.taller.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import ar.com.ozonosoft.taller.domain.model.Turno;
import ar.com.ozonosoft.taller.dto.request.CreateTurnoRequest;
import ar.com.ozonosoft.taller.dto.response.TurnoResponseDTO;
import ar.com.ozonosoft.taller.helpers.Message;

/**
 * @author OzonoSoft
 */
public interface TurnoService {
	
	Page<TurnoResponseDTO> findAll(Specification<Turno> spec, Pageable pageable);

    List<TurnoResponseDTO> findAllRegs();

    TurnoResponseDTO findById(String id);

    List<TurnoResponseDTO> findByEstadoAndTallerId(Integer estadoId, String tallerId);

    List<TurnoResponseDTO> findByTallerId(String tallerId);

    List<TurnoResponseDTO> findByClienteId(String clienteId);

	Message create(CreateTurnoRequest createTurnoRequest);
}
