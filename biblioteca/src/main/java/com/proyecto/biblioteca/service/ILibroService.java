package com.proyecto.biblioteca.service;

import java.util.List;
import com.proyecto.biblioteca.model.Libro;

public interface ILibroService {

    //Creación de libros
    public void saveLibros(Libro Lib);

    //Traer todos los Libros
    public List<Libro> getLibros();

    // Traer un solo Libro
    public Libro findLibro(Long id);

    // Eliminar un solo Libro
    public boolean deleteLibro(Long id);

    // Modificar Libro 
    public Libro editLibro(Libro lib, Long id);

}
