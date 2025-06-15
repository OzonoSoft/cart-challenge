package ar.com.ozonosoft.taller.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.TurnosConstants;
import ar.com.ozonosoft.taller.domain.model.Cliente;
import ar.com.ozonosoft.taller.domain.model.Peritaje;
import ar.com.ozonosoft.taller.domain.model.Taller;
import ar.com.ozonosoft.taller.domain.model.Turno;
import ar.com.ozonosoft.taller.domain.model.TurnoEstado;
import ar.com.ozonosoft.taller.domain.model.Vehiculo;
import ar.com.ozonosoft.taller.dto.request.CreateTurnoRequest;
import ar.com.ozonosoft.taller.dto.response.TurnoResponseDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceNotFoundException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.repository.ClienteRepository;
import ar.com.ozonosoft.taller.repository.PeritajeRepository;
import ar.com.ozonosoft.taller.repository.TallerRepository;
import ar.com.ozonosoft.taller.repository.TurnoEstadoRepository;
import ar.com.ozonosoft.taller.repository.TurnoRepository;
import ar.com.ozonosoft.taller.repository.VehiculoRepository;
import ar.com.ozonosoft.taller.service.TurnoService;


/**
 * @author OzonoSoft
 */

@Service
public class TurnoServiceImpl implements TurnoService {

	@Autowired
	private PeritajeRepository peritajeRepository;

	@Autowired
	private TurnoRepository turnoRepository;

	@Autowired
	private TurnoEstadoRepository turnoEstadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private TallerRepository tallerRepository;

	@Override
	public Page<TurnoResponseDTO> findAll(Specification<Turno> spec, Pageable pageable) {
		return this.turnoRepository.findAll(spec, pageable).map(invoice -> Mapper.map(invoice, TurnoResponseDTO.class));
	}

	@Override
    public List<TurnoResponseDTO> findAllRegs() {
        return Mapper.mapAll((this.turnoRepository.findAll()), TurnoResponseDTO.class);
    }

	@Override
	public TurnoResponseDTO findById(String id){
		Turno turno = this.turnoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El turno: " + id + " no existe"));
        return Mapper.map(turno, TurnoResponseDTO.class);
	}


	@Override
    public List<TurnoResponseDTO> findByEstadoAndTallerId(Integer estadoId, String tallerId){
		return Mapper.mapAll((this.turnoRepository.findByEstadoAndTallerId(estadoId, tallerId)), TurnoResponseDTO.class);
	}

	@Override
	public List<TurnoResponseDTO> findByTallerId(String tallerId){
		return Mapper.mapAll((this.turnoRepository.findByTallerId(tallerId)), TurnoResponseDTO.class);
	}

	@Override
    public List<TurnoResponseDTO> findByClienteId(String clienteId){
		return Mapper.mapAll((this.turnoRepository.findByClienteId(clienteId)), TurnoResponseDTO.class);
	}

	@Override
	public Message create(CreateTurnoRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(TurnosConstants.ERROR_EMPTY_FIELDS);
		}

		Cliente client = this.clienteRepository.findByClienteId(req.getClienteId());
		if (client == null){
			throw new ResourceValidationException(TurnosConstants.ERROR_CLIENT_NOT_FOUND);
		}

		Taller taller = this.tallerRepository.findByTallerId(req.getTallerId());
		if (taller == null){
			throw new ResourceValidationException(TurnosConstants.ERROR_TALLER_NOT_FOUND);
		}

		Vehiculo vehiculo = this.vehiculoRepository.findByVehiculoId(req.getVehiculoId());
		if (vehiculo == null){
			throw new ResourceValidationException(TurnosConstants.ERROR_VEHICLE_NOT_FOUND);
		}

		Peritaje peritaje = this.peritajeRepository.findByPeritajeId(req.getPeritajeaId());

		try {
			saveTurno(req,client, peritaje.getId(), vehiculo, taller);
		} catch (Exception e) {throw new InternalServerErrorException(TurnosConstants.ERROR_INTERNAL);}

        return new Message(TurnosConstants.SUCCESS);
	}

	private void saveTurno(CreateTurnoRequest req, Cliente client, String peritajeId, Vehiculo vehiculo, Taller taller){
		Turno turno = new Turno();
		TurnoEstado estado = this.turnoEstadoRepository.findByEstadoId(TurnosConstants.TURNO_STATUS_NUEVO);

		if(taller.getId() != null)
			turno.setTallerId(taller.getId());

		turno.setCliente(client);
		turno.setEstado(estado);
		turno.setObservaciones(req.getObservaciones());
		turno.setPeritajeId(peritajeId);
		turno.setFecha(new Date());
		turno.setVehiculo(vehiculo);
		turno.setHora(req.getHora());
		turno.setCreatedAt(new Date());

		this.turnoRepository.save(turno);
	}
}
