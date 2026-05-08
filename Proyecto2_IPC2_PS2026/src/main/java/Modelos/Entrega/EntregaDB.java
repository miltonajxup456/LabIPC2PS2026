/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Entrega;

/**
 *
 * @author millin-115
 */
public class EntregaDB {
    
    private final int idEntrega;
    private final String descripcion;
    private final String pathArchivo;
    private final int proyecto;
    private final String freelancer;

    public EntregaDB(int idEntrega, String descripcion, String pathArchivo, int proyecto, String freelancer) {
        this.idEntrega = idEntrega;
        this.descripcion = descripcion;
        this.pathArchivo = pathArchivo;
        this.proyecto = proyecto;
        this.freelancer = freelancer;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPathArchivo() {
        return pathArchivo;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getFreelancer() {
        return freelancer;
    }
    
}
