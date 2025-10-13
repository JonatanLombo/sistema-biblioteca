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

@RestController
public class PrestamoController {

    @Autowired IPrestamoService prestServ;

    //Endpoint creación de prestamos
    @PostMapping("/prestamos/crear")
    public ResponseEntity<?> savePrestamo(@RequestBody PrestamoDTO prestDTO){
        try {
            prestServ.savePrestamos(prestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Préstamo creado con exito");
        } catch (ResponseStatusException e) {
            // Captura los errores personalizados del servicio
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            // Captura cualquier otro error no previsto
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al registrar el préstamo");
        }
    }

    //Endpoint busqueda de prestamos
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


    //Endpoint busqueda de un prestamo especifico
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
    

    //Endpoint eliminación de prestamos
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


    //Endpoint modificación de Prestamo
    @PutMapping("/prestamos/editar/{id}")
    public ResponseEntity<?> editPrestamo(@PathVariable Long id, @RequestBody PrestamoInfoDTO prestInfoDTO){ 
        
        PrestamoInfoDTO PrestamoActualizada = prestServ.editPrestamo(id, prestInfoDTO); 
        if(PrestamoActualizada == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Prestamo con Id "+id);
        }
        return ResponseEntity.ok(PrestamoActualizada);
    }

}
