/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Pago;

/**
 *
 * @author millin-115
 */
public class PagoDB {
    
    private final int idPago;
    private final double montoPagado;
    private final String fecha;
    private final int numReservacion;
    private final int metodo;
    private final String metodoPago;

    public PagoDB(int idPago, double montoPagado, String fecha, int numReservacion, int metodo, String metodoPago) {
        this.idPago = idPago;
        this.montoPagado = montoPagado;
        this.fecha = fecha;
        this.numReservacion = numReservacion;
        this.metodo = metodo;
        this.metodoPago = metodoPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public String getFecha() {
        return fecha;
    }

    public int getNumReservacion() {
        return numReservacion;
    }

    public int getMetodo() {
        return metodo;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

}
