/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Habilidad;

/**
 *
 * @author millin-115
 */
public class HabilidadDB {
    
    private final int idHabilidad;
    private final String nombreHabilidad;
    private final String descripcion;

    public HabilidadDB(int idHabilidad, String nombreHabilidad, String descripcion) {
        this.idHabilidad = idHabilidad;
        this.nombreHabilidad = nombreHabilidad;
        this.descripcion = descripcion;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public String getNombreHabilidad() {
        return nombreHabilidad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
}
