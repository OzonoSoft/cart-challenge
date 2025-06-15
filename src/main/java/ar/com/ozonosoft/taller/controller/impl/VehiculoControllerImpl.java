package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.VehiculoController;
import ar.com.ozonosoft.taller.dto.request.VehiculoRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.VehiculoService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoControllerImpl implements VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Override
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> findAllRegs() {
        return new ResponseEntity<>(this.vehiculoService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{codigo}")
    public ResponseEntity<VehiculoDTO> findByPatente(String patente) {
        return new ResponseEntity<>(this.vehiculoService.findByPatente(patente), HttpStatus.OK);
    }

    @Override
    @GetMapping("taller/{id}")
    public ResponseEntity<List<VehiculoDTO>> findByTallerId(String id) {
        return new ResponseEntity<>(this.vehiculoService.findByTallerId(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<VehiculoDTO> findById(String id) {
        return new ResponseEntity<>(this.vehiculoService.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(VehiculoRequest vehiculoRequest){
        return new ResponseEntity<>(this.vehiculoService.create(vehiculoRequest), HttpStatus.OK);
    }

}
