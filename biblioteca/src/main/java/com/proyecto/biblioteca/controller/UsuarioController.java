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
import com.proyecto.biblioteca.model.Usuario;
import com.proyecto.biblioteca.service.IUsuarioService;

/**
 * Controlador REST responsable de gestionar las operaciones CRUD
 * relacionadas con la entidad {@link Usuario}.
 *
 * <p>Esta clase actúa como punto de entrada para las solicitudes HTTP
 * dirigidas a la gestión de usuarios, delegando la lógica de negocio en el
 * servicio {@link IUsuarioService}.</p>
 *
 * <p>Proporciona métodos para crear, consultar, actualizar y eliminar
 * registros de usuarios, retornando respuestas HTTP adecuadas según el
 * resultado de cada operación.</p>
 *
 */
@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService usuaServ;


    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param usua objeto {@link Usuario} con los datos del usuario a registrar.
     * @return una respuesta con estado {@code 201 Created} si se registra correctamente,
     *         o {@code 500 Internal Server Error} si ocurre un error al guardar.
     */
    @PostMapping("/usuarios/crear")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usua){
        try {
            usuaServ.saveUsuarios(usua);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario" + e.getMessage());
        }
    }


    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return una lista de usuarios y estado {@code 200 OK}, o {@code 404 Not Found}
     *         si no existen registros.
     */
    @GetMapping("/usuarios/traer")
    public ResponseEntity<?> getUsuarios() {
        List<Usuario> usua = usuaServ.getUsuarios();
        if(usua == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros");
        }
        return ResponseEntity.ok(usua);
    }

    /**
     * Busca un libro por su identificador único.
     *
     * @param id identificador del libro a consultar.
     * @return el libro correspondiente y estado {@code 200 OK}, o {@code 404 Not Found}
     *         si no existe.
     */
    @GetMapping("/usuarios/traer/{id}")
    public ResponseEntity<?> findUsuario(@PathVariable Long id){
        Usuario usua = usuaServ.findUsuario(id);
        if(usua == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con Id "+id);
        }
        return ResponseEntity.ok(usua);
    }

    /**
     * Elimina un usuario existente.
     *
     * @param id identificador del usuario a eliminar.
     * @return una respuesta con estado {@code 200 OK} si se elimina correctamente,
     *         o {@code 404 Not Found} si el usuario no existe.
     */
    @DeleteMapping("/usuarios/eliminar/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){
        boolean delete = usuaServ.deleteUsuario(id);
        if(delete){
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró al usuario con Id "+id);
        }   
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usua objeto {@link Usuario} con los nuevos valores a actualizar.
     * @param id identificador del usuario a modificar.
     * @return el usuario actualizado y estado {@code 200 OK}, o {@code 404 Not Found}
     *         si el usuario no existe.
     */
    @PutMapping("/usuarios/editar/{id}")
    public ResponseEntity<?> editUsuario(@RequestBody Usuario usua, @PathVariable Long id){
        Usuario usuaActualizado = usuaServ.editUsuario(usua, id);
        if(usuaActualizado == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con Id "+id);
        }
        return ResponseEntity.ok(usuaActualizado);
    }


}   
