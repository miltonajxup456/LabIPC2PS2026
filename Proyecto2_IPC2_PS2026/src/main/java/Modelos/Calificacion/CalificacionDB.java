/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Calificacion;

/**
 *
 * @author millin-115
 */
public class CalificacionDB {
    
    private final int idCalificacion;
    private final int calificacion;
    private final String comentario;
    private final int proyecto;
    private final String freelancer;

    public CalificacionDB(int idCalificacion, int calificacion, String comentario, int proyecto, String freelancer) {
        this.idCalificacion = idCalificacion;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.proyecto = proyecto;
        this.freelancer = freelancer;
    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getFreelancer() {
        return freelancer;
    }
    
}
