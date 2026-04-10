/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Model.Cliente.ClienteDB;
import Model.Cliente.ClienteRequest;
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
public class ClientesDAO {
    
    private static final String ALL_CLIENTES = "SELECT * FROM Cliente";
    private final static String INSERTAR_CLIENTE = "INSERT INTO Cliente (dpi, nombre, fecha_nac, telefono, email, nacionalidad) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String BUSCAR_CLIENTE = "SELECT * FROM Cliente WHERE dpi = ?";
    private static final String ELIMINAR_CLIENTE = "DELETE FROM Cliente WHERE dpi = ?";
    private static final String ACTUALIZAR_CLIENTE = "UPDATE Cliente SET nombre = ?, fecha_nac = ?, telefono = ?, email = ?, nacionalidad = ? WHERE dpi = ?";
    
    public List<ClienteDB> getClientes() {
        List<ClienteDB> clientes = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todos = connection.prepareStatement(ALL_CLIENTES)) {
            try (ResultSet rs = todos.executeQuery()) {
                while (rs.next()) {
                    clientes.add(crearCliente(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio con todos los clientes "+e);
        }
        return clientes;
    }
    
    public void crearClienteDB(ClienteRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(INSERTAR_CLIENTE)) {
            insert.setString(1, request.getDpi());
            insert.setString(2, request.getNombre());
            insert.setString(3, request.getFechaNac());
            insert.setString(4, request.getTelefono());
            insert.setString(5, request.getEmail());
            insert.setString(6, request.getNacionalidad());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Ocurrio un error al crear cliente "+e);
        }
    }
    
    public void actualizarCliente(String dpiCliente, ClienteRequest actualizacion) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CLIENTE)) {
            update.setString(1, actualizacion.getNombre());
            update.setString(2, actualizacion.getFechaNac());
            update.setString(3, actualizacion.getTelefono());
            update.setString(4, actualizacion.getEmail());
            update.setString(5, actualizacion.getNacionalidad());
            update.setString(6, dpiCliente);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al actualizar el Cliente "+e);
        }
    }
    
    public void eliminarCliente(String dpiCliente) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_CLIENTE)) {
            delete.setString(1, dpiCliente);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al eliminar cliente "+e);
        }
    }
    
    private ClienteDB crearCliente(ResultSet rs) throws SQLException {
        ClienteDB cliente = new ClienteDB(rs.getString("dpi"), 
                rs.getString("nombre"), 
                rs.getString("fecha_nac"), 
                rs.getString("telefono"), 
                rs.getString("email"), 
                rs.getString("nacionalidad"));
        return cliente;
    }
    
}
