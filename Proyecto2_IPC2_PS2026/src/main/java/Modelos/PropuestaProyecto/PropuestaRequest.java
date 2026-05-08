/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.PropuestaProyecto;

/**
 *
 * @author millin-115
 */
public class PropuestaRequest {
    
    private String presentacion;
    private double montoOfertado;
    private int plazoEntrega;
    private String fecha;
    private String freelancer;
    private int proyecto;
    private int estado;

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public double getMontoOfertado() {
        return montoOfertado;
    }

    public void setMontoOfertado(double montoOfertado) {
        this.montoOfertado = montoOfertado;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setPlazoEntrega(int plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }
    
    public String getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(String freelancer) {
        this.freelancer = freelancer;
    }

    public int getProyecto() {
        return proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
