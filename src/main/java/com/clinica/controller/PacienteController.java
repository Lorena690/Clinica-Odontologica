package com.clinica.controller;

import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.model.dto.PacienteDto;
import com.clinica.service.PacienteService;
import com.clinica.config.exception.ServiceException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Log4j
@RestController
@RequestMapping("paciente")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping("/nuevo")
    public ResponseEntity<Object> addNew(@Valid @RequestBody PacienteDto paciente) throws ServiceException {
        log.info("Entrando al controlador PacienteController.addNew " + paciente.toString() );
        ResponseEntity<Object> respuesta;
        paciente = service.createNew(paciente);
        respuesta = ResponseEntity.ok(paciente);
        log.info("Saliendo del controlador PacienteController.addNew");
        return respuesta;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<PacienteDto>> listarTodos(){
        log.info("Entrando al controlador PacienteController.listarTodos " );
        List<PacienteDto> resultado = service.getAll();
        log.info("Saliendo del controlador PacienteController.listarTodos");
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPorId(@PathVariable Integer id) throws ClinicaNotFoundException {
        log.info("Entrando al controlador PacienteController.buscarPorId "+ id );
        PacienteDto respuesta = service.getById(id);
        log.info("Saliendo del controlador PacienteController.buscarPorId");
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/nombre/{nombre}/apellido/{apellido}")
    public ResponseEntity<List<PacienteDto>> buscarPorNombreYApellido(@PathVariable String nombre, @PathVariable String apellido){
        log.info("Entrando al controlador PacienteController.buscarPorMatricula "+ nombre + apellido );
        List<PacienteDto> respuesta = service.getByNombreYApellido(nombre, apellido);
        log.info("Saliendo del controlador PacienteController.buscarPorMatricula");
        return ResponseEntity.ok(respuesta);
    }
}
