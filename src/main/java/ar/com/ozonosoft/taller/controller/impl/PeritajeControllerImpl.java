package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.PeritajeController;
import ar.com.ozonosoft.taller.dto.request.PeritajeRequest;
import ar.com.ozonosoft.taller.dto.response.PeritajeEmpresaResponseDTO;
import ar.com.ozonosoft.taller.dto.response.PeritajeResponseDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.PeritajeService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/peritajes")
public class PeritajeControllerImpl implements PeritajeController {

    @Autowired
    private PeritajeService peritajeService;

    @Override
    @GetMapping
    public ResponseEntity<List<PeritajeResponseDTO>> findAllRegs() {
        return new ResponseEntity<>(this.peritajeService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<PeritajeResponseDTO> findById(String id) {
        return new ResponseEntity<>(this.peritajeService.findById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("estado/{id}")
    public List<PeritajeResponseDTO> findByEstadoId(Integer id) {
        return this.peritajeService.findByEstadoId(id);
    }

    @Override
    @GetMapping("taller/{tallerId}")
    public ResponseEntity<List<PeritajeResponseDTO>> findByTallerId(String tallerId) {
        return new ResponseEntity<>(this.peritajeService.findByTallerId(tallerId), HttpStatus.OK);
    }

    @Override
    @GetMapping("empresa/{id}")
    public List<PeritajeEmpresaResponseDTO> findByEmpresaId(String id) {
        return this.peritajeService.findByEmpresaId(id);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(PeritajeRequest peritajeRequest){
        return new ResponseEntity<>(this.peritajeService.create(peritajeRequest), HttpStatus.OK);
    }

}
