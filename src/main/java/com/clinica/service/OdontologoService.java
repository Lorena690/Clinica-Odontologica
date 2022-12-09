package com.clinica.service;

import com.clinica.config.MapperConfig;
import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.config.exception.ServiceException;
import com.clinica.model.dto.OdontologoDto;
import com.clinica.persistence.entity.Odontologo;
import com.clinica.persistence.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class OdontologoService implements ApiService<OdontologoDto> {

    @Autowired
    private MapperConfig mapper;

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    private OdontologoRepository repository;

    @Override
    public OdontologoDto createNew(OdontologoDto dto) {
        log.info("Entrando al servicio OdontologoService.createNew");
        Odontologo entidad ;
        entidad = repository.save(mapper.getModelMapper().map(dto, Odontologo.class));
        log.info("Saliendo del servicio OdontologoService.createNew " + entidad);
        return mapper.getModelMapper().map(entidad, OdontologoDto.class);
    }

    @Override
    public List<OdontologoDto> getAll() {
        log.info("Entrando al servicio OdontologoService.getAll");
        List<OdontologoDto> resultado = new ArrayList<>();
        List<Odontologo> entidades = repository.findAll();
        entidades.forEach(e -> resultado.add(mapper.getModelMapper().map(e, OdontologoDto.class)));
        log.info("Saliendo del servicio OdontologoService.getAll " + entidades);
        return resultado;
    }

    @Override
    public OdontologoDto getById(Integer id) throws ClinicaNotFoundException {
        log.info("Entrando al servicio OdontologoService.getById");
        Optional<Odontologo> odontologo = repository.findById(id);
        log.info("Saliendo del servicio OdontologoService.getById " + odontologo);
        return odontologo.map(value -> mapper.getModelMapper().map(value, OdontologoDto.class)).orElseThrow(()->new ClinicaNotFoundException("Odontologo con id: " + id + " no existe"));
    }

    public List<OdontologoDto> getByMatricula(String matricula) {
        log.info("Entrando al servicio OdontologoService.getByMatricula");
        List<OdontologoDto> resultado = new ArrayList<>();
        List<Odontologo> odontologos = repository.findByMatricula(matricula);
        odontologos.forEach(e -> resultado.add(mapper.getModelMapper().map(e, OdontologoDto.class)));
        log.info("Saliendo del servicio OdontologoService.getByMatricula " + odontologos);
        return resultado;
    }
}
