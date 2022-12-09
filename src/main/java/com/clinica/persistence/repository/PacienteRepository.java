package com.clinica.persistence.repository;

import com.clinica.persistence.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    List<Paciente> findByNombreAndApellido(String nombre, String apellido);
}
