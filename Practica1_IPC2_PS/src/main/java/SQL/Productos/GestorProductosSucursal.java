/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Productos;

import SQL.ConnectorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorProductosSucursal {
    
    public void eliminarProductoDeSucursal(Producto producto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Sucursal_Producto WHERE id_sucursal = ? AND id_producto = ?;";
        try (Connection conn = ConnectorDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, producto.getIdSucursal());
            pstm.setInt(2, producto.getIdProducto());
            
            pstm.executeUpdate();
        }
    }
    
    public void agregarProductoASucursal(Producto producto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Sucursal_Producto (id_sucursal, id_producto) VALUES (?, ?);";
        try (Connection conn = ConnectorDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, producto.getIdSucursal());
            pstm.setInt(2, producto.getIdProducto());
            
            pstm.executeUpdate();
        }
    }
    
    public void renombrarProducto(Producto producto, String nuevoNombre) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Producto set nombre = ? WHERE id_producto = ?";
        try (Connection conn = ConnectorDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nuevoNombre);
            pstm.setInt(2, producto.getIdProducto());
            
            pstm.executeUpdate();
        }
             
    }
    
    public Producto agregarProducto(String nombreProducto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Producto (nombre) VALUES (?)";
        int indiceProducto = 0;
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmAgregar = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmAgregar.setString(1, nombreProducto);
            
            pstmAgregar.executeUpdate();
            
            try (ResultSet resultSet = pstmAgregar.getGeneratedKeys()) {
                if (resultSet.next()) {
                    indiceProducto = resultSet.getInt(1);
                }
            }
        }
        Producto producto = new Producto(indiceProducto, nombreProducto);
        return producto;
    }
    
}
