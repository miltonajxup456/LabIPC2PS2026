/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Pedido;

import SQL.ConnectorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorGuardadoPedido {
    
    public int crearPedido(int idProducto, int idPartida) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Pedido (producto, partida) VALUES (?, ?)";
        int numeroPedido = 0;
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmPedido = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmPedido.setInt(1, idProducto);
            pstmPedido.setInt(2, idPartida);
            
            pstmPedido.executeUpdate();
            try (ResultSet resultSet = pstmPedido.getGeneratedKeys()) {
                if (resultSet.next()) {
                    numeroPedido = resultSet.getInt(1);
                }
            }
        }
        return numeroPedido;
     }
    
    public void actualizarEstado(int numeroPedido, int idEstado) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Pedido SET estado = ? WHERE numero_pedido= ?";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmUpdate = connection.prepareStatement(sql)) {
            pstmUpdate.setInt(1, idEstado);
            pstmUpdate.setInt(2, numeroPedido);
            
            pstmUpdate.executeUpdate();
        }
    }
    
}
