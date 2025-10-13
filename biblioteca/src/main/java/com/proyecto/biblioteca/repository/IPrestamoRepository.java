package com.proyecto.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.biblioteca.model.Prestamo;

@Repository
public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {

}
