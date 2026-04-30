/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Categoria;

/**
 *
 * @author millin-115
 */
public class CategoriaDB {
    
    private final int idCategoria;
    private final String nombreCategoria;
    private final String descripcion;
    private final boolean habilitado;

    public CategoriaDB(int idCategoria, String nombre_categoria, String descripcion, boolean habilitado) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombre_categoria;
        this.descripcion = descripcion;
        this.habilitado = habilitado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

}
