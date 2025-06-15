package ar.com.ozonosoft.taller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.ClienteConstants;
import ar.com.ozonosoft.taller.domain.model.TipoCliente;
import ar.com.ozonosoft.taller.dto.request.TipoClienteRequest;
import ar.com.ozonosoft.taller.dto.response.TipoClienteDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.repository.TipoClienteRepository;
import ar.com.ozonosoft.taller.service.TipoClienteService;


/**
 * @author OzonoSoft
 */

@Service
public class TipoClienteServiceImpl implements TipoClienteService {

	@Autowired
	private TipoClienteRepository tipoClienteRepository;


	@Override
	public List<TipoClienteDTO> findAllRegs() {
		return Mapper.mapAll((this.tipoClienteRepository.findAll()), TipoClienteDTO.class);
	}

	@Override
	public TipoClienteDTO findById(Integer id){
		return Mapper.map((this.tipoClienteRepository.findById(id)), TipoClienteDTO.class);
	}

	@Override
	public TipoClienteDTO findByDescripcion(String descripcion){
		return Mapper.map((this.tipoClienteRepository.findByDescripcion(descripcion)), TipoClienteDTO.class);
	}

	@Override
	public Message create(TipoClienteRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(ClienteConstants.ERROR_EMPTY_FIELDS);
		}

		TipoCliente tc = this.tipoClienteRepository.findByDescripcion(req.getDescripcion());
		if (tc != null){
			throw new ResourceValidationException(ClienteConstants.ERROR_CLIENT_TYPE_ALREADY_REGISTRED);
		}

		try {
			saveVehiculoMarca(req);
		} catch (Exception e) {throw new InternalServerErrorException(ClienteConstants.ERROR_INTERNAL);}

		return new Message(ClienteConstants.SUCCESS_CIENT_TYPE);
	}

	private void saveVehiculoMarca(TipoClienteRequest req){
		TipoCliente tc = new TipoCliente();
		tc.setDescripcion(req.getDescripcion());
		this.tipoClienteRepository.save(tc);
	}

}
