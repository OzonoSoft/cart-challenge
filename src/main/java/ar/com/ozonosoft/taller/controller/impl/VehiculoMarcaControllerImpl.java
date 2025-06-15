package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.VehiculoMarcaController;
import ar.com.ozonosoft.taller.dto.request.VehiculoMarcaRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoMarcaDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.VehiculoMarcaService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/vehiculos/marcas")
public class VehiculoMarcaControllerImpl implements VehiculoMarcaController {

    @Autowired
    private VehiculoMarcaService vehiculoMarcaService;

    @Override
    @GetMapping
    public ResponseEntity<List<VehiculoMarcaDTO>> findAllRegs() {
        return new ResponseEntity<>(this.vehiculoMarcaService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{descripcion}")
    public ResponseEntity<VehiculoMarcaDTO> findByDescripcion(String descreipcion) {
        return new ResponseEntity<>(this.vehiculoMarcaService.findByDescripcion(descreipcion), HttpStatus.OK);
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<VehiculoMarcaDTO> findById(Integer id) {
        return new ResponseEntity<>(this.vehiculoMarcaService.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(VehiculoMarcaRequest vehiculoMarcaRequest){
        return new ResponseEntity<>(this.vehiculoMarcaService.create(vehiculoMarcaRequest), HttpStatus.OK);
    }

}
