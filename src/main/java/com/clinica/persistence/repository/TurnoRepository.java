package com.clinica.persistence.repository;

import com.clinica.persistence.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    List<Turno> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
