package com.clinica.service;

import com.clinica.config.MapperConfig;
import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.config.exception.ServiceException;
import com.clinica.model.dto.PacienteDto;
import com.clinica.persistence.entity.Paciente;
import com.clinica.persistence.repository.PacienteRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Log4j
@Service
public class PacienteService implements ApiService<PacienteDto>{
    @Autowired
    private MapperConfig mapper;

    @Autowired
    private PacienteRepository repository;

    @Override
    public PacienteDto createNew(PacienteDto dto) {
        log.info("Entrando al servicio PacienteService.createNew");
        Paciente entidad;
        entidad = repository.save(mapper.getModelMapper().map(dto, Paciente.class));
        log.info("Saliendo del servicio PacienteService.createNew " + entidad);
        return mapper.getModelMapper().map(entidad, PacienteDto.class);
    }

    @Override
    public List<PacienteDto> getAll() {
        log.info("Entrando al servicio PacienteService.getAll");
        List<PacienteDto> resultado = new ArrayList<>();
        List<Paciente> entidades = repository.findAll();
        entidades.forEach(e -> resultado.add(mapper.getModelMapper().map(e, PacienteDto.class)));
        log.info("Saliendo del servicio PacienteService.getAll " + entidades);
        return resultado;
    }
    @Override
    public PacienteDto getById(Integer id) throws ClinicaNotFoundException {
        log.info("Entrando al servicio PacienteService.getById");
        Optional<Paciente> paciente = repository.findById(id);
        log.info("Saliendo del servicio PacienteService.getById " + paciente);
        return paciente.map(value -> mapper.getModelMapper().map(value, PacienteDto.class)).orElseThrow(()->new ClinicaNotFoundException("Paciente con id: " + id + " no existe"));
    }

    public List<PacienteDto> getByNombreYApellido(String nombre, String apellido) {
        log.info("Entrando al servicio PacienteService.getByNombreYApellido");
        List<PacienteDto> resultado = new ArrayList<>();
        List<Paciente> pacientes = repository.findByNombreAndApellido(nombre, apellido);
        pacientes.forEach(e -> resultado.add(mapper.getModelMapper().map(e, PacienteDto.class)));
        log.info("Saliendo del servicio PacienteService.getByNombreYApellido " + pacientes);
        return resultado;
    }
}
