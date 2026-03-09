/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesSucursalProducto;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaProducto;
import SQL.Productos.GestorProductosSucursal;
import SQL.Productos.Producto;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class OpcionRenombrarProducto {
    
    private ListaGenerica<Producto> productosDeSucursal;

    public void setProductosDeSucursal(ListaGenerica<Producto> productosDeSucursal) {
        this.productosDeSucursal = productosDeSucursal;
    }
    
    public boolean estaVacio(String texto) {
        return texto.isBlank() || texto.isEmpty();
    }
    
    public void renombrar(Producto producto, String nuevoNombre, PlantillaProducto plantilla) throws SQLException, ClassNotFoundException, ListaException {
        GestorProductosSucursal gestor = new GestorProductosSucursal();
        gestor.renombrarProducto(producto, nuevoNombre);
        producto.setNombre(nuevoNombre);
        plantilla.setNombre(nuevoNombre);
        actualizarListaSucursal(producto, nuevoNombre);
    }
    
    private void actualizarListaSucursal(Producto producto, String nuevoNombre) throws ListaException {
        for (int i = 0; i < productosDeSucursal.getTamaño(); i++) {
            Producto productoActual = productosDeSucursal.obtenerConIndice(i);
            if (producto.getIdProducto() == productoActual.getIdProducto()) {
                productoActual.setNombre(nuevoNombre);
                return;
            }
        }
    }
    
}
