/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Sucursal;

/**
 *
 * @author millin-115
 */
public class Sucursal {
    
    private int idSucursal;
    private String nombre;

    public Sucursal(int idSucursal, String nombre) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public String getNombre() {
        return nombre;
    }
    
}
