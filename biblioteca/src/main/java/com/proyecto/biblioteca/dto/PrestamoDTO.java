package com.proyecto.biblioteca.dto;

import java.time.LocalDate;
import java.util.List;
import com.proyecto.biblioteca.model.Libro;
import com.proyecto.biblioteca.model.Prestamo;
import com.proyecto.biblioteca.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) que encapsula los datos necesarios para la creación de un nuevo préstamo.
 *
 * <p>Se utiliza para transferir la información requerida desde la capa de presentación o API
 * hacia la capa de servicio, evitando la exposición directa de la entidad {@link Prestamo}.</p>
 *
 * <p>Incluye las fechas de inicio y entrega del préstamo, el identificador del usuario solicitante
 * y la lista de identificadores de los libros que desea solicitar.</p>
 *
 * <p>Este objeto se emplea exclusivamente para operaciones de creación,
 * mientras que la gestión y persistencia se realizan mediante la entidad {@link Prestamo}.</p>
 *
 * @see Prestamo
 * @see Libro
 * @see Usuario
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PrestamoDTO {

    /**
     * Fecha de inicio del préstamo.
     *
     * <p>Debe ser una fecha presente o futura, según las reglas de negocio
     * establecidas para la creación de préstamos.</p>
     */
    private LocalDate fechaInicio;

    /**
     * Fecha de entrega o devolución del préstamo.
     *
     * <p>Debe ser una fecha posterior o igual a la de inicio, indicando
     * el plazo máximo para la devolución de los libros.</p>
     */
    private LocalDate fechaEntrega;

    /**
     * Lista de identificadores de los libros solicitados.
     *
     * <p>Contiene los IDs de los libros disponibles ({@code disponibilidad = true})
     * que el usuario desea incluir en el préstamo.</p>
     */
    private List<Long> librosId;

    /**
     * Identificador único del usuario solicitante del préstamo.
     *
     * <p>Se utiliza para establecer la relación entre el préstamo y el usuario
     * correspondiente durante la creación de la entidad {@link Prestamo}.</p>
     */
    private Long usuarioId;
}
