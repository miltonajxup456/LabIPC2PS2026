/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author millin-115
 */
public class LectorArchivosDAO {
    
    public RegistroDeErrores leerArchivoTexto(InputStream archivo) {
        
        CreadorDesdeArchivoDAO creador = new CreadorDesdeArchivoDAO();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivo))) {
            String linea = reader.readLine();
            while (linea != null) {
                creador.filtrarLinea(linea);
                linea = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return creador.getRegistroErrores();
    }
    
}
