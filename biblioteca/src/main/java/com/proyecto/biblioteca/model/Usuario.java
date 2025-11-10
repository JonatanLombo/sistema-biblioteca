package com.proyecto.biblioteca.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un usuario dentro del sistema de gestión biblioteca.
 *
 * <p>Define los atributos persistentes de un usuario y aplica validaciones
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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    /**
     * Nombre del usuario.
     *
     * <p>No puede estar vacío y es requerido para el registro de la entidad.</p>
     * <p>No puede superar los 40 caracteres.</p>
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 40, message = "El nombre no puede superar los 40 caracteres")
    private String nombre;

    /**
     * Apellido del usuario.
     *
     * <p>No puede estar vacío y es requerido para el registro de la entidad.</p>
     * <p>No puede superar los 40 caracteres.</p>
     */
    private String apellido;

    /**
     * Número de documento del usuario.
     *
     * <p>Campo obligatorio dentro del sistema.</p>
     */
    @NotBlank(message = "El número de documento es obligatorio")
    private String documento;

    /**
     * Lista de préstamos asociados al usuario.
     *
     * <p>Representa la relación entre el usuario y los préstamos que ha solicitado
     * dentro del sistema de gestión biblioteca.</p>
     *
     * <p>Cada elemento de la lista corresponde a un registro de {@link Prestamo},
     * que contiene la información sobre los libros solicitados por el usuario.</p>
     *
     * <p>Esta relación se define como <strong>uno a muchos</strong> desde el usuario hacia los préstamos,
     * indicando que un usuario puede tener múltiples préstamos registrados.</p>
     *
     * <p>Se utiliza la anotación {@code @JsonIgnore} para evitar la serialización recursiva
     * durante la conversión a JSON, ya que la entidad {@link Prestamo} también
     * mantiene una referencia al usuario.</p>
     */
    @JsonIgnore
    @OneToMany(mappedBy = "unUsuario")
    private List<Prestamo> listaPrestamos;

}
