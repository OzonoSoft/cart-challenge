package ar.com.ozonosoft.taller.service;

import java.util.List;

import ar.com.ozonosoft.taller.dto.request.VehiculoMarcaRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoMarcaDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface VehiculoMarcaService {
	
    List<VehiculoMarcaDTO> findAllRegs();

    VehiculoMarcaDTO findById(Integer id);

    VehiculoMarcaDTO findByDescripcion(String descripcion);

    Message create(VehiculoMarcaRequest request);

}
