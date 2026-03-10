/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesAdministrador.Estadisticas.ColocarEstadisticasRanking;
import OpcionesAdministrador.Estadisticas.EstadisticaRanking;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author millin-115
 */
public class GuardadoRanking implements GuardadoArchivos {
    
    private final ColocarEstadisticasRanking colocarEstadisticas;
    private final String carpeta;
    private final String archivo;
    
    public GuardadoRanking(ColocarEstadisticasRanking colocarEstadisticas) {
        this.colocarEstadisticas = colocarEstadisticas;
        this.carpeta = "ArchivosCSV";
        this.archivo = carpeta+"/Ranking.csv";
        
        File file = new File(carpeta);
        if (!file.exists()) {
            file.mkdir();
        }
    }
    
    @Override
    public void guardarArchivos() throws ListaException, IOException {
        ListaGenerica<EstadisticaRanking> estadisticas = colocarEstadisticas.getEstadisticas();
        
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(archivo))) {
            String titulo = "Nombre_Usuario,Sucursal_Usuario,Puntuacion,Nivel";
            printWriter.println(titulo);
            for (int i = 0; i < estadisticas.getTamaño(); i++) {
                EstadisticaRanking estadistica = estadisticas.obtenerConIndice(i);
                String estadisticaCSV = estadistica.getNombre()+","+estadistica.getSucursal()+","+estadistica.getPuntuacion()+","+estadistica.getNivel();
                printWriter.println(estadisticaCSV);
            }
        }
    }
    
}
