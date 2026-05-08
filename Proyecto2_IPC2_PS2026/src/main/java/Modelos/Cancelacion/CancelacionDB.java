/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Cancelacion;

/**
 *
 * @author millin-115
 */
public class CancelacionDB {
    
    private final int idCancelacion;
    private final String motivo;
    private final int proyecto;
    private final String freelancer;

    public CancelacionDB(int idCancelacion, String motivo, int proyecto, String freelancer) {
        this.idCancelacion = idCancelacion;
        this.motivo = motivo;
        this.proyecto = proyecto;
        this.freelancer = freelancer;
    }

    public int getIdCancelacion() {
        return idCancelacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getProyecto() {
        return proyecto;
    }

    public String getFreelancer() {
        return freelancer;
    }
    
}
