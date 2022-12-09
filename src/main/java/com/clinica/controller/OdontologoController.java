package com.clinica.controller;

import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.model.dto.OdontologoDto;
import com.clinica.service.OdontologoService;
import com.clinica.config.exception.ServiceException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j
@RestController
@RequestMapping("odontologo")
public class OdontologoController {

    @Autowired
    private OdontologoService service;

    @PostMapping("/nuevo")
    public ResponseEntity<Object> addNew(@Valid @RequestBody OdontologoDto odontologo)  {
        log.info("Entrando al controlador OdontologoController.addNew " + odontologo.toString() );
        ResponseEntity<Object> respuesta ;
        odontologo = service.createNew(odontologo);
        respuesta = ResponseEntity.ok(odontologo);
        log.info("Saliendo del controlador OdontologoController.addNew");
        return respuesta;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<OdontologoDto>> listarTodos(){
        log.info("Entrando al controlador OdontologoController.listarTodos " );
        List<OdontologoDto> respuesta = service.getAll();
        log.info("Saliendo del controlador OdontologoController.listarTodos");
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDto> buscarPorId(@PathVariable Integer id) throws ClinicaNotFoundException {
        log.info("Entrando al controlador OdontologoController.buscarPorId "+ id );
        OdontologoDto respuesta = service.getById(id);
        log.info("Saliendo del controlador OdontologoController.buscarPorId");
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<List<OdontologoDto>> buscarPorMatricula(@PathVariable String matricula){
        log.info("Entrando al controlador OdontologoController.buscarPorMatricula "+ matricula );
        List<OdontologoDto> respuesta = service.getByMatricula(matricula);
        log.info("Saliendo del controlador OdontologoController.buscarPorMatricula");
        return ResponseEntity.ok(respuesta);
    }
}
