/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Comision;

/**
 *
 * @author millin-115
 */
public class ComisionDB {
    
    private final int idComision;
    private final int porcentaje;
    private final String fechaInicio;
    private final String fechaFinal;

    public ComisionDB(int idComision, int porcentaje, String fechaInicio, String fechaFinal) {
        this.idComision = idComision;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public int getIdComision() {
        return idComision;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

}
