package com.clinica.service;


import com.clinica.config.MapperConfig;
import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.config.exception.ServiceException;
import com.clinica.model.dto.OdontologoDto;
import com.clinica.model.dto.PacienteDto;
import com.clinica.model.dto.TurnoDto;
import com.clinica.persistence.entity.Turno;
import com.clinica.persistence.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Log4j
@Service
public class TurnoService implements ApiService<TurnoDto>{

    @Autowired
    private MapperConfig mapper;

    @Autowired
    ObjectMapper oMapper;

    @Autowired
    private TurnoRepository repository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Override
    public TurnoDto createNew(TurnoDto dto) throws ClinicaNotFoundException {
        log.info("Entrando al servicio TurnoService.createNew");
        existenPacienteYOdontologo(dto);
        Turno respuesta = repository.save(oMapper.convertValue(dto, Turno.class));
        log.info("Saliendo del servicio TurnoService.createNew " + respuesta);
        return mapper.getModelMapper().map(respuesta, TurnoDto.class);
    }

    @Override
    public List<TurnoDto> getAll() {
        log.info("Entrando al servicio TurnoService.getAll");
        List<TurnoDto> resultado = new ArrayList<>();
        List<Turno> entidades = repository.findAll();
        entidades.forEach(e -> resultado.add(mapper.getModelMapper().map(e, TurnoDto.class)));
        log.info("Saliendo del servicio TurnoService.getAll " + entidades);
        return resultado;
    }

    @Override
    public TurnoDto getById(Integer id) throws ClinicaNotFoundException {
        log.info("Entrando al servicio TurnoService.getById");
        Optional<Turno> turnoId = repository.findById(id);
        log.info("Saliendo del servicio TurnoService.getById " + turnoId);
        return turnoId.map(turno -> mapper.getModelMapper().map(turno, TurnoDto.class)).orElseThrow(()->new ClinicaNotFoundException("El turno con id: " + id + " no existe"));
    }

    private void existenPacienteYOdontologo(TurnoDto dto) throws ClinicaNotFoundException {
        log.info("Entrando al servicio TurnoService.existenPacienteYOdontologo");
        pacienteService.getById(dto.getPaciente().getId());
        odontologoService.getById(dto.getOdontologo().getId());
        log.info("Saliendo del servicio TurnoService.existenPacienteYOdontologo ");
    }

    public List<TurnoDto> getByFechaDeterminada(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Entrando al servicio TurnoService.getByFechaDeterminada");
        List<TurnoDto> resultado = new ArrayList<>();
        List<Turno> turnos = repository.findByFechaBetween(fechaInicio, fechaFin);
        turnos.forEach(e -> resultado.add(mapper.getModelMapper().map(e, TurnoDto.class)));
        log.info("Saliendo del servicio TurnoService.getByFechaDeterminada " + turnos);
        return resultado;
    }
}
