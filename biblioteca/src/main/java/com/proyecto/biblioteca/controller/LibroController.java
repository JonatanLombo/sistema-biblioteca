package com.proyecto.biblioteca.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.proyecto.biblioteca.model.Libro;
import com.proyecto.biblioteca.service.ILibroService;

/**
 * Controlador REST responsable de gestionar las operaciones CRUD
 * relacionadas con la entidad {@link Libro}.
 *
 * <p>Esta clase actúa como punto de entrada para las solicitudes HTTP
 * dirigidas a la gestión de libros, delegando la lógica de negocio en el
 * servicio {@link ILibroService}.</p>
 *
 * <p>Proporciona métodos para crear, consultar, actualizar y eliminar
 * registros de libros, retornando respuestas HTTP adecuadas según el
 * resultado de cada operación.</p>
 *
 */
@RestController
public class LibroController {

    @Autowired
    private ILibroService libServ;


    /**
     * Crea un nuevo libro en el sistema.
     *
     * @param lib objeto {@link Libro} con los datos del libro a registrar.
     * @return una respuesta con estado {@code 201 Created} si se registra correctamente,
     *         o {@code 500 Internal Server Error} si ocurre un error al guardar.
     */
    @PostMapping("/libros/crear")
    public ResponseEntity<?> saveLibro(@RequestBody Libro lib){
        try {
            libServ.saveLibros(lib);
            return ResponseEntity.status(HttpStatus.CREATED).body("Libro creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Libro" + e.getMessage());
        }
    }

    /**
     * Obtiene la lista de todos los libros registrados.
     *
     * @return una lista de libros y estado {@code 200 OK}, o {@code 404 Not Found}
     *         si no existen registros.
     */
    @GetMapping("/libros/traer")
    public ResponseEntity<?> getLibros(){
        List<Libro> lib = libServ.getLibros();
        if(lib == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros");
        }
        return ResponseEntity.ok(lib);
    }

    /**
     * Busca un libro por su identificador único.
     *
     * @param id identificador del libro a consultar.
     * @return el libro correspondiente y estado {@code 200 OK}, o {@code 404 Not Found}
     *         si no existe.
     */
    @GetMapping("/libros/traer/{id}")
    public ResponseEntity<?> findLibro(@PathVariable Long id){
        Libro lib = libServ.findLibro(id);
        if(lib == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Libro con Id "+id);
        }
        return ResponseEntity.ok(lib);
    }


    /**
     * Elimina un libro existente.
     *
     * @param id identificador del libro a eliminar.
     * @return una respuesta con estado {@code 200 OK} si se elimina correctamente,
     *         o {@code 404 Not Found} si el libro no existe.
     */
    @DeleteMapping("/libros/eliminar/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id){
        boolean delete = libServ.deleteLibro(id);
        if(delete){
            return ResponseEntity.ok("Libro eliminado exitosamente");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró al Libro con Id "+id);
        }   
    }


    /**
     * Actualiza los datos de un libro existente.
     *
     * @param lib objeto {@link Libro} con los nuevos valores a actualizar.
     * @param id identificador del libro a modificar.
     * @return el libro actualizado y estado {@code 200 OK}, o {@code 404 Not Found}
     *         si el libro no existe.
     */
    @PutMapping("/libros/editar/{id}")
    public ResponseEntity<?> editLibro(@RequestBody Libro lib, @PathVariable Long id){
        Libro libActualizado = libServ.editLibro(lib, id);
        if(libActualizado == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Libro con Id "+id);
        }
        return ResponseEntity.ok(libActualizado);
    }


}
