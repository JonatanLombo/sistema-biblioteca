package com.proyecto.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoPrestamo;
    private LocalDate fechaInicio;
    private LocalDate fechaEntrega;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario unUsuario;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "prestamo_id")
    private List<Libro> listaLibros;

}
