/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

import SQL.Partida.GestorEstadisticaEntregados;
import Excepciones.ListaException;
import Listas.ListaGenerica;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author millin-115
 */
public class GuardadoHistorialUsuario implements GuardadoArchivos {
    
    private final GestorEstadisticaEntregados estadisticas;
    private final String carpeta;
    private String archivo;
    
    public GuardadoHistorialUsuario(GestorEstadisticaEntregados estadisticas) {
        this.estadisticas = estadisticas;
        this.carpeta = "ArchivosCSV";
        this.archivo = carpeta+"/HistorialUsuario";
        File file = new File(carpeta);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    @Override
    public void guardarArchivos() throws ListaException, IOException {
        
    }
    
}
