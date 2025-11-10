package com.proyecto.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.biblioteca.model.Usuario;

/**
 * Repositorio JPA para la entidad {@link Usuario}.
 *
 * <p>Extiende la interfaz {@link JpaRepository} de Spring Data JPA,
 * proporcionando operaciones CRUD y consultas personalizadas sobre la entidad {@code Usuario}.</p>
 *
 * <p>Esta interfaz actúa como la capa de acceso a datos (DAO) dentro del
 * sistema de gestión biblioteca, encargada de la persistencia y recuperación
 * de información de los usuarios registrados.</p>
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

}
