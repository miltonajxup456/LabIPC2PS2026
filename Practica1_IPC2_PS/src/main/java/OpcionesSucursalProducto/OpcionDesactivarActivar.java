/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesSucursalProducto;

import Excepciones.DeshabilitarException;
import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaProducto;
import SQL.Productos.GestorProductosSucursal;
import SQL.Productos.Producto;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class OpcionDesactivarActivar {
    
    private final ListaGenerica<Producto> productosDeSucursal;
    private final GestorProductosSucursal gestor;

    public OpcionDesactivarActivar(ListaGenerica<Producto> productosDeSucursal) {
        this.productosDeSucursal = productosDeSucursal;
        this.gestor = new GestorProductosSucursal();
    }
    
    public void habilitarDeshabilitar(boolean habilitado, Producto producto, PlantillaProducto plantilla, Usuario usuario) throws ListaException, SQLException, ClassNotFoundException, DeshabilitarException {
        if (habilitado) {
            evitarQueSeVacie();
            eliminarProducto(producto);
            plantilla.setTextoBoton("Habilitar");
            plantilla.setHabilitado(false);
        } else {
            Producto nuevo = new Producto(producto.getIdProducto(), producto.getNombre(), usuario.getIdSucursal());
            productosDeSucursal.agregarElemento(nuevo);
            gestor.agregarProductoASucursal(nuevo);
            plantilla.setTextoBoton("Deshabilitar");
            plantilla.setHabilitado(true);
        }
    }
    
    private void eliminarProducto(Producto producto) throws ListaException, SQLException, ClassNotFoundException {
        for (int i = 0; i < productosDeSucursal.getTamaño(); i++) {
            Producto productoActual = productosDeSucursal.obtenerConIndice(i);
            if (producto.getIdProducto() == productoActual.getIdProducto()) {
                productosDeSucursal.eliminarConIndice(i);
                gestor.eliminarProductoDeSucursal(productoActual);
                return;
            }
        }
    }
    
    private void evitarQueSeVacie() throws DeshabilitarException {
        if (productosDeSucursal.getTamaño() <= 1) {
            throw new DeshabilitarException("No se puede dejar una sucursal sin Productos");
        }
    }
    
}
