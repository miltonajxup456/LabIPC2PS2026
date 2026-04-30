/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Solicitud;

/**
 *
 * @author millin-115
 */
public class SolicitudHabilidad extends Solicitud {
    
    private final int idSolicitudHabilidad;

    public SolicitudHabilidad(int idSolicitudCategoria, String nombre, String descripcion, boolean aprovado) {
        super(nombre, descripcion, aprovado);
        this.idSolicitudHabilidad = idSolicitudCategoria;
    }

    public int getIdSolicitudHabilidad() {
        return idSolicitudHabilidad;
    }
    
}
