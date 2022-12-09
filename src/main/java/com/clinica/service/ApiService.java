package com.clinica.service;

import com.clinica.config.exception.ClinicaNotFoundException;
import com.clinica.config.exception.ServiceException;
import com.clinica.model.dto.Dto;

import java.util.List;

public interface ApiService<D extends Dto> {
    D createNew(D dto) throws ServiceException, ClinicaNotFoundException;

    List<D> getAll();

    D getById(Integer id) throws ClinicaNotFoundException;
}
