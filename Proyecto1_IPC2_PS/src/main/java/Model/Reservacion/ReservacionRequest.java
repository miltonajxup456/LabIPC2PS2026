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
public class ReservacionRequest {
    
    private int numReservacion;
    private String fechaViaje;
    private int cantPasajeros;
    private double costo;
    private int agenteDeRegistro;
    private int paquete;
    private int estado;
    private String nombrePaquete;
    private List<String> dpiPasajeros;

    public int getNumReservacion() {
        return numReservacion;
    }

    public void setNumReservacion(int numReservacion) {
        this.numReservacion = numReservacion;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public int getCantPasajeros() {
        return cantPasajeros;
    }

    public void setCantPasajeros(int cantPasajeros) {
        this.cantPasajeros = cantPasajeros;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getAgenteDeRegistro() {
        return agenteDeRegistro;
    }

    public void setAgenteDeRegistro(int agenteDeRegistro) {
        this.agenteDeRegistro = agenteDeRegistro;
    }

    public int getPaquete() {
        return paquete;
    }

    public void setPaquete(int paquete) {
        this.paquete = paquete;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<String> getDpiPasajeros() {
        return dpiPasajeros;
    }

    public void setDpiPasajeros(List<String> dpiPasajeros) {
        this.dpiPasajeros = dpiPasajeros;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }
    
}
