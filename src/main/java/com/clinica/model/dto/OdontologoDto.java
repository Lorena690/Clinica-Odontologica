package com.clinica.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OdontologoDto implements Dto {
    private Integer id;
    @NotBlank(message = "Matricula es obligatoria")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Sólo se permiten valores alfanuméricos")
    private String matricula;
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;
}
