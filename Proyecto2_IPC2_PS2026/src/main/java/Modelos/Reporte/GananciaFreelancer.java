/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class GananciaFreelancer {
    
    private final String freelancer;
    private final double comisionTotal;

    public GananciaFreelancer(String freelancer, double comisionTotal) {
        this.freelancer = freelancer;
        this.comisionTotal = comisionTotal;
    }

    public String getFreelancer() {
        return freelancer;
    }

    public double getComisionTotal() {
        return comisionTotal;
    }
    
    
}
