package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.TipoClienteController;
import ar.com.ozonosoft.taller.dto.request.TipoClienteRequest;
import ar.com.ozonosoft.taller.dto.response.TipoClienteDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.TipoClienteService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/clientes/tipos")
public class TipoClienteControllerImpl implements TipoClienteController {

    @Autowired
    private TipoClienteService tipoClienteService;

    @Override
    @GetMapping
    public ResponseEntity<List<TipoClienteDTO>> findAllRegs() {
        return new ResponseEntity<>(this.tipoClienteService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{descripcion}")
    public ResponseEntity<TipoClienteDTO> findByDescripcion(String descreipcion) {
        return new ResponseEntity<>(this.tipoClienteService.findByDescripcion(descreipcion), HttpStatus.OK);
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<TipoClienteDTO> findById(Integer id) {
        return new ResponseEntity<>(this.tipoClienteService.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(TipoClienteRequest tcq){
        return new ResponseEntity<>(this.tipoClienteService.create(tcq), HttpStatus.OK);
    }

}
