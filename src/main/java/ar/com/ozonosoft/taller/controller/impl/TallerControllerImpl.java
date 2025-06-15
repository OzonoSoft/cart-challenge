package ar.com.ozonosoft.taller.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.TallerController;
import ar.com.ozonosoft.taller.dto.request.TallerRequest;
import ar.com.ozonosoft.taller.dto.response.TallerDTO;
import ar.com.ozonosoft.taller.helpers.Message;
import ar.com.ozonosoft.taller.service.TallerService;


/**
 * @author OzonoSoft
 */

@RestController
@RequestMapping("/api/talleres")
public class TallerControllerImpl implements TallerController {

    @Autowired
    private TallerService tallerService;

    @Override
    @GetMapping
    public ResponseEntity<List<TallerDTO>> findAllRegs() {
        return new ResponseEntity<>(this.tallerService.findAllRegs(), HttpStatus.OK);
    }

    @Override
    @GetMapping("{code}")
    public ResponseEntity<TallerDTO> findByCode(String code) {
        return new ResponseEntity<>(this.tallerService.findByCode(code), HttpStatus.OK);
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<TallerDTO> findById(String id) {
        return new ResponseEntity<>(this.tallerService.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Message> create(TallerRequest tallerRequest){
        return new ResponseEntity<>(this.tallerService.create(tallerRequest), HttpStatus.OK);
    }

}
