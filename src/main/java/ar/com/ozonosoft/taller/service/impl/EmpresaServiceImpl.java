package ar.com.ozonosoft.taller.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ozonosoft.taller.constants.EmpresaConstants;
import ar.com.ozonosoft.taller.domain.model.Empresa;
import ar.com.ozonosoft.taller.dto.request.EmpresaRequest;
import ar.com.ozonosoft.taller.dto.response.EmpresaDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceNotFoundException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.repository.EmpresaRepository;
import ar.com.ozonosoft.taller.service.EmpresaService;


/**
 * @author OzonoSoft
 */

@Service
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;


	@Override
    public List<EmpresaDTO> findAllRegs() {
        return Mapper.mapAll((this.empresaRepository.findAll()), EmpresaDTO.class);
    }

	@Override
	public EmpresaDTO findById(String id){
		Empresa empresa = this.empresaRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("La empresa: " + id + " no existe"));
        return Mapper.map(empresa, EmpresaDTO.class);
	}

	@Override
	public EmpresaDTO findByCode(String code){
		return Mapper.map((this.empresaRepository.findByCodigo(code)), EmpresaDTO.class);
	}

	@Override
	public Message create(EmpresaRequest req){
		if(!req.isValidInput()){
			throw new ResourceValidationException(EmpresaConstants.ERROR_EMPTY_FIELDS);
		}

		Empresa emp = this.empresaRepository.findByCodigo(req.getCodigo());
		if (emp != null){
			throw new ResourceValidationException(EmpresaConstants.ERROR_EMPRESA_ALREADY_REGISTRED);
		}

		try {
			saveEmpresa(req);
		} catch (Exception e) {throw new InternalServerErrorException(EmpresaConstants.ERROR_INTERNAL);}

		return new Message(EmpresaConstants.SUCCESS);
	}

	private void saveEmpresa(EmpresaRequest req){
		Empresa emp = new Empresa();

		emp.setCodigo(req.getCodigo());
		emp.setCuit(req.getCuit());
		emp.setCalle(req.getCalle());
		emp.setRazonSocial(req.getRazonSocial());
		emp.setDepto(req.getDepto());
		emp.setPiso(req.getPiso());
		emp.setNumeroCalle(req.getNumeroCalle());
		emp.setCalle(req.getCalle());
		emp.setCodigoPostal(EmpresaConstants.RIO_CUARTO_CP);
		emp.setStatus(EmpresaConstants.EMPRESA_STATUS_ACTIVE);
		emp.setTelCelular(req.getTelCelular());
		emp.setTelFijo(req.getTelFijo());
		emp.setUrlWeb(req.getUrlWeb());
		emp.setCreatedAt(new Date());

		this.empresaRepository.save(emp);
	}
}
