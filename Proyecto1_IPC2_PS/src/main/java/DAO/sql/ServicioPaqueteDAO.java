/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.ServicioPaquete.ServicioPaqueteDB;
import Model.ServicioPaquete.ServicioPaqueteRequest;
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
public class ServicioPaqueteDAO {
    
    private static final String TODOS_LOS_SERVICIOS = "SELECT sp.*, prov.nombre FROM Servicio_Paquete sp JOIN Proveedor prov ON sp.proveedor = prov.id_proveedor";
    private static final String SERVICIO_POR_PAQUETE = "SELECT sp.*, prov.nombre AS nombre_servicio "
            + "FROM Servicio_Paquete sp "
            + "JOIN Proveedor prov "
            + "ON sp.proveedor = prov.id_proveedor "
            + "WHERE paquete = ?";
    private static final String AGREGAR_SERVICIO_A_PAQUETE = "INSERT INTO Servicio_Paquete (descripcion, costo, paquete, proveedor) VALUES (?,?,?,?)";
    private static final String ACTUALIZAR_SERVICIO = "UPDATE Servicio_Paquete SET descripcion = ?, costo = ? WHERE id_servicio_paquete = ?";
    private static final String ELIMINAR_SERVICIO_DE_PAQUETE = "DELETE FROM Servicio_Paquete WHERE id_servicio_paquete = ?";
    
    public List<ServicioPaqueteDB> getServiciosPaquete(int idPaquete) throws EntidadNoEncontradaException {
        List<ServicioPaqueteDB> servicios = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todos = connection.prepareStatement(SERVICIO_POR_PAQUETE)) {
            todos.setInt(1, idPaquete);
            
            try (ResultSet rs = todos.executeQuery()) {
                while (rs.next()) {
                    servicios.add(armarServicio(rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Ocurrio un error al llamar a los servicios de paquete "+e);
        }
        return servicios;
    }
    
    public void guardarServicio(ServicioPaqueteRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_SERVICIO_A_PAQUETE)) {
            insert.setString(1, request.getDescripcion());
            insert.setDouble(2, request.getCosto());
            insert.setInt(3, request.getPaquete());
            insert.setInt(4, request.getProveedor());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Un id no coincide con los paquetes o proveedores guardados "+e);
        }
    }
    
    public void actualizarPaquete(ServicioPaqueteRequest request, int idServicio) throws EntidadNoEncontradaException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SERVICIO)) {
            update.setString(1, request.getDescripcion());
            update.setDouble(2, request.getCosto());
            update.setInt(3, idServicio);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al actualizar servicio "+e);
        }
    }
    
    public void eliminarServicioDePaquete(int idServicio) throws EntidadNoEncontradaException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(ELIMINAR_SERVICIO_DE_PAQUETE)) {
            insert.setInt(1, idServicio);
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("No se pudo eliminar el servicio porque no se encontro el id "+e);
        }
    }
    
    private ServicioPaqueteDB armarServicio(ResultSet rs) throws SQLException {
        ServicioPaqueteDB servicio = new ServicioPaqueteDB(
                rs.getInt("id_servicio_paquete"), 
                rs.getString("descripcion"), 
                rs.getDouble("costo"), 
                rs.getInt("paquete"), 
                rs.getInt("proveedor"), 
                rs.getString("nombre_servicio"));
        return servicio;
    }
    
}
