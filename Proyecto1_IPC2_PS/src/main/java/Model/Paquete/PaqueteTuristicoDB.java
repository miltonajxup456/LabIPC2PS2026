/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Paquete;

/**
 *
 * @author millin-115
 */
public class PaqueteTuristicoDB {
    
    private final int idPaquete;
    private final String nombre;
    private final int duracionDias;
    private final double precio;
    private final int capacidad;
    private final String descripcion;
    private final boolean estadoPaquete;
    private final int idDestino;
    private String destino;
    private int reservacionesHechas;

    public PaqueteTuristicoDB(int idPaquete, String nombre, int duracionDias, double precio, int capacidad, String descripcion, boolean estadoPaquete, 
            int idDestino, String destino) {
        this.idPaquete = idPaquete;
        this.nombre = nombre;
        this.duracionDias = duracionDias;
        this.precio = precio;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
        this.estadoPaquete = estadoPaquete;
        this.idDestino = idDestino;
        this.destino = destino;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean getEstadoPaquete() {
        return estadoPaquete;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public String getDestino() {
        return destino;
    }
    
    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getReservacionesHechas() {
        return reservacionesHechas;
    }

    public void setReservacionesHechas(int reservacionesHechas) {
        this.reservacionesHechas = reservacionesHechas;
    }
    
    
}
