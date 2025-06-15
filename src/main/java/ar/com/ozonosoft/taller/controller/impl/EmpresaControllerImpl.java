package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.EmpresaController;
import ar.com.ozonosoft.taller.dto.request.EmpresaRequest;
import ar.com.ozonosoft.taller.dto.response.EmpresaDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.EmpresaService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/empresas")
public class EmpresaControllerImpl implements EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Override
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> findAllRegs() {
        return new ResponseEntity<>(this.empresaService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<EmpresaDTO> findById(String id) {
        return new ResponseEntity<>(this.empresaService.findById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/code/{code}")
    public ResponseEntity<EmpresaDTO> findByCodigo(String code) {
        return new ResponseEntity<>(this.empresaService.findByCode(code), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(EmpresaRequest empresaRequest){
        return new ResponseEntity<>(this.empresaService.create(empresaRequest), HttpStatus.OK);
    }

}
