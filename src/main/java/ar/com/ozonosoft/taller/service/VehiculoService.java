package ar.com.ozonosoft.taller.service;

import java.util.List;

import ar.com.ozonosoft.taller.dto.request.VehiculoRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface VehiculoService {
	
    List<VehiculoDTO> findAllRegs();

    VehiculoDTO findById(String id);

    List<VehiculoDTO> findByTallerId(String id);

    VehiculoDTO findByPatente(String patente);

    Message create(VehiculoRequest request);

}
