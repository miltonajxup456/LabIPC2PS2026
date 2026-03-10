/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Estadisticas;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaEstadistica;
import OpcionesSucursalProducto.Frontent.VentanaEstadisticas;
import SQL.Partida.GestorEstadisticas;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ColocarEstadisticasRanking {
    
    private final VentanaEstadisticas ventanaRanking;
    private final GestorEstadisticas gestorEstadisticas;
    private ListaGenerica<EstadisticaRanking> estadisticas;
    
    public ColocarEstadisticasRanking(VentanaEstadisticas ventanaRanking) {
        this.ventanaRanking = ventanaRanking;
        this.gestorEstadisticas = new GestorEstadisticas();
    }

    public ListaGenerica<EstadisticaRanking> getEstadisticas() {
        return estadisticas;
    }
    
    public void colocarEstadisticasPorSucursal(Usuario usuario) throws SQLException, ClassNotFoundException, ListaException {
        setTitulo();
        estadisticas = gestorEstadisticas.getEstadisticasPorSucursal(usuario.getIdSucursal());
        for (int i = 0; i < estadisticas.getTamaño(); i++) {
            EstadisticaRanking estadistica = estadisticas.obtenerConIndice(i);
            agregarAVentana(estadistica);
        }
    }
    
    public void colocarEstadisticasGlobales() throws SQLException, ClassNotFoundException, ListaException {
        setTitulo();
        estadisticas = gestorEstadisticas.getEstadisticasGlobales();
        for (int i = 0; i < estadisticas.getTamaño(); i++) {
            EstadisticaRanking estadistica = estadisticas.obtenerConIndice(i);
            agregarAVentana(estadistica);
        }
    }
    
    private void setTitulo() {
        ventanaRanking.setTextos("Nombre", "Sucursal", "Puntuacion", "Nivel");
    }
    
    private void agregarAVentana(EstadisticaRanking estadistica) {
        PlantillaEstadistica plantilla = new PlantillaEstadistica(estadistica.getNombre(), estadistica.getSucursal(), 
                String.valueOf(estadistica.getPuntuacion()), String.valueOf(estadistica.getNivel()));
        ventanaRanking.agregarEstadistica(plantilla);
    }
    
}
