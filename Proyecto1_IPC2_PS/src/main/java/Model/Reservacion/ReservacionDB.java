/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Reservacion;

import java.util.List;

/**
 *
 * @author millin-115
 */
public class ReservacionDB {
    
    private final int numReservacion;
    private final String fechaViaje;
    private final String fechaCreacion;
    private final int cantPasajeros;
    private final double costo;
    private final int agenteDeRegistro;
    private final int paquete;
    private final int estado;
    private double dineroCancelado;
    private final String nombrePaquete;
    private String nombreCliente;
    private List<String> dpiPasajeros;
    
    public ReservacionDB(String nombreRegistrador, int numeroReservacion, String fechaReservacion, String fechaCreacion, int cantPasajeros, double costo, int agenteDeRegistro, 
            int paquete, int estado, String nombrePaquete) {
        this.numReservacion = numeroReservacion;
        this.fechaViaje = fechaReservacion;
        this.fechaCreacion = fechaCreacion;
        this.cantPasajeros = cantPasajeros;
        this.costo = costo;
        this.agenteDeRegistro = agenteDeRegistro;
        this.paquete = paquete;
        this.estado = estado;
        this.nombrePaquete = nombrePaquete;
    }

    public ReservacionDB(int numeroReservacion, String fechaReservacion, String fechaCreacion, int cantPasajeros, double costo, int agenteDeRegistro, 
            int paquete, int estado, double dineroCancelado, String nombrePaquete) {
        this.numReservacion = numeroReservacion;
        this.fechaViaje = fechaReservacion;
        this.fechaCreacion = fechaCreacion;
        this.cantPasajeros = cantPasajeros;
        this.costo = costo;
        this.agenteDeRegistro = agenteDeRegistro;
        this.paquete = paquete;
        this.estado = estado;
        this.dineroCancelado = dineroCancelado;
        this.nombrePaquete = nombrePaquete;
    }

    public ReservacionDB(int numeroReservacion, String fechaReservacion, String fechaCreacion, int cantPasajeros, double costo, int agenteDeRegistro, 
            int paquete, int estado, String nombrePaquete, String nombreCliente) {
        this.numReservacion = numeroReservacion;
        this.fechaViaje = fechaReservacion;
        this.fechaCreacion = fechaCreacion;
        this.cantPasajeros = cantPasajeros;
        this.costo = costo;
        this.agenteDeRegistro = agenteDeRegistro;
        this.paquete = paquete;
        this.estado = estado;
        this.nombrePaquete = nombrePaquete;
        this.nombreCliente = nombreCliente;
    }

    public int getNumReservacion() {
        return numReservacion;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public int getCantPasajeros() {
        return cantPasajeros;
    }

    public double getCosto() {
        return costo;
    }

    public int getAgenteDeRegistro() {
        return agenteDeRegistro;
    }

    public int getPaquete() {
        return paquete;
    }

    public int getEstado() {
        return estado;
    }

    public double getDineroCancelado() {
        return dineroCancelado;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public List<String> getDpiPasajeros() {
        return dpiPasajeros;
    }

    public void setDpiPasajeros(List<String> dpiPasajeros) {
        this.dpiPasajeros = dpiPasajeros;
    }

}
