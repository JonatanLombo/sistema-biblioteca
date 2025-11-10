package com.proyecto.biblioteca.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.biblioteca.model.Usuario;
import com.proyecto.biblioteca.repository.IUsuarioRepository;

/**
 * Servicio que implementa las operaciones de negocio relacionadas con la entidad {@link Usuario}.
 *
 * <p>Esta clase actúa como capa intermedia entre el controlador y el repositorio,
 * gestionando la lógica necesaria para la creación, consulta, edición y eliminación
 * de libros dentro del sistema de gestión biblioteca.</p>
 *
 * <p>Delegando la persistencia en {@link IUsuarioRepository}, garantiza la integridad
 * de los datos y centraliza las reglas de negocio aplicables a la entidad {@code Libro}.</p>
 *
 */
@Service
public class UsuarioService implements IUsuarioService {


    @Autowired
    private IUsuarioRepository usuaRepo;


    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usua objeto {@link Usuario} que contiene los datos del nuevo usuario a registrar.
     */
    @Override
    public void saveUsuarios(Usuario usua) {
        usuaRepo.save(usua);
    }

    /**
     * Obtiene la lista completa de usuarios registrados.
     *
     * @return una lista de objetos {@link Usuario}, o {@code null} si no existen registros.
     */
    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> listaUsuarios = usuaRepo.findAll();
        if(listaUsuarios.isEmpty()){
            return null;
        }
        return listaUsuarios;
    }

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param id identificador del usuario.
     * @return el objeto {@link Usuario} correspondiente, o {@code null} si no se encuentra.
     */
    @Override
    public Usuario findUsuario(Long id) {
        Usuario usua = usuaRepo.findById(id).orElse(null);
        if(usua == null){
            return null;
        }
        return usua;
    }

    /**
     * Elimina un usuario del sistema según su identificador.
     *
     * @param id identificador del usuario a eliminar.
     * @return {@code true} si el usuario fue eliminado correctamente, {@code false} si no existe.
     */
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

    /**
     * Actualiza los datos de un usuario existente.
     *
     * <p>Solo se modifican los campos que no estén vacíos o nulos, permitiendo
     * actualizaciones parciales.</p>
     *
     * @param usua objeto {@link Usuario} con los nuevos valores.
     * @param id identificador del usuario a actualizar.
     * @return el objeto {@link Usuario} actualizado, o {@code null} si el usuario no existe.
     */
    @Override
    public Usuario editUsuario(Usuario usua, Long id) {
        Usuario usuario = usuaRepo.findById(id).orElse(null);
        if(usuario == null){
            return null;
        }
        if(usua.getNombre() != null && !usua.getNombre().isBlank()){
        usuario.setNombre(usua.getNombre());
        }
        if(usua.getApellido() != null && !usua.getApellido().isBlank()){
        usuario.setApellido(usua.getApellido());
        }
         if(usua.getDocumento() != null && !usua.getDocumento().isBlank()){
        usuario.setDocumento(usua.getDocumento());
        }

        return usuaRepo.save(usuario);
    }


}
