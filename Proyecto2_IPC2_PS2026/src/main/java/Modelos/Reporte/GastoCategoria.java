/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class GastoCategoria {
    
    private final String nombreCategoria;
    private final double total;

    public GastoCategoria(String nombreCategoria, double total) {
        this.nombreCategoria = nombreCategoria;
        this.total = total;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public double getTotal() {
        return total;
    }
    
}
