package com.proyecto.biblioteca.service;

import java.util.List;
import com.proyecto.biblioteca.dto.PrestamoDTO;
import com.proyecto.biblioteca.dto.PrestamoInfoDTO;

public interface IPrestamoService {

    //Creación de Prestamos
    public void savePrestamos(PrestamoDTO prestDTO);

    //Traer todas los Prestamos
    public List<PrestamoInfoDTO> getPrestamos();

    // Traer una sola Prestamo
    public PrestamoInfoDTO findPrestamo(Long id);


    // Eliminar una solo Prestamo
    public void deletePrestamo(Long id);

    // Modificar Prestamo 
    public PrestamoInfoDTO editPrestamo(Long id, PrestamoInfoDTO PrestInfoDTO);

}
