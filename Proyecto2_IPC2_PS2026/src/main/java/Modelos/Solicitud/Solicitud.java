/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Solicitud;

/**
 *
 * @author millin-115
 */
public class Solicitud {
    
    private String nombre;
    private String descripcion;
    private boolean aprovado;

    public Solicitud(String nombre, String descripcion, boolean aprovado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.aprovado = aprovado;
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

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }
}
