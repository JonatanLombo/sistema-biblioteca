package com.proyecto.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.proyecto.biblioteca.dto.PrestamoDTO;
import com.proyecto.biblioteca.dto.PrestamoInfoDTO;
import com.proyecto.biblioteca.model.Libro;
import com.proyecto.biblioteca.model.Prestamo;
import com.proyecto.biblioteca.model.Usuario;
import com.proyecto.biblioteca.repository.ILibroRepository;
import com.proyecto.biblioteca.repository.IPrestamoRepository;
import com.proyecto.biblioteca.repository.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PrestamoService implements IPrestamoService {

    @Autowired 
    private IPrestamoRepository prestRepo;
    @Autowired
    private ILibroRepository libRepo;
    @Autowired
    private IUsuarioRepository usuaRepo;

    

    //Creación de prestamos
    @Override
    @Transactional
    public void savePrestamos(PrestamoDTO prestDTO) {

        // Trae el objeto usuario
        Usuario usuario = usuaRepo.findById(prestDTO.getUsuarioId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario"));

        // Trae los libros seleccionados
        List<Libro> libros = libRepo.findAllById(prestDTO.getLibrosId());

        if (libros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontraron los libros seleccionados");
        }
        // Verifica disponibilidad de todos los libros antes de crear el préstamo
        for (Libro libro : libros) {
            if (Boolean.FALSE.equals(libro.getDisponibilidad())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"El libro con ID " + libro.getIdLibro() + " no está disponible actualmente");
            }
        }

        // Crea el prestamo
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaInicio(prestDTO.getFechaInicio());
        prestamo.setFechaEntrega(prestDTO.getFechaEntrega());
        prestamo.setListaLibros(libros);
        prestamo.setUnUsuario(usuario);

        // Guarda el prestamo
        prestRepo.save(prestamo);
        // Actualiza la disponibilidad de los libros a FALSE
        for (Libro libro : libros) {
            libro.setDisponibilidad(false);
        }
        libRepo.saveAll(libros);
    }


    //Traer todas las prestamos
    @Override
    public List<PrestamoInfoDTO> getPrestamos(){

        List<Prestamo> listaPrestamos = prestRepo.findAll();
        List <PrestamoInfoDTO> listaDTO = new ArrayList<>();

        for(Prestamo prest: listaPrestamos){
            listaDTO.add(this.convertirDTO(prest));
        }
        return listaDTO;
    } 


    // Traer un solo prestamo
    @Override
    public PrestamoInfoDTO findPrestamo(Long id) {

        Prestamo prest = prestRepo.findById(id).orElse(null);

        //Retorno para manejo de ResponseEntity
        if(prest == null){
            return null;
        }
        return this.convertirDTO(prest);
    }


    // Eliminación de una sola prestamo
    @Override
    @Transactional
    public void deletePrestamo(Long id) {
    // Busca el prestamo por su ID
    Prestamo prestamo = prestRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el préstamo con ID " + id));

    // Trae los libros asociados al préstamo
    List<Libro> librosAsociados = prestamo.getListaLibros();

    // Marca los libros como disponibles nuevamente
    for (Libro libro : librosAsociados) {
        libro.setDisponibilidad(true);
        // Desvincula el prestamo del libro para permitir eliminar
        libro.setUnPrestamo(null);
    }

    // Guarda los libros actualizados
    libRepo.saveAll(librosAsociados);

    // Elimina el prestamo
    prestRepo.delete(prestamo);
    }


    // Edición de una sola prestamo
    @Override
    public PrestamoInfoDTO editPrestamo(Long id, PrestamoInfoDTO prestInfoDTO) {
        //Trae el objeto de la Prestamo por Id 
        Prestamo prest = prestRepo.findById(id).orElse(null);
        //Retorno para manejo de ResponseEntity
        if(prest == null){
            return null;
        }
        // Editar atributos de prestamo
        // Confirma que el titulo no esté vacio y permite editar los dos atributos o uno de los dos sin retorno null
        if(prestInfoDTO.getFechaInicio() != null){
        prest.setFechaInicio(prestInfoDTO.getFechaInicio());
        }
        if(prestInfoDTO.getFechaEntrega() != null ){
        prest.setFechaEntrega(prestInfoDTO.getFechaEntrega());
        }
        // Guarda el nuevo valor 
        Prestamo PrestamoActualizada = prestRepo.save(prest);   
        //Convierte a DTO el resultado
        return this.convertirDTO(PrestamoActualizada);
    }


    //------------------------------------------------------------------------

    // Trae los valores de prestamo y los Settea en DTO de info
    public PrestamoInfoDTO convertirDTO(Prestamo prest){
        PrestamoInfoDTO prestDTO = new PrestamoInfoDTO();

        // Atributos del prestamo a mostrar
        prestDTO.setCodigoPrestamo(prest.getCodigoPrestamo());
        prestDTO.setFechaInicio(prest.getFechaInicio());
        prestDTO.setFechaEntrega(prest.getFechaEntrega());

        // Atributos de libro a mostrar
        List<String> nomLib = new ArrayList<>();
        for(Libro listaLibros : prest.getListaLibros()){
            nomLib.add(listaLibros.getTitulo());
        }
        prestDTO.setTitulos(nomLib);

        // Atributos a mostrar de usuario
        prestDTO.setNomUsuario(prest.getUnUsuario().getNombre());

        // Se retorna el objeto completo
        return prestDTO;

    }


}
