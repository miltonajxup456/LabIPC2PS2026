/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Entrega;

/**
 *
 * @author millin-115
 */
public class EntregaLinkDB {
    
    private final int idEntrega;
    private final String descripcion;
    private final String link;
    private final int proyecto;
    private final String freelancer;

    public EntregaLinkDB(int idEntrega, String descripcion, String link, int proyecto, String freelancer) {
        this.idEntrega = idEntrega;
        this.descripcion = descripcion;
        this.link = link;
        this.proyecto = proyecto;
        this.freelancer = freelancer;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getFreelancer() {
        return freelancer;
    }

}
