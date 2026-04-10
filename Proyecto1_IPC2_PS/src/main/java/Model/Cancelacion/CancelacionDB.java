/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Cancelacion;

/**
 *
 * @author millin-115
 */
public class CancelacionDB {
    
    private final int numeroReservacion;
    private final String fechaViaje;
    private final double costo;
    private final String nombrePaquete;
    private final double dineroPagado;
    private String fechaCancelacion;
    private int porcentajeReembolso;

    public CancelacionDB(int numeroReservacion, String fechaViaje, double costo, String nombrePaquete, double dineroPagado) {
        this.numeroReservacion = numeroReservacion;
        this.fechaViaje = fechaViaje;
        this.costo = costo;
        this.nombrePaquete = nombrePaquete;
        this.dineroPagado = dineroPagado;
    }

    public int getNumeroReservacion() {
        return numeroReservacion;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public double getCosto() {
        return costo;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public double getDineroPagado() {
        return dineroPagado;
    }

    public String getFechaCancelacion() {
        return fechaCancelacion;
    }

    public int getPorcentajeReembolso() {
        return porcentajeReembolso;
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public void setPorcentajeReembolso(int porcentajeReembolso) {
        this.porcentajeReembolso = porcentajeReembolso;
    }
    

}
