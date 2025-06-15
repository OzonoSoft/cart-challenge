package ar.com.ozonosoft.taller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.VehiculoMarcaConstants;
import ar.com.ozonosoft.taller.domain.model.VehiculoMarca;
import ar.com.ozonosoft.taller.dto.request.VehiculoMarcaRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoMarcaDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.repository.VehiculoMarcaRepository;
import ar.com.ozonosoft.taller.service.VehiculoMarcaService;


/**
 * @author OzonoSoft
 */

@Service
public class VehiculoMarcaServiceImpl implements VehiculoMarcaService {

	@Autowired
	private VehiculoMarcaRepository vehiculoMarcaRepository;


	@Override
	public List<VehiculoMarcaDTO> findAllRegs() {
		return Mapper.mapAll((this.vehiculoMarcaRepository.findAll()), VehiculoMarcaDTO.class);
	}

	@Override
	public VehiculoMarcaDTO findById(Integer id){
		return Mapper.map((this.vehiculoMarcaRepository.findById(id)), VehiculoMarcaDTO.class);
	}

	@Override
	public VehiculoMarcaDTO findByDescripcion(String descripcion){
		return Mapper.map((this.vehiculoMarcaRepository.findByDescripcion(descripcion)), VehiculoMarcaDTO.class);
	}

	@Override
	public Message create(VehiculoMarcaRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(VehiculoMarcaConstants.ERROR_EMPTY_FIELDS);
		}

		VehiculoMarca marca = this.vehiculoMarcaRepository.findByDescripcion(req.getDescripcion());
		if (marca != null){
			throw new ResourceValidationException(VehiculoMarcaConstants.ERROR_VEHICLE_MARCA_ALREADY_REGISTRED);
		}

		try {
			saveVehiculoMarca(req);
		} catch (Exception e) {throw new InternalServerErrorException(VehiculoMarcaConstants.ERROR_INTERNAL);}

		return new Message(VehiculoMarcaConstants.SUCCESS);
	}

	private void saveVehiculoMarca(VehiculoMarcaRequest req){
		VehiculoMarca marca = new VehiculoMarca();
		marca.setDescripcion(req.getDescripcion());
		this.vehiculoMarcaRepository.save(marca);
	}

}
