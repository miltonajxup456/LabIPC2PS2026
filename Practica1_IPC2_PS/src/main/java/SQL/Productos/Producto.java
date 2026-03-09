/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Productos;

/**
 *
 * @author millin-115
 */
public class Producto {
    
    private final int idProducto;
    private String nombre;
    private int idSucursal;

    public Producto(int idProducto, String nombre) {
        this.idProducto = idProducto;
        this.nombre = nombre;
    }

    public Producto(int idProducto, String nombre, int idSucursal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.idSucursal = idSucursal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdSucursal() {
        return idSucursal;
    }
    
}
