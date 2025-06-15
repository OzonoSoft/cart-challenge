package ar.com.ozonosoft.taller.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ar.com.ozonosoft.taller.domain.model.*;
import ar.com.ozonosoft.taller.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.PeritajesConstants;
import ar.com.ozonosoft.taller.dto.request.PeritajeRequest;
import ar.com.ozonosoft.taller.dto.response.PeritajeEmpresaResponseDTO;
import ar.com.ozonosoft.taller.dto.response.PeritajeResponseDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceNotFoundException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.PeritajeService;


/**
 * @author OzonoSoft
 */

@Service
public class PeritajeServiceImpl implements PeritajeService {

	@Autowired
	private PeritajeRepository peritajeRepository;

	@Autowired
	PeritajeEstadoRepository peritajeEstadoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	VehiculoRepository vehiculoRepository;

	@Autowired
	private TallerRepository tallerRepository;

	@Override
	public Page<PeritajeResponseDTO> findAll(Specification<Peritaje> spec, Pageable pageable) {
		return this.peritajeRepository.findAll(spec, pageable).map(invoice -> Mapper.map(invoice, PeritajeResponseDTO.class));
	}

	@Override
    public List<PeritajeResponseDTO> findAllRegs() {
        return Mapper.mapAll((this.peritajeRepository.findAll()), PeritajeResponseDTO.class);
    }

	@Override
	public PeritajeResponseDTO findById(String id){
		Peritaje peritaje = this.peritajeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El peritaje: " + id + " no existe"));
        return Mapper.map(peritaje, PeritajeResponseDTO.class);
	}

	@Override
    public List<PeritajeEmpresaResponseDTO> findByEmpresaId(String emrpesaId){
		return Mapper.mapAll((this.peritajeRepository.findByEmpresaId(emrpesaId)), PeritajeEmpresaResponseDTO.class);
	}

	@Override
    public List<PeritajeEmpresaResponseDTO> findByEmpresaIdAndEstadoId(String emrpesaId, Integer estadoId){
		return Mapper.mapAll((this.peritajeRepository.findByEmpresaIdAndEstadoId(emrpesaId,  estadoId)), PeritajeEmpresaResponseDTO.class);
	}

	@Override
    public List<PeritajeResponseDTO> findByEstadoId(Integer estadoId){
		return Mapper.mapAll((this.peritajeRepository.findByEstadoId(estadoId)), PeritajeResponseDTO.class);
	}

	@Override
	public List<PeritajeResponseDTO> findByTallerId(String tallerId){
		return Mapper.mapAll((this.peritajeRepository.findByTallerId(tallerId)), PeritajeResponseDTO.class);
	}

	@Override
    public Optional<Peritaje> findByNroSiniestroId(Integer nroSiniestro){
		return this.peritajeRepository.findByNroSiniestroId(nroSiniestro);
	}

	@Override
    public List<Peritaje> findByClienteId(String clienteId){
		return this.peritajeRepository.findByClienteId(clienteId);
	}

	@Override
	public Message create(PeritajeRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(PeritajesConstants.ERROR_EMPTY_FIELDS);
		}

		Cliente client = this.clienteRepository.findByClienteId(req.getClienteId());
		if (client == null){
			throw new ResourceValidationException(PeritajesConstants.ERROR_CLIENT_NOT_FOUND);
		}

		Empresa emp = this.empresaRepository.findByEmpresaId(req.getEmpresaId());
		if (emp == null){
			throw new ResourceValidationException(PeritajesConstants.ERROR_COMPANY_NOT_FOUND);
		}

		Taller taller = this.tallerRepository.findByTallerId(req.getTallerId());
		if (taller == null){
			throw new ResourceValidationException(PeritajesConstants.ERROR_TALLER_NOT_FOUND);
		}

		Vehiculo vehiculo = this.vehiculoRepository.findByVehiculoId(req.getVehiculoId());
		if (vehiculo == null){
			throw new ResourceValidationException(PeritajesConstants.ERROR_VEHICLE_NOT_FOUND);
		}

		try {
			savePeritaje(req,client, emp, vehiculo, taller);
		} catch (Exception e) {throw new InternalServerErrorException(PeritajesConstants.ERROR_INTERNAL);}

        return new Message(PeritajesConstants.SUCCESS);
	}

	private void savePeritaje(PeritajeRequest req, Cliente client, Empresa emp, Vehiculo vehiculo, Taller taller){
		Peritaje peritaje = new Peritaje();
		PeritajeEstado estado = this.peritajeEstadoRepository.findByEstadoId(PeritajesConstants.PERITAJE_STATUS_PENDING);

		peritaje.setCliente(client);
		peritaje.setEstado(estado);
		peritaje.setDescripcion(req.getDesdripcion());
		peritaje.setPresupuesto(req.getPresupuesto());
		peritaje.setEmpresa(emp);
		peritaje.setTaller(taller);
		peritaje.setNroSiniestro(req.getNroSiniestro());
		peritaje.setFecha(new Date());
		peritaje.setVehiculo(vehiculo);
		this.peritajeRepository.save(peritaje);
	}
}
