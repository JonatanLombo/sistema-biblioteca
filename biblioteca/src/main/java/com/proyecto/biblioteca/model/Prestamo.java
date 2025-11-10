package com.proyecto.biblioteca.model;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.biblioteca.dto.PrestamoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un préstamo dentro del sistema de gestión biblioteca.
 *
 * <p>Define los atributos y relaciones asociadas a un préstamo realizado por un usuario,
 * incluyendo la fecha de inicio, la fecha de devolución y los libros involucrados en la operación.</p>
 *
 * <p>La entidad se encuentra relacionada con un usuario mediante {@code unUsuario},
 * que representa al solicitante del préstamo, y con una colección de libros mediante {@code listaLibros},
 * los cuales corresponden a los ejemplares registrados en la base de datos.</p>
 *
 * <p>Solo los libros con disponibilidad establecida en {@code true} pueden ser prestados,
 * se ajusta automáticamente su estado a {@code false} una vez se asocian a un préstamo activo.</p>
 *
 *  <p>Para la creación de un nuevo préstamo, el sistema utiliza la clase {@link PrestamoDTO},
 *  la cual actúa como objeto de transferencia de datos (DTO) y encapsula la información
 *  necesaria para registrar el préstamo sin exponer directamente la entidad persistente.</p>
 *
 * <p>Esta clase está mapeada a la tabla <strong>prestamo</strong> en la base de datos
 * y es administrada por el contexto de persistencia de JPA.</p>
 *
 * @see Libro
 * @see Usuario
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Prestamo {

    /**
     * Identificador único del préstamo.
     *
     * <p>Generado automáticamente mediante la estrategia
     * {@link GenerationType#IDENTITY} al persistir la entidad.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoPrestamo;

    /**
     * Fecha de inicio del préstamo.
     *
     * <p>La fecha es obligatoria.</p>
     * <p>Debe ser una fecha presente o futura, de acuerdo con las restricciones
     * definidas en las reglas de negocio.</p>
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message= "La fecha de inicio debe ser futura o presente")
    private LocalDate fechaInicio;

    /**
     * Fecha de entrega del préstamo.
     *
     * <p>La fecha es obligatoria.</p>
     * <p>Debe ser una fecha futura a la fecha de inicio del préstamo.</p>
     */
    @NotNull(message = "La fecha de entrega es obligatoria")
    @Future(message = "La fecha de entrega debe ser futura")
    private LocalDate fechaEntrega;

    /**
     * Usuario asociado al préstamo.
     *
     * <p>Representa la relación entre el préstamo y el usuario que lo solicita.
     * Esta relación se establece mediante una asociación <strong>muchos a uno</strong>.</p>
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario unUsuario;

    /**
     * Lista de libros asociados al préstamo.
     *
     * <p>Define la relación entre el préstamo y los libros que el usuario solicita.
     * Solo pueden incluirse aquellos libros cuya disponibilidad esté establecida en {@code true}.</p>
     *
     * <p>Una vez registrados en el préstamo, los libros cambian su estado de disponibilidad a {@code false}.</p>
     *
     * <p>Se utiliza la anotación {@code @JsonIgnore} para evitar la serialización recursiva durante
     * la conversión a JSON, ya que la entidad {@link Libro} mantiene también una referencia a {@link Prestamo}.
     * Esto previene ciclos infinitos y optimiza la representación de datos en las respuestas REST.</p>
     */
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "prestamo_id")
    private List<Libro> listaLibros;

}
