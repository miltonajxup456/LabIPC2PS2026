/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Recarga;

/**
 *
 * @author millin-115
 */
public class RecargaDB {
    
    private final int idRecarga;
    private final double monto;
    private final String fecha;
    private final String cliente;

    public RecargaDB(int idRecarga, double monto, String fecha, String cliente) {
        this.idRecarga = idRecarga;
        this.monto = monto;
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public int getIdRecarga() {
        return idRecarga;
    }

    public double getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

}
