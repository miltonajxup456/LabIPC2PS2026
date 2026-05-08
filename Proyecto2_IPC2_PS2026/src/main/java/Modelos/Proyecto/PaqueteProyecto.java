/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Proyecto;

import Modelos.Rechazo.RechazoDB;

/**
 *
 * @author millin-115
 */
public class PaqueteProyecto {
    
    private final ProyectoDB proyecto;
    private final RechazoDB[] rechazos;

    public PaqueteProyecto(ProyectoDB proyecto, RechazoDB[] rechazos) {
        this.proyecto = proyecto;
        this.rechazos = rechazos;
    }

    public ProyectoDB getProyecto() {
        return proyecto;
    }

    public RechazoDB[] getRechazos() {
        return rechazos;
    }
    
}
