/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesSucursalProducto;

import Listas.ListaGenerica;
import SQL.Productos.GestorProductosSucursal;
import SQL.Productos.Producto;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class OpcionCrearProducto {
    
    private final ListaGenerica<Producto> todosLosProductos;
    private final GestorProductosSucursal gestorProductos;
    
    public OpcionCrearProducto(ListaGenerica<Producto> todosLosProductos) {
        this.todosLosProductos = todosLosProductos;
        this.gestorProductos = new GestorProductosSucursal();
    }
    
    public void crear(String nombreProducto) throws SQLException, ClassNotFoundException {
        Producto producto = gestorProductos.agregarProducto(nombreProducto);
        todosLosProductos.agregarElemento(producto);
    }
    
}
