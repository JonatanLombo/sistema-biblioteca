package com.proyecto.biblioteca.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PrestamoDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaEntrega;
    private List<Long> librosId;
    private Long usuarioId;
}
