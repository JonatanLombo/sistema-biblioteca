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

/**
 * Servicio que implementa la lógica de negocio asociada a la entidad {@link Prestamo}.
 *
 * <p>Esta clase actúa como intermediaria entre el controlador y el repositorio,
 * gestionando la creación, consulta, modificación y eliminación de préstamos dentro del
 * sistema de gestión biblioteca.</p>
 *
 * <p>Durante el proceso de creación, se aplican validaciones sobre la disponibilidad
 * de los libros y la existencia del usuario antes de registrar el préstamo.
 * Además, se actualiza el estado de disponibilidad de los libros asociados.</p>
 *
 * <p>El servicio utiliza transacciones ({@link jakarta.transaction.Transactional})
 * para asegurar la integridad de los datos durante las operaciones que afectan
 * múltiples entidades relacionadas.</p>
 *
 */
@Service
public class PrestamoService implements IPrestamoService {

    @Autowired 
    private IPrestamoRepository prestRepo;

    @Autowired
    private ILibroRepository libRepo;

    @Autowired
    private IUsuarioRepository usuaRepo;

    /**
     * Registra un nuevo préstamo en el sistema.
     *
     * <p>Valida la existencia del usuario y la disponibilidad de los libros antes de crear el préstamo.
     * Si alguno de los libros no está disponible o no se encuentra, lanza una excepción {@link ResponseStatusException}.
     * Una vez registrado el préstamo, los libros asociados cambian su disponibilidad a {@code false}.</p>
     *
     * @param prestDTO objeto {@link PrestamoDTO} con los datos necesarios para la creación del préstamo.
     * @throws ResponseStatusException si el usuario no existe, no se encuentran los libros seleccionados
     *         o alguno de ellos no está disponible.
     */
    @Override
    @Transactional
    public void savePrestamos(PrestamoDTO prestDTO) {
        Usuario usuario = usuaRepo.findById(prestDTO.getUsuarioId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario"));

        List<Libro> libros = libRepo.findAllById(prestDTO.getLibrosId());

        if (libros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontraron los libros seleccionados");
        }
        for (Libro libro : libros) {
            if (Boolean.FALSE.equals(libro.getDisponibilidad())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"El libro con ID " + libro.getIdLibro() + " no está disponible actualmente");
            }
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setFechaInicio(prestDTO.getFechaInicio());
        prestamo.setFechaEntrega(prestDTO.getFechaEntrega());
        prestamo.setListaLibros(libros);
        prestamo.setUnUsuario(usuario);

        prestRepo.save(prestamo);
        for (Libro libro : libros) {
            libro.setDisponibilidad(false);
        }
        libRepo.saveAll(libros);
    }

    /**
     * Recupera la lista completa de préstamos registrados.
     *
     * <p>Convierte cada entidad {@link Prestamo} en un objeto {@link PrestamoInfoDTO}
     * para ser utilizado en las respuestas del controlador.</p>
     *
     * @return lista de préstamos existentes representados como {@link PrestamoInfoDTO}.
     */
    @Override
    public List<PrestamoInfoDTO> getPrestamos(){

        List<Prestamo> listaPrestamos = prestRepo.findAll();
        List <PrestamoInfoDTO> listaDTO = new ArrayList<>();
        for(Prestamo prest: listaPrestamos){
            listaDTO.add(this.convertirDTO(prest));
        }
        return listaDTO;
    }

    /**
     * Busca un préstamo específico según su identificador.
     *
     * <p>Si no se encuentra el préstamo, retorna {@code null} para manejo adecuado
     * mediante {@code ResponseEntity} en la capa controladora.</p>
     *
     * @param id identificador único del préstamo.
     * @return objeto {@link PrestamoInfoDTO} con la información del préstamo, o {@code null} si no existe.
     */
    @Override
    public PrestamoInfoDTO findPrestamo(Long id) {
        Prestamo prest = prestRepo.findById(id).orElse(null);
        if(prest == null){
            return null;
        }
        return this.convertirDTO(prest);
    }

    /**
     * Elimina un préstamo existente y actualiza la disponibilidad de los libros asociados.
     *
     * <p>Los libros vinculados al préstamo son marcados nuevamente como disponibles
     * y se desvincula el préstamo antes de eliminarlo de la base de datos.</p>
     *
     * @param id identificador único del préstamo a eliminar.
     * @throws ResponseStatusException si el préstamo no se encuentra registrado.
     */
    @Override
    @Transactional
    public void deletePrestamo(Long id) {
    Prestamo prestamo = prestRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el préstamo con ID " + id));

    List<Libro> librosAsociados = prestamo.getListaLibros();
    for (Libro libro : librosAsociados) {
        libro.setDisponibilidad(true);
        // Desvincula el prestamo del libro para permitir eliminar
        libro.setUnPrestamo(null);
    }
    libRepo.saveAll(librosAsociados);

    prestRepo.delete(prestamo);
    }

    /**
     * Modifica los datos de un préstamo existente.
     *
     * <p>Permite actualizar las fechas de inicio o entrega de un préstamo registrado.
     * Si el préstamo no existe, retorna {@code null}.</p>
     *
     * @param id identificador único del préstamo a modificar.
     * @param prestInfoDTO objeto {@link PrestamoInfoDTO} con los nuevos valores a actualizar.
     * @return el préstamo actualizado representado como {@link PrestamoInfoDTO}, o {@code null} si no existe.
     */
    @Override
    public PrestamoInfoDTO editPrestamo(Long id, PrestamoInfoDTO prestInfoDTO) {
        Prestamo prest = prestRepo.findById(id).orElse(null);
        if(prest == null){
            return null;
        }

        if(prestInfoDTO.getFechaInicio() != null){
        prest.setFechaInicio(prestInfoDTO.getFechaInicio());
        }
        if(prestInfoDTO.getFechaEntrega() != null ){
        prest.setFechaEntrega(prestInfoDTO.getFechaEntrega());
        }

        Prestamo PrestamoActualizada = prestRepo.save(prest);
        return this.convertirDTO(PrestamoActualizada);
    }


    //------------------------------------------------------------------------

    /**
     * Convierte una entidad {@link Prestamo} en su representación {@link PrestamoInfoDTO}.
     *
     * <p>Este método se utiliza internamente para preparar los datos que serán expuestos
     * a través de la capa de presentación. Incluye la información del préstamo, los títulos
     * de los libros asociados y el nombre del usuario que realizó el préstamo.</p>
     *
     * @param prest entidad {@link Prestamo} a convertir.
     * @return objeto {@link PrestamoInfoDTO} con los datos del préstamo.
     */
    public PrestamoInfoDTO convertirDTO(Prestamo prest){
        PrestamoInfoDTO prestDTO = new PrestamoInfoDTO();

        prestDTO.setCodigoPrestamo(prest.getCodigoPrestamo());
        prestDTO.setFechaInicio(prest.getFechaInicio());
        prestDTO.setFechaEntrega(prest.getFechaEntrega());

        List<String> nomLib = new ArrayList<>();
        for(Libro listaLibros : prest.getListaLibros()){
            nomLib.add(listaLibros.getTitulo());
        }
        prestDTO.setTitulos(nomLib);

        prestDTO.setNomUsuario(prest.getUnUsuario().getNombre());
        return prestDTO;

    }


}
