package com.proyecto.biblioteca.service;

import java.util.List;
import com.proyecto.biblioteca.dto.PrestamoDTO;
import com.proyecto.biblioteca.dto.PrestamoInfoDTO;
import com.proyecto.biblioteca.model.Libro;
import com.proyecto.biblioteca.model.Prestamo;
import com.proyecto.biblioteca.model.Usuario;

/**
 * Interfaz que define las operaciones del servicio relacionadas con la entidad {@link Prestamo}.
 *
 * <p>Proporciona los métodos necesarios para gestionar el ciclo de vida de los préstamos,
 * incluyendo la creación, consulta, actualización y eliminación de registros.</p>
 *
 * <p>Su implementación concreta delega las operaciones de persistencia al repositorio correspondiente
 * y aplica la lógica de negocio necesaria antes de interactuar con la base de datos, asegurando
 * la integridad y consistencia de las relaciones entre {@link Usuario} y {@link Libro}.</p>
 *
 */
public interface IPrestamoService {

    /**
     * Crea un nuevo préstamo en el sistema.
     *
     * <p>Utiliza los datos proporcionados en el {@link PrestamoDTO} para registrar un nuevo préstamo,
     * asociando un usuario con una lista de libros disponibles.
     * Los libros incluidos pasan a tener su disponibilidad establecida en {@code false}.</p>
     *
     * @param prestDTO objeto que contiene los datos necesarios para la creación del préstamo.
     */
    public void savePrestamos(PrestamoDTO prestDTO);

    /**
     * Obtiene la lista completa de préstamos registrados.
     *
     * <p>Devuelve todos los préstamos almacenados en el sistema, incluyendo la información
     * de las fechas, el usuario asociado y los títulos de los libros prestados.</p>
     *
     * @return una lista de objetos {@link PrestamoInfoDTO} que representan los préstamos existentes.
     */
    public List<PrestamoInfoDTO> getPrestamos();

    /**
     * Busca un préstamo específico según su identificador.
     *
     * <p>Permite consultar la información detallada de un préstamo existente,
     * incluyendo los datos del usuario y los libros asociados.</p>
     *
     * @param id identificador único del préstamo a buscar.
     * @return un objeto {@link PrestamoInfoDTO} con la información del préstamo encontrado,
     *         o {@code null} si no existe.
     */
    public PrestamoInfoDTO findPrestamo(Long id);

    /**
     * Elimina un préstamo del sistema según su identificador.
     *
     * <p>Al eliminar un préstamo, se pueden actualizar las disponibilidades de los libros asociados,
     * restaurando su estado a {@code true} para permitir nuevos préstamos.</p>
     *
     * @param id identificador único del préstamo a eliminar.
     */
    public void deletePrestamo(Long id);

    /**
     * Modifica la información de un préstamo existente.
     *
     * <p>Permite actualizar las fechas o los datos relacionados con un préstamo ya registrado.
     * Este método también puede validar la disponibilidad de los libros antes de aplicar los cambios.</p>
     *
     * @param id identificador único del préstamo a modificar.
     * @param PrestInfoDTO objeto {@link PrestamoInfoDTO} que contiene los nuevos valores a actualizar.
     * @return el préstamo actualizado representado como {@link PrestamoInfoDTO}.
     */
    public PrestamoInfoDTO editPrestamo(Long id, PrestamoInfoDTO PrestInfoDTO);

}
