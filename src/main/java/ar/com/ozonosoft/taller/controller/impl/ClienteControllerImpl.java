package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.ClienteController;
import ar.com.ozonosoft.taller.dto.request.ClienteRequest;
import ar.com.ozonosoft.taller.dto.response.ClienteDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.ClienteService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/clientes")
public class ClienteControllerImpl implements ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Override
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAllRegs() {
        return new ResponseEntity<>(this.clienteService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<ClienteDTO> findById(String id) {
        return new ResponseEntity<>(this.clienteService.findById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("taller/{id}")
    public ResponseEntity<List<ClienteDTO>> findByTallerId(String id) {
        return new ResponseEntity<>(this.clienteService.findByTallerId(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/doc/{nroDoc}")
    public ResponseEntity<ClienteDTO> findByNroDoc(Long nroDoc) {
        return new ResponseEntity<>(this.clienteService.findByNroDoc(nroDoc), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(ClienteRequest clienteRequest){
        return new ResponseEntity<>(this.clienteService.create(clienteRequest), HttpStatus.OK);
    }

}
