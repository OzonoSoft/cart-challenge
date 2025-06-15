package ar.com.ozonosoft.taller.service;

import java.util.List;

import ar.com.ozonosoft.taller.dto.request.TallerRequest;
import ar.com.ozonosoft.taller.dto.response.TallerDTO;
import ar.com.ozonosoft.taller.helpers.Message;


/**
 * @author OzonoSoft
 */
public interface TallerService {
	
    List<TallerDTO> findAllRegs();

    TallerDTO findById(String id);

    TallerDTO findByCode(String code);

    Message create(TallerRequest request);

}
