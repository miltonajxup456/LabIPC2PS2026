/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Archivos;

import Excepciones.ListaException;
import java.io.IOException;

/**
 *
 * @author millin-115
 */
public interface GuardadoArchivos {
    
    void guardarArchivos() throws ListaException, IOException;
    
}
