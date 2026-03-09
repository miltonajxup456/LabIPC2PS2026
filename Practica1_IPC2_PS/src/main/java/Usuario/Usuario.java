/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

/**
 *
 * @author millin-115
 */
public class Usuario {
    
    private final int idUsuario;
    private final String nombre;
    private final int idRol;
    private final int idSucursal;
    private String nombreSucursal;
    
    public Usuario(int idUsuario, String nombre, int idRol, int sucursal) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.idRol = idRol;
        this.idSucursal = sucursal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int getIdRol() {
        return idRol;
    }

    public int getIdSucursal() {
        return idSucursal;
    }
    
    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    } 
    
    public String getNombreSucursal() {
        return nombreSucursal;
    }
}
