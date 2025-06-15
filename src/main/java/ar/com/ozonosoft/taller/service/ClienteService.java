package ar.com.ozonosoft.taller.service;

import java.util.List;

import ar.com.ozonosoft.taller.dto.request.ClienteRequest;
import ar.com.ozonosoft.taller.dto.response.ClienteDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface ClienteService {
	
    List<ClienteDTO> findAllRegs();

    ClienteDTO findById(String id);

    List<ClienteDTO> findByTallerId(String id);

    ClienteDTO findByNroDoc(Long nroDoc);

    Message create(ClienteRequest request);

}
