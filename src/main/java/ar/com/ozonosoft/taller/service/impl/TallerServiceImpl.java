package ar.com.ozonosoft.taller.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.TallerConstants;
import ar.com.ozonosoft.taller.domain.model.Taller;
import ar.com.ozonosoft.taller.dto.request.TallerRequest;
import ar.com.ozonosoft.taller.dto.response.TallerDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceNotFoundException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.repository.TallerRepository;
import ar.com.ozonosoft.taller.service.TallerService;


/**
 * @author OzonoSoft
 */

@Service
public class TallerServiceImpl implements TallerService {

	@Autowired
	private TallerRepository tallerRepository;

	@Override
	public List<TallerDTO> findAllRegs() {
		return Mapper.mapAll((this.tallerRepository.findAll()), TallerDTO.class);
	}

	@Override
	public TallerDTO findById(String id){
		Taller taller = this.tallerRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El taller: " + id + " no existe"));
        return Mapper.map(taller, TallerDTO.class);
	}

	@Override
	public TallerDTO findByCode(String code){
		return Mapper.map((this.tallerRepository.findByCodigo(code)), TallerDTO.class);
	}

	@Override
	public Message create(TallerRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(TallerConstants.ERROR_EMPTY_FIELDS);
		}

		Taller taller = this.tallerRepository.findByCodigo(req.getCodigo());
		if (taller != null){
			throw new ResourceValidationException(TallerConstants.ERROR_TALLER_ALREADY_REGISTRED);
		}

		try {
			saveTaller(req);
		} catch (Exception e) {throw new InternalServerErrorException(TallerConstants.ERROR_INTERNAL);}

		return new Message(TallerConstants.SUCCESS);
	}

	private void saveTaller(TallerRequest req){
		Taller taller = new Taller();

		taller.setCodigo(req.getCodigo());
		taller.setCuit(req.getCuit());
		taller.setRazonSocial(req.getRazonSocial());
		taller.setCalle(req.getCalle());
		taller.setTelCelular(req.getTelCelular());
		taller.setTelFijo(req.getTelFijo());
		taller.setNumeroCalle(req.getNumeroCalle());
		taller.setUrlWeb(req.getUrlWeb());
		taller.setCodigoPostal(TallerConstants.RIO_CUARTO_CP);
		taller.setStatus(TallerConstants.TALLER_STATUS_ACTIVE);
		taller.setCreatedAt(new Date());

		this.tallerRepository.save(taller);
	}
}
