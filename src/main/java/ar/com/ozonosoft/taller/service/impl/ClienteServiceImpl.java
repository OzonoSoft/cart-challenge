package ar.com.ozonosoft.taller.service.impl;

import ar.com.ozonosoft.taller.constants.ClienteConstants;
import ar.com.ozonosoft.taller.dto.request.ClienteRequest;
import ar.com.ozonosoft.taller.dto.response.ClienteDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceNotFoundException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.domain.model.*;
import ar.com.ozonosoft.taller.repository.ClienteRepository;
import ar.com.ozonosoft.taller.repository.TallerRepository;
import ar.com.ozonosoft.taller.repository.TipoClienteRepository;
import ar.com.ozonosoft.taller.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author OzonoSoft
 */

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TipoClienteRepository tipoClienteRepository;

	@Autowired
	private TallerRepository tallerRepository;


	@Override
	public List<ClienteDTO> findAllRegs() {
		return Mapper.mapAll((this.clienteRepository.findAll()), ClienteDTO.class);
	}

	@Override
	public ClienteDTO findById(String id){
		Cliente cliente = this.clienteRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El cliente: " + id + " no existe"));
        return Mapper.map(cliente, ClienteDTO.class);
	}

	@Override
	public List<ClienteDTO> findByTallerId(String id) {
		return Mapper.mapAll((this.clienteRepository.findByTallereId(id)), ClienteDTO.class);
	}

	@Override
	public ClienteDTO findByNroDoc(Long nroDoc){
		return Mapper.map((this.clienteRepository.findByNroDoc(nroDoc)), ClienteDTO.class);
	}

	@Override
	public Message create(ClienteRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(ClienteConstants.ERROR_EMPTY_FIELDS);
		}

		Taller taller = this.tallerRepository.findByTallerId(req.getTallerId());
		if (taller == null){
			throw new ResourceValidationException(ClienteConstants.ERROR_TALLER_NOT_FOUND);
		}

		Cliente cliente = this.clienteRepository.findByNroDoc(req.getNroDoc());
		if (cliente != null){
			throw new ResourceValidationException(ClienteConstants.ERROR_CLIENT_ALREADY_REGISTRED);
		}

		TipoCliente clientType = this.tipoClienteRepository.findByTipoClienteId(req.getTipo());
		if (clientType == null){
			throw new ResourceValidationException(ClienteConstants.ERROR_CLIENT_TYPE_NOT_FOUND);
		}

		try {
			saveCliente(req, clientType, taller);
		} catch (Exception e) {throw new InternalServerErrorException(ClienteConstants.ERROR_INTERNAL);}

		return new Message(ClienteConstants.SUCCESS);
	}

	private void saveCliente(ClienteRequest req, TipoCliente clientType, Taller taller){
		Cliente cliente = new Cliente();

		cliente.setApellido(req.getApellido());
		cliente.setNombre(req.getNombre());
		cliente.setNroDoc(req.getNroDoc());
		cliente.setTipo(clientType);
		cliente.setTallerId(taller.getId());
		cliente.setRazonSocial(req.getRazonSocial());
		cliente.setCreatedAt(new Date());

		this.clienteRepository.save(cliente);
	}
}
