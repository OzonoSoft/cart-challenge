package ar.com.ozonosoft.taller.controller.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.TurnoController;
import ar.com.ozonosoft.taller.dto.request.CreateTurnoRequest;
import ar.com.ozonosoft.taller.dto.response.TurnoResponseDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.TurnoService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/turnos")
public class TurnoControllerImpl implements TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Override
    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> findAllRegs() {
        return new ResponseEntity<>(this.turnoService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<TurnoResponseDTO> findById(String id) {
        return new ResponseEntity<>(this.turnoService.findById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("estado/{id}/{tallerId}")
    public List<TurnoResponseDTO> findByEstadoAndTallerId(Integer id, String tallerId) {
        return this.turnoService.findByEstadoAndTallerId(id, tallerId);
    }

    @Override
    @GetMapping("taller/{tallerId}")
    public ResponseEntity<List<TurnoResponseDTO>> findByTallerId(String tallerId) {
        return new ResponseEntity<>(this.turnoService.findByTallerId(tallerId), HttpStatus.OK);
    }

    @Override
    @GetMapping("cliente/{id}")
    public List<TurnoResponseDTO> findByClienteId(String id) {
        return this.turnoService.findByClienteId(id);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(CreateTurnoRequest createTurnoRequest){
        return new ResponseEntity<>(this.turnoService.create(createTurnoRequest), HttpStatus.OK);
    }

}
