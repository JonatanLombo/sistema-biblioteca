package com.proyecto.biblioteca.dto;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) que representa la información de un préstamo existente
 * dentro del sistema de gestión biblioteca.
 *
 * <p>Se utiliza para las operaciones de lectura y actualización de información
 * en el ciclo de vida del préstamo, permitiendo consultar, modificar o mostrar
 * los datos asociados a un préstamo ya registrado.</p>
 *
 * <p>A diferencia de {@link PrestamoDTO}, que se emplea exclusivamente para la creación
 * de nuevos préstamos, esta clase encapsula los datos que representan un préstamo
 * persistente, incluyendo identificadores, fechas y relaciones con usuario y libros.</p>
 *
 * <p>Su principal función es servir como objeto de transferencia entre la capa de
 * persistencia y la capa de presentación en las operaciones de tipo <strong>read</strong>
 * y <strong>update</strong> del CRUD.</p>
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PrestamoInfoDTO {

    /**
     * Código único del préstamo.
     *
     * <p>Permite identificar el préstamo al realizar consultas o actualizaciones.</p>
     */
    private Long codigoPrestamo;

    /**
     * Fecha de inicio del préstamo.
     *
     * <p>Corresponde a la fecha en que el préstamo fue creado o actualizado.</p>
     */
    private LocalDate fechaInicio;

    /**
     * Fecha límite de devolución del préstamo.
     *
     * <p>Indica el plazo máximo establecido para la devolución de los libros asociados.</p>
     */
    private LocalDate fechaEntrega;

    /**
     * Lista de títulos de los libros asociados al préstamo.
     *
     * <p>Contiene los nombres de los libros actualmente vinculados al préstamo,
     * utilizados para la visualización de información en consultas o reportes.</p>
     */
    private List<String> titulos;

    /**
     * Nombre del usuario asociado al préstamo.
     *
     * <p>Permite identificar al solicitante del préstamo en las operaciones de lectura
     * o modificación de la información.</p>
     */
    private String nomUsuario;

}
