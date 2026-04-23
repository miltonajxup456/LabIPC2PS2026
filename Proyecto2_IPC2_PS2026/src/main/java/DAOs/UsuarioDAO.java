/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author millin-115
 */
public class UsuarioDAO {
    
    private static final String CREAR_USUARIO = "INSERT INTO Usuario (nombre_usuario, nombre, password_user, correo_electronico, telefono, direccion, cui, fecha_nac, informacion_usuario, baneo, saldo, rol) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String BUSCAR_POR_USUARIO = "SELECT uso.*, rol.tipo_rol FROM Usuario usu JOIN Rol rol ON usu.rol = rol.id_rol WHERE nombre_usuario = ?";
    
    public void crearUsuario(UsuarioRequest request) throws DataBaseException {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(request.getPasswordUser());
        
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_USUARIO)) {
            insert.setString(1, request.getNombreUsuario());
            insert.setString(2, request.getNombre());
            insert.setString(3, hashedPassword);
            insert.setString(4, request.getCorreoElectronico());
            insert.setString(5, request.getTelefono());
            insert.setString(6, request.getDireccion());
            insert.setString(7, request.getCui());
            insert.setString(8, request.getFechaNac());
            insert.setString(9, request.getInformacionUsuario());
            insert.setBoolean(10, false);
            insert.setDouble(11, request.getSaldo());
            insert.setString(12, request.getTipoRol());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error Interno en la Base de Datos al crear usuario "+e);
        }
    }
    
    public UsuarioDB buscarPorNombreUsuario(UsuarioRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_POR_USUARIO)) {
            select.setString(1, request.getNombreUsuario());
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error interno en la base de datos al buscar usuario por nombre "+e);
        }
        return null;
    }
    
    private UsuarioDB armarUsuario(ResultSet rs) throws SQLException {
        UsuarioDB usuario = new UsuarioDB(rs.getString("nombre_usuario"), rs.getString("nombre"), rs.getString("password_user"), 
                rs.getString("correo_electronico"), rs.getString("telefono"), rs.getString("direccion"), rs.getString("cui"), 
                rs.getString("fecha_nac"), rs.getString("fecha_nac"), 
                rs.getBoolean("baneo"), rs.getDouble("saldo"), rs.getInt("rol"), rs.getString("tipo_rol"));
        return usuario;
    }
    
}
