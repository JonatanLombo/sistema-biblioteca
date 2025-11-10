package com.proyecto.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un libro dentro del sistema de gestión biblioteca.
 *
 * <p>Define los atributos persistentes de un libro y aplica validaciones
 * mediante anotaciones de Jakarta Validation para garantizar la integridad
 * de los datos en operaciones de creación.</p>
 *
 * <p>Esta clase se encuentra mapeada a una tabla de base de datos y
 * es administrada por el contexto de persistencia de JPA.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Libro {

    /**
     * Identificador único del libro.
     *
     * <p>Generado automáticamente mediante la estrategia
     * {@link GenerationType#IDENTITY} al persistir la entidad.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    /**
     * Titulo del libro.
     *
     *<p>No puede estar vacío y es requerido para el registro de la entidad.</p>
     *<p>No puede superar los 40 caracteres.</p>
     */
    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 40, message = "El titulo no puede superar los 40 caracteres")
    private String titulo;

    /**
     * Nombre editorial.
     *
     *<p>No puede estar vacío y es requerido para el registro de la entidad.</p>
     *<p>No puede superar los 40 caracteres.</p>
     */
    @NotBlank(message = "La editorial es obligatorio")
    @Size(max = 40, message = "La editorial no puede superar los 40 caracteres")
    private String editorial;

    /**
     * Indica la disponibilidad del libro.
     *
     * <p>Se inicializa en {@code true}, lo que significa que el libro está disponible.</p>
     * <p>Cuando el libro se presta, su valor cambia a {@code false}, indicando que ya no está disponible.</p>
     */
    @Column(nullable = false)
    private Boolean disponibilidad = true;

    /**
     * Préstamo asociado al libro.
     *
     * <p>Establece la relación entre el libro y el préstamo al que pertenece.</p>
     * <p>Cuando se crea un préstamo, se modifica la disponibilidad del libro para reflejar que ya no está disponible.</p>
     */
    @ManyToOne
    @JoinColumn(name = "prestamo_id", insertable = false, updatable = false)
    private Prestamo unPrestamo;
}
