/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesSucursalProducto;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSucursalProducto.Frontent.PlantillaProducto;
import OpcionesSucursalProducto.Frontent.VentanaHabilitarProductos;
import SQL.Productos.Producto;
import Usuario.Usuario;

/**
 *
 * @author millin-115
 */
public class ColocarPlantillas {
    
    private final ListaGenerica<Producto> todosLosProductos;
    private OpcionDesactivarActivar opcionesProducto;
    private final VentanaHabilitarProductos ventanaEleccion;
    private final OpcionRenombrarProducto opcionRenombrarProducto;
    private Usuario usuario;
    
    public ColocarPlantillas(VentanaHabilitarProductos ventanaEleccion, ListaGenerica<Producto> todosLosProductos) {
        this.ventanaEleccion = ventanaEleccion;
        this.todosLosProductos = todosLosProductos;
        this.opcionRenombrarProducto = new OpcionRenombrarProducto();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void colocar(ListaGenerica<Producto> productosDeSucursal, String nombreSucursal) throws ListaException {
        opcionesProducto = new OpcionDesactivarActivar(productosDeSucursal);
        ventanaEleccion.escribirNombre(nombreSucursal);
        opcionRenombrarProducto.setProductosDeSucursal(productosDeSucursal);
        for (int i = 0; i < todosLosProductos.getTamaño(); i++) {
            Producto productoLista = todosLosProductos.obtenerConIndice(i);
            Producto productoSucursal = existeEnSucursal(productosDeSucursal, productoLista);
            PlantillaProducto plantilla;
            if (productoSucursal != null) {
                plantilla = new PlantillaProducto(opcionesProducto, opcionRenombrarProducto, productoSucursal, usuario);
                plantilla.setTextoBoton("Deshabilitar");
                plantilla.setHabilitado(true);
            } else {
                plantilla = new PlantillaProducto(opcionesProducto, opcionRenombrarProducto, productoLista, usuario);
                plantilla.setTextoBoton("Habilitar");
                plantilla.setHabilitado(false);
            }
            ventanaEleccion.agregarProducto(plantilla);
        }
        
    }
    
    private Producto existeEnSucursal(ListaGenerica<Producto> productosSucursal, Producto productoLista) throws ListaException {
        for (int i = 0; i < productosSucursal.getTamaño(); i++) {
            Producto productoSucursal = productosSucursal.obtenerConIndice(i);
            if (productoLista.getIdProducto() == productoSucursal.getIdProducto()) {
                return productoSucursal;
            }
        }
        return null;
    }
    
}
