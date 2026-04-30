/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Historial;

/**
 *
 * @author millin-115
 */
public class HistorialRequest {
    
    private double montoProyecto;
    private int porcentajeComision;
    private String cliente;
    private String freelancer;

    public double getMontoProyecto() {
        return montoProyecto;
    }

    public void setMontoProyecto(double montoProyecto) {
        this.montoProyecto = montoProyecto;
    }

    public int getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(int porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(String freelancer) {
        this.freelancer = freelancer;
    }
    
}
