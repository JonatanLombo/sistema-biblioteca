package com.proyecto.biblioteca.service;

import java.util.List;
import com.proyecto.biblioteca.model.Libro;


/**
 * Interfaz que define las operaciones del servicio relacionadas con la entidad {@link Libro}.
 *
 * <p>Proporciona los métodos necesarios para gestionar el ciclo de vida de los libros,
 * incluyendo creación, consulta, actualización y eliminación de registros.</p>
 *
 * <p>Su implementación concreta delega la persistencia en el repositorio correspondiente
 * y aplica la lógica de negocio necesaria antes de interactuar con la base de datos.</p>
 */
public interface ILibroService {

    /**
     * Crea un nuevo registro de libro.
     *
     * <p>Valida los datos del objeto recibido antes de persistirlo.</p>
     *
     * @param Lib objeto {@link Libro} que contiene los datos a guardar.
     */
    public void saveLibros(Libro Lib);

    /**
     * Obtiene la lista completa de libros registrados en el sistema.
     *
     * @return una lista de objetos {@link Libro}.
     */
    public List<Libro> getLibros();

    /**
     * Busca un libro por su identificador único.
     *
     * @param id identificador del libro a buscar.
     */
    public Libro findLibro(Long id);

    /**
     * Elimina el registro de un libro existente.
     *
     * @param id identificador único del libro a eliminar.
     */
    public boolean deleteLibro(Long id);

    /**
     * Actualiza parcialmente los datos de un libro existente.
     *
     * <p>Solo se modifican los campos no nulos o no vacíos del objeto {@link Libro}.
     * Si el libro no existe, la operación no realiza cambios.</p>
     *
     * @param id identificador único del libro a actualizar.
     * @param lib objeto {@link Libro} con los campos a modificar.
     */
    public Libro editLibro(Libro lib, Long id);

}
