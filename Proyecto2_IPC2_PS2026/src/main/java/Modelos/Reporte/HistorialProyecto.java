/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class HistorialProyecto {
    
    private final int estado;
    private final String tipoEstado;
    private final double presupuesto;
    private final String freelancer;

    public HistorialProyecto(int estado, String tipoEstado, double presupuesto, String freelancer) {
        this.estado = estado;
        this.tipoEstado = tipoEstado;
        this.presupuesto = presupuesto;
        this.freelancer = freelancer;
    }

    public int getEstado() {
        return estado;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public String getFreelancer() {
        return freelancer;
    }
    
}
