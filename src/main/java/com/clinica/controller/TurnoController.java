package com.clinica.controller;

import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.model.dto.TurnoDto;
import com.clinica.config.exception.ServiceException;
import com.clinica.service.TurnoService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
@Log4j
@RestController
@RequestMapping("turno")
public class TurnoController {
    @Autowired
    private TurnoService service;

    @PostMapping("/nuevo")
    public ResponseEntity<Object> addNew(@Valid @RequestBody TurnoDto turno) throws ClinicaNotFoundException {
        log.info("Entrando al controlador TurnoController.addNew " + turno.toString() );
        ResponseEntity<Object> respuesta;
        turno = service.createNew(turno);
        respuesta = ResponseEntity.ok(turno);
        log.info("Saliendo del controlador TurnoController.addNew");
        return respuesta;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TurnoDto>> listarTodos(){
        log.info("Entrando al controlador TurnoController.listarTodos " );
        List<TurnoDto> resultado = service.getAll();
        log.info("Saliendo del controlador TurnoController.listarTodos");
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarPorId(@PathVariable Integer id) throws ClinicaNotFoundException {
        log.info("Entrando al controlador TurnoController.buscarPorId "+ id );
        TurnoDto respuesta = service.getById(id);
        log.info("Saliendo del controlador TurnoController.buscarPorId");
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/fechaInicio/{fechaInicio}/fechaFin/{fechaFin}")
    public ResponseEntity<List<TurnoDto>> buscarByFechaDeterminada(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin){
        log.info("Entrando al controlador TurnoController.buscarByFechaDeterminada "+ fechaInicio + fechaFin );
        List<TurnoDto> respuesta = service.getByFechaDeterminada(fechaInicio, fechaFin);
        log.info("Saliendo del controlador TurnoController.buscarByFechaDeterminada");
        return ResponseEntity.ok(respuesta);
    }
}
