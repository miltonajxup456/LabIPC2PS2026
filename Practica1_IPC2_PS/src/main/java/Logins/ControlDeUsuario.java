/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logins;

import ControlCocina.OrganizadorDeCocina;
import Excepciones.LoginUsuarioException;
import Listas.ListaGenerica;
import Menus.VentanaMenuAdministrador;
import Menus.VentanaMenuSuperAdmin;
import Menus.VentanaMenuUsuario;
import SQL.ConnectorDB;
import SQL.Productos.Producto;
import SQL.Productos.ProductosDeSucursal;
import Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ControlDeUsuario {
    
    private final VentanaMenuSuperAdmin menuSuperAdmin;
    private final VentanaMenuAdministrador menuAdmin;
    private final VentanaMenuUsuario menuUsuario;
    private final OrganizadorDeCocina organizador;
    private final ListaGenerica<Producto> todosLosProductos;
    private final ProductosDeSucursal productoSucursal;

    public ControlDeUsuario(VentanaMenuSuperAdmin menuSuperAdmin, VentanaMenuAdministrador menuAdmin, VentanaMenuUsuario menuUsuario, 
            OrganizadorDeCocina organizador, ListaGenerica<Producto> todosLosProductos) {
        this.menuSuperAdmin = menuSuperAdmin;
        this.menuAdmin = menuAdmin;
        this.menuUsuario = menuUsuario;
        this.organizador = organizador;
        this.todosLosProductos = todosLosProductos;
        this.productoSucursal = new ProductosDeSucursal();
    }
    
    /*
    El tipo de usuario es como en la base de datos
    1 superAdministrador
    2 administrador
    3 usuario
    */
    
    public void validarUsuario(int tipoUsuario, String nombreUsuario, String contraseña) throws SQLException, ClassNotFoundException, LoginUsuarioException {
        String sql = "SELECT id_usuario, nombre, id_rol, sucursal FROM Usuario WHERE id_rol = ? AND nombre = ? AND clave_ingreso = ?";
        String sqlSucursal = "SELECT nombre FROM Sucursal WHERE id_sucursal = ?";
        Usuario usuarioActivo;
        
        try (Connection conn = ConnectorDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql);
                PreparedStatement pstm2 = conn.prepareStatement(sqlSucursal)) {
            pstm.setInt(1, tipoUsuario);
            pstm.setString(2, nombreUsuario);
            pstm.setString(3, contraseña);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    usuarioActivo = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getInt("id_rol"), rs.getInt("sucursal"));
                } else {
                    throw new LoginUsuarioException("Usuario o contraseña incorrectos");
                }
            }
            
            pstm2.setInt(1, usuarioActivo.getIdSucursal());
            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    usuarioActivo.setNombreSucursal(rs.getString("nombre"));
                }
            }
        }
        
        ListaGenerica<Producto> productosSucursal = productoSucursal.buscar(usuarioActivo);
        
        switch (tipoUsuario) {
            case 1:
                organizador.setProductosDisponibles(todosLosProductos);
                organizador.setUsuario(usuarioActivo);
                menuSuperAdmin.mostrar(true);
                break;
            case 2:
                menuAdmin.setProductosDeSucursal(productosSucursal);
                menuAdmin.setUsuario(usuarioActivo);
                menuAdmin.mostrar();
                break;
            case 3:
                organizador.setProductosDisponibles(productosSucursal);
                organizador.setUsuario(usuarioActivo);
                menuUsuario.setUsuario(usuarioActivo);
                menuUsuario.mostrar();
                break;
            default:
                throw new LoginUsuarioException("No se eligio un tipo de usuario");
        }
        
    }
    
    
}
