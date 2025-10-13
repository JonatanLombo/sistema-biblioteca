package com.proyecto.biblioteca.service;

import java.util.List;
import com.proyecto.biblioteca.model.Usuario;

public interface IUsuarioService {

    //Creación de clientes
    public void saveUsuarios(Usuario usua);

    //Traer todos los Usuarios
    public List<Usuario> getUsuarios();

    // Traer un solo Usuario
    public Usuario findUsuario(Long id);

    // Eliminar un solo Usuario
    public boolean deleteUsuario(Long id);

    // Modificar Usuario 
    public Usuario editUsuario(Usuario usua, Long id);

}
