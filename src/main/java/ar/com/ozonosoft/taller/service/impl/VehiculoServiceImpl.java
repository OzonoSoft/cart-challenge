package ar.com.ozonosoft.taller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.VehiculoConstants;
import ar.com.ozonosoft.taller.domain.model.Cliente;
import ar.com.ozonosoft.taller.domain.model.Taller;
import ar.com.ozonosoft.taller.domain.model.Vehiculo;
import ar.com.ozonosoft.taller.domain.model.VehiculoMarca;
import ar.com.ozonosoft.taller.dto.request.VehiculoRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.repository.ClienteRepository;
import ar.com.ozonosoft.taller.repository.TallerRepository;
import ar.com.ozonosoft.taller.repository.VehiculoMarcaRepository;
import ar.com.ozonosoft.taller.repository.VehiculoRepository;
import ar.com.ozonosoft.taller.service.VehiculoService;


/**
 * @author OzonoSoft
 */

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private VehiculoMarcaRepository vehiculoMarcaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TallerRepository tallerRepository;

	@Override
	public List<VehiculoDTO> findAllRegs() {
		return Mapper.mapAll((this.vehiculoRepository.findAll()), VehiculoDTO.class);
	}

	@Override
	public VehiculoDTO findById(String id){
		return Mapper.map((this.vehiculoRepository.findById(id)), VehiculoDTO.class);
	}

	@Override
	public List<VehiculoDTO> findByTallerId(String id) {
		return Mapper.mapAll((this.vehiculoRepository.findByTallerId(id)), VehiculoDTO.class);
	}

	@Override
	public VehiculoDTO findByPatente(String patente){
		return Mapper.map((this.vehiculoRepository.findByPatente(patente)), VehiculoDTO.class);
	}

	@Override
	public Message create(VehiculoRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(VehiculoConstants.ERROR_EMPTY_FIELDS);
		}

		Vehiculo vehiculo = this.vehiculoRepository.findByPatente(req.getPatente());
		if (vehiculo != null){
			throw new ResourceValidationException(VehiculoConstants.ERROR_VEHICLE_ALREADY_REGISTRED);
		}

		VehiculoMarca marca = this.vehiculoMarcaRepository.findByMarcaId(req.getMarcaId());
		if (marca == null){
			throw new ResourceValidationException(VehiculoConstants.ERROR_MARCA_NOT_FOUND);
		}

		Cliente client = this.clienteRepository.findByClienteId(req.getClienteId());
		if (client == null){
			throw new ResourceValidationException(VehiculoConstants.ERROR_CLIENT_NOT_FOUND);
		}

		Taller taller = this.tallerRepository.findByTallerId(req.getTallerId());
		if (taller == null){
			throw new ResourceValidationException(VehiculoConstants.ERROR_TALLER_NOT_FOUND);
		}

		try {
			saveVehicle(req, marca, client, taller);
		} catch (Exception e) {throw new InternalServerErrorException(VehiculoConstants.ERROR_INTERNAL);}

		return new Message(VehiculoConstants.SUCCESS);
	}

	private void saveVehicle(VehiculoRequest req, VehiculoMarca marca, Cliente client, Taller taller){
		Vehiculo vehiculo = new Vehiculo();

		vehiculo.setPatente(req.getPatente());
		vehiculo.setModelo(req.getModelo());
		vehiculo.setAnio(req.getAnio());
		vehiculo.setVersion(req.getVersion());
		vehiculo.setMarca(marca);
		vehiculo.setTallerId(taller.getId());
		vehiculo.setTitular(client.getId());

		this.vehiculoRepository.save(vehiculo);
	}

}
