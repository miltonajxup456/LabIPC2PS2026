/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.OcupacionDestino;

/**
 *
 * @author millin-115
 */
public class OcupacionDestinoDB {
    
    private final int idDestino;
    private final String nombreDestino;
    private final int ocupacion;

    public OcupacionDestinoDB(int idDestino, String nombreDestino, int ocupacion) {
        this.idDestino = idDestino;
        this.nombreDestino = nombreDestino;
        this.ocupacion = ocupacion;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public int getOcupacion() {
        return ocupacion;
    }
    
}
