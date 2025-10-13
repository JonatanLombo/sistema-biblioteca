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

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService usuaServ;


    //Endpoint creación de Usuarios
    @PostMapping("/usuarios/crear")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usua){
        // Control mensaje de retorno
        try {
            usuaServ.saveUsuarios(usua);
            // Status code 201, creado
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } catch (Exception e) {
            //Status code 500, muestra mensaje error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario" + e.getMessage());
        }
    }

    //Endpoint busqueda de todos los Usuarios
    @GetMapping("/usuarios/traer")
    public ResponseEntity<?> getUsuarios(){
        List<Usuario> usua = usuaServ.getUsuarios();
        // Control de mensaje retorno
        if(usua == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros");
        }
        return ResponseEntity.ok(usua);
    }

    //Endpoint busqueda de un Usuario especifico
    @GetMapping("/usuarios/traer/{id}")
    public ResponseEntity<?> findUsuario(@PathVariable Long id){
        Usuario usua = usuaServ.findUsuario(id);
        // Control de mensaje retorno
        if(usua == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con Id "+id);
        }
        return ResponseEntity.ok(usua);
    }

    //Endpoint eliminación de Usuario
    @DeleteMapping("/usuarios/eliminar/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){
        boolean delete = usuaServ.deleteUsuario(id);
         // Control de mensaje retorno
        if(delete){
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró al usuario con Id "+id);
        }   
    }

    //Endpoint modificación de Usuario
    @PutMapping("/usuarios/editar/{id}")
    public ResponseEntity<?> editUsuario(@RequestBody Usuario usua, @PathVariable Long id){
        Usuario usuaActualizado = usuaServ.editUsuario(usua, id);
        // Control de mensaje retorno
        if(usuaActualizado == null){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con Id "+id);
        }
        return ResponseEntity.ok(usuaActualizado);
    }


}   
