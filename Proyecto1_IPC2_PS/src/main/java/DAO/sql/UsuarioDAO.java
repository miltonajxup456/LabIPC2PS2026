/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Login.UsuarioDB;
import Model.Login.UsuarioRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author millin-115
 */
public class UsuarioDAO {
    
    private final String CONSULTA_USUARIOS = "SELECT * FROM Usuario";
    private final String CONSULTA_SOLICITANTE = "SELECT * FROM Usuario WHERE BINARY nombre = ?";
    private final String USUARIO_POR_NOMBRE = "SELECT * FROM Usuario WHERE nombre = ?";
    private final String CREAR_USUARIO = "INSERT INTO Usuario (nombre, password_user, tipo_rol) VALUES (?, ?, ?)";
    private final String ELIMINAR_USUARIO = "DELETE FROM Usuario WHERE id_usuario = ?";
    
    public List<UsuarioDB> getUsuarios() {
        List<UsuarioDB> usuarios = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement consulta = connection.prepareStatement(CONSULTA_USUARIOS)) {
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(getUsuario(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error en base de datos "+e);
        }
        return usuarios;
    }
    
    public UsuarioDB consultarUsuario(UsuarioRequest solicitante) {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement ps = connection.prepareStatement(CONSULTA_SOLICITANTE)) {
            ps.setString(1, solicitante.getNombre());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return getUsuario(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error en base de datos "+e);
        }
        return null;
    }
    
    public UsuarioDB usuarioPorNombre(String nombre) {
        UsuarioDB usuario = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement ps = connection.prepareStatement(USUARIO_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario por nombre "+e);
        }
        return usuario;
    }
    
    public void crearUsuario(UsuarioRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement ps = connection.prepareStatement(CREAR_USUARIO)) {
            ps.setString(1, request.getNombre());
            ps.setString(2, request.getPassword_user());
            ps.setInt(3, request.getRol());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al crear usuario "+e);
        }
    }
    
    public void eliminarUsuario(int idUsuario) throws EntidadNoEncontradaException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_USUARIO)) {
            delete.setInt(1, idUsuario);
            
            delete.executeUpdate();
        } catch (Exception e) {
            throw new EntidadNoEncontradaException("Error al eliminar Usuario "+e);
        }
    }
    
    private UsuarioDB getUsuario(ResultSet rs) throws SQLException {
        UsuarioDB usuario = new UsuarioDB(
                        rs.getInt("id_usuario"), 
                        rs.getString("nombre"), 
                        rs.getString("password_user"), 
                        rs.getInt("tipo_rol"));
        return usuario;
    }
    
}
