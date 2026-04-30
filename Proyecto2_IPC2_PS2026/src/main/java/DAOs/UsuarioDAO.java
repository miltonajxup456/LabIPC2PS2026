/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Habilidad.ListaHabilidades;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author millin-115
 */
public class UsuarioDAO {
    
    private static final String CREAR_USUARIO = "INSERT INTO Usuario (nombre_usuario, nombre, password_user, correo_electronico, telefono, direccion, cui, fecha_nac, saldo, rol) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String BUSCAR_POR_USUARIO = "SELECT usu.*, rol.tipo_rol FROM Usuario usu JOIN Rol rol ON usu.rol = rol.id_rol WHERE nombre_usuario = ?";
    private static final String BUSCAR_CLIENTE = "SELECT * FROM Cliente WHERE id_cliente = ?";
    private static final String BUSCAR_FREELANCE = "SELECT free.*, niv.tipo_nivel FROM Freelance free JOIN Nivel_De_Experiencia niv WHERE free.id_freelancer = ?";
    private static final String ACTUALIZAR_DATOS_USUARIO = "UPDATE Usuario SET nombre = ?, password_user = ?, correo_electronico = ?, telefono = ?, direccion = ?, cui = ?, fecha_nac = ?, informacion_usuario = ?, baneo = ?, saldo = ? WHERE nombre_usuario = ?";
    private static final String ACTUALIZAR_CLIENTE = "UPDATE Cliente SET descripcion_empresa = ?, industria_perteneciente = ?, sitio_web = ? WHERE nombre_usuario = ?";
    private static final String ACTUALIZAR_FREELANCER = "UPDATE Freelancer SET biografia = ?, tarifa_referencial = ? WHERE nombre_usuario = ?";
    private static final String AGREGAR_HABILIDADES = "INSERT INTO Habilidad_Freelancer (habilidad, freelancer) VALUES (?,?)";
    private static final String ELIMINAR_HABILIDAD_FREELANCER = "DELETE Habilidad_Freelancer WHERE habilidad = ?, freelancer = ?";
    private static final String ELIMINAR_USUARIO = "DELETE Cliente WHERE nombre_usuario = ?";
    
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
            insert.setDouble(9, request.getSaldo());
            insert.setInt(10, request.getRol());
            
            insert.executeUpdate();
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
        
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_FREELANCE)) {
            select.setString(1, nombreUsuario);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return new FreelancerDB(rs.getString("biografia"), rs.getDouble("tarifa_referencial"), rs.getInt("nivel_experiencia"), rs.getString("tipo_nivel"));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error interno en base de datos al amrar Freelance "+e);
        }
        return null;
    }
    
    public void actualizarCliente(ClienteRequest request, String idCliente) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CLIENTE)) {
            actualizarDatosUsuario(connection, request);
            update.setString(1, request.getDescripcionEmpresa());
            update.setString(2, request.getIndustriaPerteneciente());
            update.setString(3, request.getSitioWeb());
            update.setString(4, idCliente);
            
            update.executeUpdate();
        } catch (Exception e) {
            throw new DataBaseException("Error interno al actualizar Cliente "+e);
        }
    }
    
    public void actualizarFreelancer(FreelanceRequest request, String idCliente) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_FREELANCER)) {
            actualizarDatosUsuario(connection, request);
            update.setString(1, request.getBiografia());
            update.setDouble(2, request.getTarifaReferencial());
            update.setString(3, idCliente);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error interno al acualizar Freelancer "+e);
        }
    }
    
    private void actualizarDatosUsuario(Connection connection, UsuarioRequest request) throws DataBaseException, SQLException {
        try (PreparedStatement update = connection.prepareCall(ACTUALIZAR_DATOS_USUARIO)) {
            update.setString(1, request.getNombre());
            update.setString(2, request.getPasswordUser());
            update.setString(3, request.getCorreoElectronico());
            update.setString(4, request.getTelefono());
            update.setString(5, request.getDireccion());
            update.setString(6, request.getCui());
            update.setString(7, request.getFechaNac());
            update.setString(8, request.getInformacionUsuario());
            update.setBoolean(9, request.isBaneo());
            update.setDouble(10, request.getSaldo());
            update.setString(11, request.getNombreUsuario());
            
            update.executeUpdate();
        }
    }
    
    public void agregarHabilidades(ListaHabilidades lista, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_HABILIDADES)) {
            for (int i = 0; i < lista.getListaIds().size(); i++) {
                int idHabilidad = lista.getListaIds().get(i);
                insert.setInt(1, idHabilidad);
                insert.setString(2, idFreelancer);
                
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error interno al acualizar Freelancer "+e);
        }
    }
    
    public void eliminarHabilidad(int idHabilidad, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_HABILIDAD_FREELANCER)) {
            delete.setInt(1, idHabilidad);
            delete.setString(2, idFreelancer);

            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error interno al acualizar Freelancer "+e);
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
