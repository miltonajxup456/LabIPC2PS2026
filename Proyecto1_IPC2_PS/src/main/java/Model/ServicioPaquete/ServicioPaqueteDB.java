/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.ServicioPaquete;

/**
 *
 * @author millin-115
 */
public class ServicioPaqueteDB {
    
    private final int idServicioPaquete;
    private final String descripcion;
    private final double costo;
    private final int paquete;
    private final int proveedor;
    private final String nombreServicio;

    public ServicioPaqueteDB(int idServicioPaquete, String descripcion, double costo, int paquete, int proveedor, String nombreServicio) {
        this.idServicioPaquete = idServicioPaquete;
        this.descripcion = descripcion;
        this.costo = costo;
        this.paquete = paquete;
        this.proveedor = proveedor;
        this.nombreServicio = nombreServicio;
    }

    public int getIdServicioPaquete() {
        return idServicioPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public int getPaquete() {
        return paquete;
    }

    public int getProveedor() {
        return proveedor;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }
    
    
    
}
