/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Usuario;

import Listas.ListaGenerica;
import SQL.ConnectorDB;
import Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorUsuario {
    
    public void agregarUsuario(String nombreUsuario, String contraseña, int idRol, int idSucursal) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Usuario (nombre, clave_ingreso, id_rol, sucursal) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmAgregar = connection.prepareStatement(sql)) {
            pstmAgregar.setString(1, nombreUsuario);
            pstmAgregar.setString(2, contraseña);
            pstmAgregar.setInt(3, idRol);
            pstmAgregar.setInt(4, idSucursal);
            
            pstmAgregar.executeUpdate();
        }
    }
    
    public ListaGenerica<Usuario> getUsuarios() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_usuario, nombre, clave_ingreso, id_rol, sucursal FROM Usuario";
        ListaGenerica<Usuario> usuarios = new ListaGenerica<>();
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmLista = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmLista.executeQuery()) {
                while (resultSet.next()) {
                    Usuario usuario = new Usuario(resultSet.getInt("id_usuario"), resultSet.getString("nombre"), resultSet.getInt("id_rol"), resultSet.getInt("sucursal"));
                    usuarios.agregarElemento(usuario);
                }
            }
        }
        return usuarios;
    }
    
    public void cambiarDeSucursal(int idSucursal, int idUsuario) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Usuario SET sucursal = ? WHERE id_usuario = ?";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmCambiar = connection.prepareStatement(sql)) {
            pstmCambiar.setInt(1, idSucursal);
            pstmCambiar.setInt(2, idUsuario);
            
            pstmCambiar.executeUpdate();
        }
    }
    
}
    