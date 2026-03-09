/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Estadisticas;

/**
 *
 * @author millin-115
 */
public class Estadistica {
    
    private String nombre;
    private String sucursal;
    private int puntuacion;
    private int nivel;

    public Estadistica(String nombre, String sucursal, int puntuacion, int nivel) {
        this.nombre = nombre;
        this.sucursal = sucursal;
        this.puntuacion = puntuacion;
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSucursal() {
        return sucursal;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getNivel() {
        return nivel;
    }
    
}
