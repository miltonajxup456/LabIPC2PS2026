/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Estadisticas;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaEstadistica;
import OpcionesSucursalProducto.Frontent.VentanaRanking;
import SQL.Partida.GestorEstadisticaEntregados;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ColocarEstadisticasPedidos {
    
    private final VentanaRanking ventanaRanking;
    private final GestorEstadisticaEntregados gestor;
    private ListaGenerica<EstadisticaEntregado> entregados;

    public ColocarEstadisticasPedidos(VentanaRanking ventanaRanking, GestorEstadisticaEntregados gestor) {
        this.ventanaRanking = ventanaRanking;
        this.gestor = gestor;
    }

    public ListaGenerica<EstadisticaEntregado> getEntregados() {
        return entregados;
    }
    
    public void colocarEstadisticaUsuario(Usuario usuario) throws SQLException, ClassNotFoundException, ListaException {
        ventanaRanking.setTextos("Nombre", "Pedidos Entregados", "Puntaje Mas Alto", "");
        entregados = gestor.estadisticasPedidosUsuario(usuario);
        EstadisticaEntregado estadistica = entregados.obtenerConIndice(0);
        PlantillaEstadistica plantilla = new PlantillaEstadistica(estadistica.getNombre(), String.valueOf(estadistica.getPedidosEntregados()), 
                estadistica.getMejorPuntuacion(), 0);
        ventanaRanking.agregarEstadistica(plantilla);
    }
    
    
    
}
