package com.clinica.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDto implements Dto{
    private Integer id;
    @NotNull(message = "Paciente es obligatorio")
    private PacienteDto paciente;
    @NotNull(message = "Odontologo es obligatorio")
    private OdontologoDto odontologo;
    @NotNull(message = "Fecha es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
}
