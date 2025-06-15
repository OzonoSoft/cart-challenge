package ar.com.ozonosoft.taller.service;

import java.util.List;

import ar.com.ozonosoft.taller.dto.request.TipoClienteRequest;
import ar.com.ozonosoft.taller.dto.response.TipoClienteDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface TipoClienteService {
	
    List<TipoClienteDTO> findAllRegs();

    TipoClienteDTO findById(Integer id);

    TipoClienteDTO findByDescripcion(String descripcion);

    Message create(TipoClienteRequest request);

}
