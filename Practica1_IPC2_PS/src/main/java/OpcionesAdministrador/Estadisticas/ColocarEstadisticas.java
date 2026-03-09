/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Estadisticas;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaEstadistica;
import OpcionesSucursalProducto.Frontent.VentanaRanking;
import SQL.Partida.GestorEstadisticas;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ColocarEstadisticas {
    
    private final VentanaRanking ventanaRanking;
    private final GestorEstadisticas gestorEstadisticas;
    private ListaGenerica<Estadistica> estadisticas;
    
    public ColocarEstadisticas(VentanaRanking ventanaRanking) {
        this.ventanaRanking = ventanaRanking;
        this.gestorEstadisticas = new GestorEstadisticas();
    }

    public ListaGenerica<Estadistica> getEstadisticas() {
        return estadisticas;
    }
    
    public void colocarEstadisticasPorSucursal(Usuario usuario) throws SQLException, ClassNotFoundException, ListaException {
        estadisticas = gestorEstadisticas.getEstadisticasPorSucursal(usuario.getIdSucursal());
        for (int i = 0; i < estadisticas.getTamaño(); i++) {
            Estadistica estadistica = estadisticas.obtenerConIndice(i);
            PlantillaEstadistica plantilla = new PlantillaEstadistica(estadistica.getNombre(), estadistica.getSucursal(), 
                    estadistica.getPuntuacion(), estadistica.getNivel());
            ventanaRanking.agregarEstadistica(plantilla);
        }
    }
    
    public void colocarEstadisticasGlobales() throws SQLException, ClassNotFoundException, ListaException {
        estadisticas = gestorEstadisticas.getEstadisticasGlobales();
        for (int i = 0; i < estadisticas.getTamaño(); i++) {
            Estadistica estadistica = estadisticas.obtenerConIndice(i);
            PlantillaEstadistica plantilla = new PlantillaEstadistica(estadistica.getNombre(), estadistica.getSucursal(), 
                    estadistica.getPuntuacion(), estadistica.getNivel());
            ventanaRanking.agregarEstadistica(plantilla);
        }
    }
    
}
