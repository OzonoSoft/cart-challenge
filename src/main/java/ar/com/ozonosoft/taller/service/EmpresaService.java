package ar.com.ozonosoft.taller.service;

import java.util.List;

import ar.com.ozonosoft.taller.dto.request.EmpresaRequest;
import ar.com.ozonosoft.taller.dto.response.EmpresaDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface EmpresaService {

    List<EmpresaDTO> findAllRegs();

    EmpresaDTO findById(String id);

    EmpresaDTO findByCode(String code);

    Message create(EmpresaRequest request);

}
