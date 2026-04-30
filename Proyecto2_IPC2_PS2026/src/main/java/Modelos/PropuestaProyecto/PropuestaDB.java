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
    private final int freelancer;
    private final int proyecto;
    private final double calificacionPromedio;

    public PropuestaDB(int idPropuesta, String presentacion, double montoOfertado, int plazoEntrega, int freelancer, int proyecto, double calificacionPromedio) {
        this.idPropuesta = idPropuesta;
        this.presentacion = presentacion;
        this.montoOfertado = montoOfertado;
        this.plazoEntrega = plazoEntrega;
        this.freelancer = freelancer;
        this.proyecto = proyecto;
        this.calificacionPromedio = calificacionPromedio;
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

    public int getFreelancer() {
        return freelancer;
    }

    public int getProyecto() {
        return proyecto;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

}
