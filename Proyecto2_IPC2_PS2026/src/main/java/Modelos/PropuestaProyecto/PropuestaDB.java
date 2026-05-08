/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.PropuestaProyecto;

/**
 *
 * @author millin-115
 */
public class PropuestaDB {
    
    private final int idPropuesta;
    private final String presentacion;
    private final double montoOfertado;
    private final int plazoEntrega;
    private final String fecha;
    private final String freelancer;
    private final int proyecto;
    private final int estado;
    private final String tipoEstado;
    private double calificacionPromedio;

    public PropuestaDB(int idPropuesta, String presentacion, double montoOfertado, int plazoEntrega, String fecha, String freelancer, int proyecto, int estado, String tipoEstado) {
        this.idPropuesta = idPropuesta;
        this.presentacion = presentacion;
        this.montoOfertado = montoOfertado;
        this.plazoEntrega = plazoEntrega;
        this.fecha = fecha;
        this.freelancer = freelancer;
        this.proyecto = proyecto;
        this.estado = estado;
        this.tipoEstado = tipoEstado;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public double getMontoOfertado() {
        return montoOfertado;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public String getFreelancer() {
        return freelancer;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getFecha() {
        return fecha;
    }

    public int getEstado() {
        return estado;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }
    
    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

}
