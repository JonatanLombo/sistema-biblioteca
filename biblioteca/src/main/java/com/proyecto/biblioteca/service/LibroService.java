package com.proyecto.biblioteca.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.biblioteca.model.Libro;
import com.proyecto.biblioteca.repository.ILibroRepository;

/**
 * Servicio que implementa las operaciones de negocio relacionadas con la entidad {@link Libro}.
 *
 * <p>Esta clase actúa como capa intermedia entre el controlador y el repositorio,
 * gestionando la lógica necesaria para la creación, consulta, edición y eliminación
 * de libros dentro del sistema de gestión biblioteca.</p>
 *
 * <p>Delegando la persistencia en {@link ILibroRepository}, garantiza la integridad
 * de los datos y centraliza las reglas de negocio aplicables a la entidad {@code Libro}.</p>
 *
 */
@Service
public class LibroService implements ILibroService {

    @Autowired
    private ILibroRepository libRepo;

    /**
     * Registra un nuevo libro en el sistema.
     *
     * <p>Guarda el libro recibido en la base de datos mediante el repositorio.
     * Por defecto, el libro se crea con disponibilidad establecida en {@code true}.</p>
     *
     * @param lib objeto {@link Libro} que contiene los datos del nuevo libro a registrar.
     */
    @Override
    public void saveLibros(Libro lib){
        libRepo.save(lib);
    }

    /**
     * Obtiene la lista completa de libros registrados en el sistema.
     *
     * <p>Devuelve todos los libros almacenados en la base de datos.
     * Si no existen registros, retorna {@code null} para permitir un manejo adecuado
     * en la capa de controlador mediante {@code ResponseEntity}.</p>
     *
     * @return una lista de objetos {@link Libro} o {@code null} si no hay registros.
     */
    @Override
    public List<Libro> getLibros() {
        List<Libro> listaLibros = libRepo.findAll();
        if(listaLibros.isEmpty()){
            return null;
        }
        return listaLibros;
    }

    /**
     * Busca un libro específico según su identificador único.
     *
     * <p>Si no se encuentra el libro solicitado, retorna {@code null} para
     * permitir un manejo adecuado en la capa de respuesta HTTP.</p>
     *
     * @param id identificador único del libro a buscar.
     * @return el objeto {@link Libro} correspondiente o {@code null} si no existe.
     */
    @Override
    public Libro findLibro(Long id) {
        Libro lib = libRepo.findById(id).orElse(null);
        if(lib == null){
            return null;
        }
        return lib;
    }

    /**
     * Elimina un libro del sistema según su identificador.
     *
     * <p>Antes de eliminarlo, verifica su existencia en la base de datos.
     * Si el libro no existe, retorna {@code false}.</p>
     *
     * @param id identificador único del libro a eliminar.
     * @return {@code true} si el libro fue eliminado exitosamente; {@code false} si no existe.
     */
    @Override
    public boolean deleteLibro(Long id) {
        if(libRepo.existsById(id)){
            libRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Modifica los datos de un libro existente.
     *
     * <p>Permite actualizar el título o la editorial del libro identificado por su {@code id}.
     * Solo se actualizan los campos que contengan valores no nulos y no vacíos.</p>
     *
     * <p>Si el libro no existe, retorna {@code null}.</p>
     *
     * @param lib objeto {@link Libro} con los nuevos valores a actualizar.
     * @param id identificador único del libro a modificar.
     * @return el libro actualizado, o {@code null} si no se encontró el registro original.
     */
    @Override
    public Libro editLibro(Libro lib, Long id) {
        Libro Libro = libRepo.findById(id).orElse(null);
        if(Libro == null){
            return null;
        }
        if(lib.getTitulo() != null && !lib.getTitulo().isBlank()){
        Libro.setTitulo(lib.getTitulo());
        }
        if(lib.getEditorial() != null && !lib.getEditorial().isBlank()){
        Libro.setEditorial(lib.getEditorial());
        }

        return libRepo.save(Libro);
    }

}
