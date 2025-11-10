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
import org.springframework.web.server.ResponseStatusException;
import com.proyecto.biblioteca.dto.PrestamoDTO;
import com.proyecto.biblioteca.dto.PrestamoInfoDTO;
import com.proyecto.biblioteca.service.IPrestamoService;

/**
 * Controlador REST responsable de gestionar las operaciones relacionadas con los préstamos.
 *
 * <p>Esta clase expone los métodos necesarios para administrar el ciclo de vida
 * de los préstamos dentro del sistema de gestión de biblioteca, incluyendo la
 * creación, consulta, actualización y eliminación de registros.</p>
 *
 * <p>El controlador delega la lógica de negocio en la capa de servicio
 * representada por {@link IPrestamoService}, manejando además las respuestas
 * HTTP y los códigos de estado apropiados para cada operación.</p>
 *
 * <p>Durante la creación y eliminación de préstamos, se controlan las excepciones
 * de tipo {@link ResponseStatusException} generadas por el servicio, permitiendo
 * devolver mensajes personalizados según el tipo de error ocurrido (por ejemplo,
 * usuario no encontrado o libro no disponible).</p>
 *
 */
@RestController
public class PrestamoController {

    @Autowired IPrestamoService prestServ;

    /**
     * Registra un nuevo préstamo en el sistema.
     *
     * <p>Válida la disponibilidad de los libros y la existencia del usuario
     * antes de crear el registro. En caso de error, retorna mensajes adecuados
     * con los códigos de estado HTTP correspondientes.</p>
     *
     * @param prestDTO objeto {@link PrestamoDTO} con los datos necesarios
     *                 para crear un préstamo.
     * @return una respuesta con estado {@code 201 Created} si se crea correctamente,
     *         o un código de error según el tipo de excepción capturada.
     */
    @PostMapping("/prestamos/crear")
    public ResponseEntity<?> savePrestamo(@RequestBody PrestamoDTO prestDTO){
        try {
            prestServ.savePrestamos(prestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Préstamo creado con exito");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al registrar el préstamo");
        }
    }

    /**
     * Obtiene la lista de todos los préstamos registrados en el sistema.
     *
     * @return una lista de objetos {@link PrestamoInfoDTO} con información de los préstamos,
     *         o un mensaje indicando que no existen registros si la lista está vacía.
     */
    @GetMapping("/prestamos/traer")
    public ResponseEntity<?> PrestamosCompletas(){
        List<PrestamoInfoDTO> Prestamos = prestServ.getPrestamos();
        
        if(Prestamos.isEmpty()){
            return ResponseEntity.ok("No hay prestamos registradas, por favor registre un prestamo");
        }
        else{
            return ResponseEntity.ok(Prestamos);
        } 
    }

    /**
     * Busca un préstamo específico por su identificador único.
     *
     * @param id identificador del préstamo a consultar.
     * @return el préstamo correspondiente en formato {@link PrestamoInfoDTO} y estado {@code 200 OK},
     *         o {@code 404 Not Found} si no existe el registro.
     */
    @GetMapping("/prestamos/traer/{id}")
    public ResponseEntity<?> findPrestamo(@PathVariable Long id){
        PrestamoInfoDTO prestDTO = prestServ.findPrestamo(id);
        if(prestDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el prestamo con Id "+id);
        }
        else{
            return ResponseEntity.ok(prestDTO);
        }
    }

    /**
     * Elimina un préstamo existente del sistema.
     *
     * <p>Durante la eliminación, se actualiza el estado de disponibilidad de los
     * libros asociados, liberándolos para futuros préstamos.</p>
     *
     * @param id identificador del préstamo a eliminar.
     * @return una respuesta con estado {@code 200 OK} si se elimina correctamente,
     *         o un mensaje de error si ocurre una excepción.
     */
    @DeleteMapping("/prestamos/eliminar/{id}")
    public ResponseEntity<?> deletePrestamo(@PathVariable Long id){
        try {
            prestServ.deletePrestamo(id);
        return ResponseEntity.ok("Préstamo eliminado exitosamente");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al eliminar el préstamo");
        }
    }


    /**
     * Modifica los datos de un préstamo existente.
     *
     * <p>Permite actualizar las fechas de inicio o entrega del préstamo,
     * conservando las relaciones establecidas con usuario y libros.</p>
     *
     * @param id identificador del préstamo a modificar.
     * @param prestInfoDTO objeto {@link PrestamoInfoDTO} con los nuevos valores a actualizar.
     * @return una respuesta con el préstamo actualizado y estado {@code 200 OK},
     *         o {@code 404 Not Found} si no se encuentra el préstamo.
     */
    @PutMapping("/prestamos/editar/{id}")
    public ResponseEntity<?> editPrestamo(@PathVariable Long id, @RequestBody PrestamoInfoDTO prestInfoDTO){ 
        
        PrestamoInfoDTO PrestamoActualizada = prestServ.editPrestamo(id, prestInfoDTO); 
        if(PrestamoActualizada == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Prestamo con Id "+id);
        }
        return ResponseEntity.ok(PrestamoActualizada);
    }

}
