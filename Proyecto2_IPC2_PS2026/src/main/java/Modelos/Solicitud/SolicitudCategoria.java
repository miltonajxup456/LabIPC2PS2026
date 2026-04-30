/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Solicitud;

/**
 *
 * @author millin-115
 */
public class SolicitudCategoria extends Solicitud {
    
    private final int idSolicitudCategoria;

    public SolicitudCategoria(int idSolicitudCategoria, String nombre, String descripcion, boolean aprovado) {
        super(nombre, descripcion, aprovado);
        this.idSolicitudCategoria = idSolicitudCategoria;
    }

    public int getIdSolicitudCategoria() {
        return idSolicitudCategoria;
    }
    
}
