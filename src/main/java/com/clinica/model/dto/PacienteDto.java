package com.clinica.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto implements Dto{
    private Integer id;
    @NotBlank(message = "Dni o cédula es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Sólo se permiten valores alfanuméricos")
    private String dni;
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "Apellido es obligatorio")
    private String apellido;
}
