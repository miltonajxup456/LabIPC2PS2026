/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class ContratoCompletado {
    
    private final String cliente;
    private final int proyecto;
    private final String titulo;
    private final double montoRecibido;
    private final int calificacionObtenida;

    public ContratoCompletado(String cliente, int proyecto, String titulo, double montoRecibido, int calificacionObtenida) {
        this.cliente = cliente;
        this.proyecto = proyecto;
        this.titulo = titulo;
        this.montoRecibido = montoRecibido;
        this.calificacionObtenida = calificacionObtenida;
    }

    public String getCliente() {
        return cliente;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getMontoRecibido() {
        return montoRecibido;
    }

    public int getCalificacionObtenida() {
        return calificacionObtenida;
    }
    
}
