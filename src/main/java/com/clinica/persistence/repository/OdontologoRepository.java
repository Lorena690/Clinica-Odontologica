package com.clinica.persistence.repository;

import com.clinica.persistence.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {
    List<Odontologo> findByMatricula(String matricula);
}
