/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Propuestas;

/**
 *
 * @author millin-115
 */
public class Propuesta {
    
    private String nombre;
    private String descripcion;
    private boolean aprobado;

    public Propuesta(String nombre, String descripcion, boolean aprovado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.aprobado = aprovado;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
}
