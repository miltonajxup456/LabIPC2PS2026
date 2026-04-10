/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Proveedor.ProveedorDB;
import Model.Proveedor.ProveedorRequest;
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
public class ProveedorDAO {
    
    //SELECT prov.*, servicio.servicio AS nombre_servicio FROM Proveedor prov JOIN Tipo_Servicio servicio ON prov.tipo_servicio = servicio.id_tipo_servicio
    //JOIN Destino des ON des.pais = prov.pais WHERE id_destino = ?
    private static final String TODOS_PROVEEDORES = "SELECT prov.*, servicio.servicio AS nombre_servicio "
            + "FROM Proveedor prov "
            + "JOIN Tipo_Servicio servicio "
            + "ON prov.tipo_servicio = servicio.id_tipo_servicio ORDER BY id_proveedor";
    private static final String PROVEEDORES_PAIS = "SELECT prov.*, servicio.servicio AS nombre_servicio "
            + "FROM Proveedor prov "
            + "JOIN Tipo_Servicio servicio "
            + "ON prov.tipo_servicio = servicio.id_tipo_servicio JOIN Destino des ON des.pais = prov.pais WHERE id_destino = ?";
    private static final String PROVEEDOR_POR_NOMBRE = "SELECT prov.*, servicio.servicio AS nombre_servicio "
            + "FROM Proveedor prov "
            + "JOIN Tipo_Servicio servicio "
            + "ON prov.tipo_servicio = servicio.id_tipo_servicio "
            + "WHERE prov.nombre = ? ORDER BY id_proveedor";
    private static final String AGREGAR_PROVEEDOR = "INSERT INTO Proveedor (nombre, pais, tipo_servicio) VALUES (?,?,?)";
    private static final String MODIFICAR_PROVEEDOR = "UPDATE Proveedor SET nombre = ?, pais = ?, tipo_servicio = ? WHERE id_proveedor = ?";
    private static final String ELIMINAR_PROVEEDOR = "DELETE FROM Proveedor WHERE id_proveedor = ?";
    
    public List<ProveedorDB> getProveedores() throws EntidadNoEncontradaException {
        List<ProveedorDB> proveedores = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todo = connection.prepareStatement(TODOS_PROVEEDORES)) {
            try (ResultSet rs = todo.executeQuery()) {
                while (rs.next()) {
                    proveedores.add(armarProveedor(rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al llamar a todos los proveedores "+e);
        }
        return proveedores;
    }
    
    public List<ProveedorDB> getProveedoresPorPais(int idDestino) throws EntidadNoEncontradaException {
        List<ProveedorDB> proveedores = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todo = connection.prepareStatement(PROVEEDORES_PAIS)) {
            todo.setInt(1, idDestino);
            try (ResultSet rs = todo.executeQuery()) {
                while (rs.next()) {
                    proveedores.add(armarProveedor(rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al llamar a todos los proveedores "+e);
        }
        return proveedores;
    }
    
    public ProveedorDB proveedorPorNombre(String nombre) {
        ProveedorDB proveedor = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todo = connection.prepareStatement(PROVEEDOR_POR_NOMBRE)) {
            todo.setString(1, nombre);
            try (ResultSet rs = todo.executeQuery()) {
                if (rs.next()) {
                    proveedor = armarProveedor(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al Buscar Proveedor por nombre "+e);
        }
        return proveedor;
    }
    
    public void agregarProveedor(ProveedorRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_PROVEEDOR)) {
            insert.setString(1, request.getNombre());
            insert.setString(2, request.getPais());
            insert.setInt(3, request.getTipoServicio());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al agregar proveedor "+e);
        }
    }
    
    public void modificarProveedor(ProveedorRequest request, int idProveedor) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(MODIFICAR_PROVEEDOR)) {
            update.setString(1, request.getNombre());
            update.setString(2, request.getPais());
            update.setInt(3, request.getTipoServicio());
            update.setInt(4, idProveedor);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al modificar proveedor "+e);
        }
    }
    
    public void eliminarProveedor(int idProveedor) throws EntidadNoEncontradaException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PROVEEDOR)) {
            delete.setInt(1, idProveedor);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al eliminar proveedor "+e);
        }
    }
    
    private ProveedorDB armarProveedor(ResultSet rs) throws SQLException {
        ProveedorDB proveedor = new ProveedorDB(rs.getInt("id_proveedor"), 
                rs.getString("nombre"), 
                rs.getString("pais"), 
                rs.getInt("tipo_servicio"), 
                rs.getString("nombre_servicio"));
        return proveedor;
    }
    
}
