/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Proyecto;

/**
 *
 * @author millin-115
 */
public class ProyectoDB {
    
    private final int idProyecto;
    private final String titulo;
    private final String descripcion;
    private final double presupuesto;
    private final String fechaLimite;
    private final String cliente;
    private final int categoria;
    private final String tipoCategoria;
    private final int estado;
    private final String tipoEstado;

    public ProyectoDB(int idProyecto, String titulo, String descripcion, double presupuesto, String fecha_limite, String cliente, 
            int categoria, String tipoCategoria, int estado, String tipoEstado) {
        this.idProyecto = idProyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupuesto = presupuesto;
        this.fechaLimite = fecha_limite;
        this.cliente = cliente;
        this.categoria = categoria;
        this.tipoCategoria = tipoCategoria;
        this.estado = estado;
        this.tipoEstado = tipoEstado;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public String getCliente() {
        return cliente;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public int getEstado() {
        return estado;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

}
