package com.proyecto.biblioteca.service;

import java.util.List;
import com.proyecto.biblioteca.model.Usuario;

/**
 * Interfaz que define las operaciones del servicio relacionadas con la entidad {@link Usuario}.
 *
 * <p>Proporciona los métodos necesarios para gestionar el ciclo de vida de los usuarios,
 * incluyendo creación, consulta, actualización y eliminación de registros.</p>
 *
 * <p>Su implementación concreta delega la persistencia en el repositorio correspondiente
 * y aplica la lógica de negocio necesaria antes de interactuar con la base de datos.</p>
 */
public interface IUsuarioService {

    /**
     * Crea un nuevo registro de usuario.
     *
     * <p>Valida los datos del objeto recibido antes de persistirlo.</p>
     *
     * @param usua objeto {@link Usuario} que contiene los datos a guardar.
     */
    public void saveUsuarios(Usuario usua);

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return una lista de objetos {@link Usuario}.
     */
    public List<Usuario> getUsuarios();

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id identificador del usuario a buscar.
     */
    public Usuario findUsuario(Long id);

    /**
     * Elimina el registro de un usuario existente.
     *
     * @param id identificador único del usuario a eliminar.
     */
    public boolean deleteUsuario(Long id);

    /**
     * Actualiza parcialmente los datos de un usuario existente.
     *
     * <p>Solo se modifican los campos no nulos o no vacíos del objeto {@link Usuario}.
     * Si el usuario no existe, la operación no realiza cambios.</p>
     *
     * @param id identificador único del usuario a actualizar.
     * @param usua objeto {@link Usuario} con los campos a modificar.
     */
    public Usuario editUsuario(Usuario usua, Long id);

}
