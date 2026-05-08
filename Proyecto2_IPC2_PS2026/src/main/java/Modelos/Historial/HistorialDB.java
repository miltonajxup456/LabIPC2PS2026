/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Historial;

/**
 *
 * @author millin-115
 */
public class HistorialDB {
    
    private final int idHistorial;
    private final double montoProyecto;
    private final int porcentajeComision;
    private final String fecha;
    private final String cliente;
    private final String freelancer;
    private final int categoria;

    public HistorialDB(int idHistorial, double montoProyecto, int porcentajeComision, String fecha, String cliente, String freelancer, int categoria) {
        this.idHistorial = idHistorial;
        this.montoProyecto = montoProyecto;
        this.porcentajeComision = porcentajeComision;
        this.fecha = fecha;
        this.cliente = cliente;
        this.freelancer = freelancer;
        this.categoria = categoria;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public double getMontoProyecto() {
        return montoProyecto;
    }

    public int getPorcentajeComision() {
        return porcentajeComision;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getFreelancer() {
        return freelancer;
    }
    
    public int getCategoria() {
        return categoria;
    }

}
