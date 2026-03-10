/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Estadisticas;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaEstadistica;
import OpcionesSucursalProducto.Frontent.VentanaEstadisticas;
import SQL.Partida.GestorEstadisticasHistorial;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ColocarEstadisticasHistorial {
    
    private final VentanaEstadisticas ventanaEstadisticas;
    private final GestorEstadisticasHistorial gestor;
    private ListaGenerica<EstadisticaHistorial> historial;

    public ColocarEstadisticasHistorial(VentanaEstadisticas ventanaRanking) {
        this.ventanaEstadisticas = ventanaRanking;
        this.gestor = new GestorEstadisticasHistorial();
    }

    public ListaGenerica<EstadisticaHistorial> getHistorial() {
        return historial;
    }
    
    public void colocarEstadisticaUsuario(Usuario usuario) throws SQLException, ClassNotFoundException, ListaException {
        setTitulo();
        historial = gestor.estadisticasPedidosUsuario(usuario);
        EstadisticaHistorial estadistica = historial.obtenerConIndice(0);
        agregarAVentana(estadistica);
    }
    
    public void colocarEstadisticasSucursal(Usuario usuario) throws SQLException, ClassNotFoundException, ListaException {
        setTitulo();
        historial = gestor.estadisticaPedidosPorSucursal(usuario);
        for (int i = 0; i < historial.getTamaño(); i++) {
            EstadisticaHistorial estadistica = historial.obtenerConIndice(i);
            agregarAVentana(estadistica);
        }
    }
    
    public void colocarEstadisticaGlobal()  throws SQLException, ClassNotFoundException, ListaException {
        setTitulo();
        historial = gestor.estadisticasPedidosGlobales();
        for (int i = 0; i < historial.getTamaño(); i++) {
            EstadisticaHistorial estadistica = historial.obtenerConIndice(i);
            agregarAVentana(estadistica);
        }
    }
    
    private void setTitulo() {
        ventanaEstadisticas.setTextos("Nombre", "Pedidos Entregados", "Puntaje Mas Alto", "Puntos Acumulados");
    }
    private void agregarAVentana(EstadisticaHistorial estadistica) {
        PlantillaEstadistica plantilla = new PlantillaEstadistica(estadistica.getNombre(), String.valueOf(estadistica.getPedidosEntregados()), 
                String.valueOf(estadistica.getMejorPuntuacion()), String.valueOf(estadistica.getPuntosAcumulados()));
        ventanaEstadisticas.agregarEstadistica(plantilla);
    }
    
}
