package com.proyecto.biblioteca.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.biblioteca.model.Usuario;
import com.proyecto.biblioteca.repository.IUsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {


    @Autowired
    private IUsuarioRepository usuaRepo;


    //Creación de Usuarios
    @Override
    public void saveUsuarios(Usuario usua) {
        usuaRepo.save(usua);
    }

    //Traer todos los Usuarios
    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> listaUsuarios = usuaRepo.findAll();
        // Retorno para manejo de ResponseEntity
        if(listaUsuarios.isEmpty()){
            return null;
        }
        return listaUsuarios;
    }

    // Traer un solo Usuario
    @Override
    public Usuario findUsuario(Long id) {
        Usuario usua = usuaRepo.findById(id).orElse(null);
        // Retorno para ,amejo de ResponseEntity
        if(usua == null){
            return null;
        }
        return usua;
    }

    // Eliminación de un solo Usuario
    @Override
    public boolean deleteUsuario(Long id) {
        if(usuaRepo.existsById(id)){
            usuaRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    // Edición de un solo Usuario
    @Override
    public Usuario editUsuario(Usuario usua, Long id) {
        // Trae el objeto completo por id
        Usuario usuario = usuaRepo.findById(id).orElse(null);
        // Retorno para ,amejo de ResponseEntity
        if(usuario == null){
            return null;
        }
        // Confirma que el titulo no esté vacio y permite editar los dos atributos o uno de los dos sin retorno null
        if(usua.getNombre() != null && !usua.getNombre().isBlank()){
        usuario.setNombre(usua.getNombre());
        }
        if(usua.getApellido() != null && !usua.getApellido().isBlank()){
        usuario.setApellido(usua.getApellido());
        }
         if(usua.getDocumento() != null && !usua.getDocumento().isBlank()){
        usuario.setDocumento(usua.getDocumento());
        }
        // Guarda y retorna el nuevo objeto
        return usuaRepo.save(usuario);
    }


}
