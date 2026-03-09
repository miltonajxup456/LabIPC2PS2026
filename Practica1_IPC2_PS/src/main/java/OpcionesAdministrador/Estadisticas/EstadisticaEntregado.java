/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Estadisticas;

/**
 *
 * @author millin-115
 */
public class EstadisticaEntregado {
    
    private final String nombre;
    private final int pedidosEntregados;
    private final int mejorPuntuacion;

    public EstadisticaEntregado(String nombre, int pedidosEntregados, int mejorPuntuacion) {
        this.nombre = nombre;
        this.pedidosEntregados = pedidosEntregados;
        this.mejorPuntuacion = mejorPuntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPedidosEntregados() {
        return pedidosEntregados;
    }

    public int getMejorPuntuacion() {
        return mejorPuntuacion;
    }
    
}
