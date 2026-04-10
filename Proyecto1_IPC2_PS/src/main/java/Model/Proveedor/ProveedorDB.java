/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Proveedor;

/**
 *
 * @author millin-115
 */
public class ProveedorDB {
    
    private final int idProveedor;
    private final String nombre;
    private final String pais;
    private final int tipoServicio;
    private final String nombreServicio;

    public ProveedorDB(int idProveedor, String nombre, String pais, int tipoServicio, String nombreServicio) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.pais = pais;
        this.tipoServicio = tipoServicio;
        this.nombreServicio = nombreServicio;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public int getTipoServicio() {
        return tipoServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }
    
}
