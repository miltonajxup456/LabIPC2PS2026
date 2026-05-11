/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Usuario.ClienteDB;
import Modelos.Usuario.ClienteRequest;
import Modelos.Usuario.FreelanceRequest;
import Modelos.Usuario.FreelancerDB;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author millin-115
 */
public class UsuarioDAO {
    
    private static final String GET_USUARIOS = "SELECT usu.*, rol.tipo_rol FROM Usuario usu JOIN Rol rol ON usu.rol = rol.id_rol";
    private static final String CREAR_USUARIO = "INSERT INTO Usuario (nombre_usuario, nombre, password_user, correo_electronico, telefono, direccion, cui, fecha_nac, saldo, rol) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String CREAR_CLIENTE = "INSERT INTO Cliente (id_cliente) VALUES (?)";
    private static final String CREAR_FREELANCER = "INSERT INTO Freelancer (id_freelancer) VALUES (?)";
    private static final String BUSCAR_POR_USUARIO = "SELECT usu.*, rol.tipo_rol FROM Usuario usu JOIN Rol rol ON usu.rol = rol.id_rol WHERE nombre_usuario = ?";
    private static final String BUSCAR_CLIENTE = "SELECT * FROM Cliente WHERE id_cliente = ?";
    private static final String BUSCAR_FREELANCE = "SELECT free.*, niv.tipo_nivel FROM Freelancer free JOIN Nivel_De_Experiencia niv ON free.nivel_experiencia = niv.id_nivel WHERE free.id_freelancer = ?";
    private static final String CALIFICACION_PROMEDIO = "SELECT COALESCE(AVG(calificacion), 0) AS calificacion_promedio FROM Calificacion_Freelancer WHERE freelancer = ?";
    private static final String ACTUALIZAR_DATOS_USUARIO = "UPDATE Usuario SET nombre = ?, password_user = COALESCE(?, password_user), correo_electronico = ?, telefono = ?, direccion = ?, cui = ?, fecha_nac = ?, baneo = ?, saldo = ? WHERE nombre_usuario = ?";
    private static final String ACTUALIZAR_SALDO = "UPDATE Usuario SET saldo = ? WHERE nombre_usuario = ?";
    private static final String ACTUALIZAR_CLIENTE = "UPDATE Cliente SET descripcion_empresa = ?, industria_perteneciente = ?, sitio_web = ? WHERE id_cliente = ?";
    private static final String ACTUALIZAR_FREELANCER = "UPDATE Freelancer SET biografia = ?, tarifa_referencial = ?, nivel_experiencia = ? WHERE id_freelancer = ?";
    private static final String BANEAR_USUARIO = "UPDATE Usuario SET baneo = ? WHERE nombre_usuario = ?";
    private static final String ELIMINAR_USUARIO = "DELETE FROM Usuario WHERE nombre_usuario = ?";
    
    public List<UsuarioDB> getUsuarios() {
        List<UsuarioDB> usuarios = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(GET_USUARIOS)){
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(armarUsuario(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al traer todos los usuarios "+e);
        }
        return usuarios;
    }
    
    public void crearUsuario(UsuarioRequest request) throws DataBaseException {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(request.getPasswordUser());
        
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_USUARIO);
                PreparedStatement insertCliente = connection.prepareStatement(CREAR_CLIENTE);
                PreparedStatement insertFreelancer = connection.prepareStatement(CREAR_FREELANCER)) {
            insert.setString(1, request.getNombreUsuario());
            insert.setString(2, request.getNombre());
            insert.setString(3, hashedPassword);
            insert.setString(4, request.getCorreoElectronico());
            insert.setString(5, request.getTelefono());
            insert.setString(6, request.getDireccion());
            insert.setString(7, request.getCui());
            insert.setString(8, request.getFechaNac());
            insert.setDouble(9, request.getSaldo());
            insert.setInt(10, request.getRol());
            
            insert.executeUpdate();
            if (request.getRol() == 2) {
                insertCliente.setString(1, request.getNombreUsuario());
                insertCliente.executeUpdate();
            } else if (request.getRol() == 3) {
                insertFreelancer.setString(1, request.getNombreUsuario());
                insertFreelancer.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error Interno en la Base de Datos al crear usuario "+e);
        }
    }
    
    public UsuarioDB buscarPorNombreUsuario(String nombreUsuario) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_POR_USUARIO)) {
            select.setString(1, nombreUsuario);
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
    
    public ClienteDB armarCliente(String nombreUsuario) throws DataBaseException {
        
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_CLIENTE)) {
            select.setString(1, nombreUsuario);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return new ClienteDB(rs.getString("descripcion_empresa"), rs.getString("industria_perteneciente"), rs.getString("sitio_web"));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error interno en base de datos al armar cliente "+e);
        }
        return null;
    }
    
    public FreelancerDB armarFreelance(String nombreUsuario) throws DataBaseException {
        FreelancerDB freelancer = null;
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_FREELANCE);
                PreparedStatement promedio = connection.prepareStatement(CALIFICACION_PROMEDIO)) {
            select.setString(1, nombreUsuario);
            promedio.setString(1, nombreUsuario);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    freelancer = new FreelancerDB(rs.getString("biografia"), rs.getDouble("tarifa_referencial"), rs.getInt("nivel_experiencia"), rs.getString("tipo_nivel"));
                }
            }
            if (freelancer != null) {
                try (ResultSet rs = promedio.executeQuery()) {
                    if (rs.next()) {
                        double calificacion = rs.getDouble("calificacion_promedio");
                        freelancer.setCalificacionPromedio(calificacion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error interno en base de datos al amrar Freelance "+e);
        }
        return freelancer;
    }
    
    public void actualizarCliente(ClienteRequest request, String idCliente) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CLIENTE)) {
            actualizarDatosUsuario(connection, request, idCliente);
            update.setString(1, request.getDescripcionEmpresa());
            update.setString(2, request.getIndustriaPerteneciente());
            update.setString(3, request.getSitioWeb());
            update.setString(4, idCliente);
            
            update.executeUpdate();
        } catch (Exception e) {
            throw new DataBaseException("Error interno al actualizar Cliente "+e);
        }
    }
    
    public void actualizarFreelancer(FreelanceRequest request, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_FREELANCER)) {
            actualizarDatosUsuario(connection, request, idFreelancer);
            update.setString(1, request.getBiografia());
            update.setDouble(2, request.getTarifaReferencial());
            update.setInt(3, request.getNivelExperiencia());
            update.setString(4, idFreelancer);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error interno al acualizar Freelancer "+e);
        }
    }
    
    public void actualizarSaldoUsuario(double saldo, String idUsuario) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SALDO)) {
            update.setDouble(1, saldo);
            update.setString(2, idUsuario);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al actualizar saldo usuario "+e);
        }
    }
    
    private void actualizarDatosUsuario(Connection connection, UsuarioRequest request, String idUsuario) throws DataBaseException, SQLException {
        if (request.getPasswordUser() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(request.getPasswordUser());
            
            request.setPasswordUser(hashedPassword);
        }
        
        try (PreparedStatement update = connection.prepareCall(ACTUALIZAR_DATOS_USUARIO)) {
            update.setString(1, request.getNombre());
            update.setString(2, request.getPasswordUser());
            update.setString(3, request.getCorreoElectronico());
            update.setString(4, request.getTelefono());
            update.setString(5, request.getDireccion());
            update.setString(6, request.getCui());
            update.setString(7, request.getFechaNac());
            update.setBoolean(8, request.isBaneo());
            update.setDouble(9, request.getSaldo());
            update.setString(10, idUsuario);
            
            update.executeUpdate();
        }
    }
    
    public void banearUsuario(UsuarioRequest request, String idUsuario) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(BANEAR_USUARIO)) {
            update.setBoolean(1, request.isBaneo());
            update.setString(2, idUsuario);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error interno al modificar baneo usuario "+e);
        }
    }
    
    
    public void eliminarUsuario(String idUsuario) throws DataBaseException   {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_USUARIO)) {
            delete.setString(1, idUsuario);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error interno al eliminar usuario "+e);
        }
    }
    
}
