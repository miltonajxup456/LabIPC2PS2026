/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesAdministrador.Estadisticas.ColocarEstadisticasHistorial;
import OpcionesAdministrador.Estadisticas.EstadisticaHistorial;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author millin-115
 */
public class GuardadoHistorialUsuario implements GuardadoArchivos {
    
    private final ColocarEstadisticasHistorial colocar;
    private final String carpeta;
    private String archivo;
    
    public GuardadoHistorialUsuario(ColocarEstadisticasHistorial colocar) {
        this.colocar = colocar;
        this.carpeta = "ArchivosCSV";
        File file = new File(carpeta);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    @Override
    public void guardarArchivos() throws ListaException, IOException {
        ListaGenerica<EstadisticaHistorial> estadPedidos = colocar.getHistorial();
        EstadisticaHistorial estadistica;
        
        if (estadPedidos.getTamaño() > 1) {
            archivo = carpeta+"/Historial Usuarios.csv";
        } else {
            estadistica = estadPedidos.obtenerConIndice(0);
            archivo = carpeta+"/Historial Usuario "+estadistica.getNombre()+".csv";
        }
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(archivo))) {
            String titulo = "Nombre,Pedidos_Entregados,Mejor_Puntuacion,Puntos_Acumulados";
            printWriter.println(titulo);
            for (int i = 0; i < estadPedidos.getTamaño(); i++) {
                estadistica = estadPedidos.obtenerConIndice(i);
                printWriter.println(estadistica.getNombre()+","+estadistica.getPedidosEntregados()+","+
                        estadistica.getMejorPuntuacion()+","+estadistica.getPuntosAcumulados());
            }
        }
        
    }
    
}
