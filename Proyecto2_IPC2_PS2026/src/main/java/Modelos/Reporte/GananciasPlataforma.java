/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class GananciasPlataforma {
    
    private final double ganancias;
    private final int contratosCompletados;

    public GananciasPlataforma(double ganancias, int contratosCompletados) {
        this.ganancias = ganancias;
        this.contratosCompletados = contratosCompletados;
    }

    public double getGanancias() {
        return ganancias;
    }

    public int getContratosCompletados() {
        return contratosCompletados;
    }
    
}
