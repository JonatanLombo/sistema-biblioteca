package com.proyecto.biblioteca.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.biblioteca.model.Libro;
import com.proyecto.biblioteca.repository.ILibroRepository;

@Service
public class LibroService implements ILibroService {

    @Autowired
    private ILibroRepository libRepo;

    //Creación de Libros
    @Override
    public void saveLibros(Libro lib){
        libRepo.save(lib);
    }

    //Traer todos los Libros
    @Override
    public List<Libro> getLibros() {
        List<Libro> listaLibros = libRepo.findAll();
        // Retorno para manejo de ResponseEntity
        if(listaLibros.isEmpty()){
            return null;
        }
        return listaLibros;
    }

    // Traer un solo Libro
    @Override
    public Libro findLibro(Long id) {
        Libro lib = libRepo.findById(id).orElse(null);
        // Retorno para ,amejo de ResponseEntity
        if(lib == null){
            return null;
        }
        return lib;
    }

    // Eliminación de un solo Libro
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

    // Edición de un solo Libro
    @Override
    public Libro editLibro(Libro lib, Long id) {
        // Trae el objeto completo por id
        Libro Libro = libRepo.findById(id).orElse(null);
        // Retorno para ,amejo de ResponseEntity
        if(Libro == null){
            return null;
        }
        // Confirma que el titulo no esté vacio y permite editar los dos atributos o uno de los dos sin retorno null
        if(lib.getTitulo() != null && !lib.getTitulo().isBlank()){
        Libro.setTitulo(lib.getTitulo());
        }
        if(lib.getEditorial() != null && !lib.getEditorial().isBlank()){
        Libro.setEditorial(lib.getEditorial());
        }
        // Guarda y retorna el nuevo objeto
        return libRepo.save(Libro);
    }

}
