/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class CategoriaTrabajada {
    
    private final String nombreCategoria;
    private final int cantidadContratos;
    private final double totalIngresos;

    public CategoriaTrabajada(String nombreCategoria, int cantidadContratos, double totalIngresos) {
        this.nombreCategoria = nombreCategoria;
        this.cantidadContratos = cantidadContratos;
        this.totalIngresos = totalIngresos;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getCantidadContratos() {
        return cantidadContratos;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }
    
}
