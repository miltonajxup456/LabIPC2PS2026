/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Rechazo;

/**
 *
 * @author millin-115
 */
public class RechazoDB {
    
    private final int idRechazo;
    private final String respuesta;
    private final String fecha;
    private final int proyecto;
    private final String freelancer;

    public RechazoDB(int idRechazo, String respuesta, String fecha, int proyecto, String freelancer) {
        this.idRechazo = idRechazo;
        this.respuesta = respuesta;
        this.fecha = fecha;
        this.proyecto = proyecto;
        this.freelancer = freelancer;
    }

    public int getIdRechazo() {
        return idRechazo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getFecha() {
        return fecha;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getFreelancer() {
        return freelancer;
    }

}
