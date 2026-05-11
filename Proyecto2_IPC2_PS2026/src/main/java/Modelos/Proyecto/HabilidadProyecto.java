/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Proyecto;

/**
 *
 * @author millin-115
 */
public class HabilidadProyecto {
    
    private final int idHabilidadProyecto;
    private final int habilidad;
    private final int proyecto;

    public HabilidadProyecto(int idHabilidadProyecto, int habilidad, int proyecto) {
        this.idHabilidadProyecto = idHabilidadProyecto;
        this.habilidad = habilidad;
        this.proyecto = proyecto;
    }

    public int getIdHabilidadProyecto() {
        return idHabilidadProyecto;
    }

    public int getHabilidad() {
        return habilidad;
    }

    public int getProyecto() {
        return proyecto;
    }
    
}
