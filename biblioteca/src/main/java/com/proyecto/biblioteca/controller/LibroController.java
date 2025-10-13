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

@RestController
public class LibroController {

    @Autowired
    private ILibroService libServ;


    //Endpoint creación de Libros
    @PostMapping("/libros/crear")
    public ResponseEntity<?> saveLibro(@RequestBody Libro lib){
        // Control mensaje de retorno
        try {
            libServ.saveLibros(lib);
            // Status code 201, creado
            return ResponseEntity.status(HttpStatus.CREATED).body("Libro creado exitosamente");
        } catch (Exception e) {
            //Status code 500, muestra mensaje error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Libro" + e.getMessage());
        }
    }


    //Endpoint busqueda de todos los Libros
    @GetMapping("/libros/traer")
    public ResponseEntity<?> getLibros(){
        List<Libro> lib = libServ.getLibros();
        // Control de mensaje retorno
        if(lib == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros");
        }
        return ResponseEntity.ok(lib);
    }


    //Endpoint busqueda de un Libro especifico
    @GetMapping("/libros/traer/{id}")
    public ResponseEntity<?> findLibro(@PathVariable Long id){
        Libro lib = libServ.findLibro(id);
        // Control de mensaje retorno
        if(lib == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Libro con Id "+id);
        }
        return ResponseEntity.ok(lib);
    }


    //Endpoint eliminación de Libro
    @DeleteMapping("/libros/eliminar/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id){
        boolean delete = libServ.deleteLibro(id);
         // Control de mensaje retorno
        if(delete){
            return ResponseEntity.ok("Libro eliminado exitosamente");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró al Libro con Id "+id);
        }   
    }


    //Endpoint modificación de Libro
    @PutMapping("/libros/editar/{id}")
    public ResponseEntity<?> editLibro(@RequestBody Libro lib, @PathVariable Long id){
        Libro libActualizado = libServ.editLibro(lib, id);
        // Control de mensaje retorno
        if(libActualizado == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Libro con Id "+id);
        }
        return ResponseEntity.ok(libActualizado);
    }


}
