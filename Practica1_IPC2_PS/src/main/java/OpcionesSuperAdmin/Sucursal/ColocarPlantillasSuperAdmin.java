/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesSuperAdmin.Sucursal;

import Excepciones.ListaException;
import Listas.ListaGenerica;
import OpcionesSuperAdmin.Frontent.PlantillaSucursal;
import OpcionesSuperAdmin.Frontent.PlantillaSucursalCambio;
import OpcionesSuperAdmin.Frontent.PlantillaUsuarioCambio;
import OpcionesSuperAdmin.Frontent.VentanaCambiarUsuario;
import OpcionesSuperAdmin.Frontent.VentanaCrearUsuario;
import SQL.Sucursal.GestorSucursal;
import SQL.Sucursal.Sucursal;
import SQL.Usuario.GestorUsuario;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ColocarPlantillasSuperAdmin {
    
    private final GestorSucursal gestorSucursal;
    private final GestorUsuario gestorUsuario;
    
    public ColocarPlantillasSuperAdmin(GestorSucursal gestorSucursal, GestorUsuario gestorUsuario) {
        this.gestorSucursal = gestorSucursal;
        this.gestorUsuario = gestorUsuario;
    }
    
    public void agregarSucursalesParaCrear(VentanaCrearUsuario ventana) throws SQLException, ClassNotFoundException, ListaException {
        ListaGenerica<Sucursal> sucursales = gestorSucursal.getSucursales();
        Sucursal sucursal;
        for (int i = 0; i < sucursales.getTamaño(); i++) {
            sucursal = sucursales.obtenerConIndice(i);
            PlantillaSucursal plantilla = new PlantillaSucursal(ventana, sucursal);
            ventana.agregarSucursal(plantilla);
        }
    }
    
    public void agregarOpcionesCambiarSucursal(VentanaCambiarUsuario ventana) throws SQLException, ClassNotFoundException, ListaException {
        ListaGenerica<Sucursal> sucursales = gestorSucursal.getSucursales();
        Sucursal sucursal;
        for (int i = 0; i < sucursales.getTamaño(); i++) {
            sucursal = sucursales.obtenerConIndice(i);
            PlantillaSucursalCambio plantilla = new PlantillaSucursalCambio(ventana, sucursal);
            ventana.agregarSucursal(plantilla);
        }
        
        ListaGenerica<Usuario> usuarios = gestorUsuario.getUsuarios();
        Usuario usuario;
        for (int i = 0; i < usuarios.getTamaño(); i++) {
            usuario = usuarios.obtenerConIndice(i);
            PlantillaUsuarioCambio plantilla = new PlantillaUsuarioCambio(ventana, usuario);
            ventana.agregarUsuario(plantilla);
        }
    }
    
}
