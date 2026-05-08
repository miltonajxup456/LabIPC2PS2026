/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Propuestas;

/**
 *
 * @author millin-115
 */
public class PropuestaCategoriaDB extends Propuesta {
    
    private final int idPropuestaCategoria;

    public PropuestaCategoriaDB(int idSolicitudCategoria, String nombre, String descripcion, boolean aprovado) {
        super(nombre, descripcion, aprovado);
        this.idPropuestaCategoria = idSolicitudCategoria;
    }

    public int getIdPropuestaCategoria() {
        return idPropuestaCategoria;
    }
    
}
