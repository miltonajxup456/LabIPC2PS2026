/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Pedido;

import SQL.ConnectorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorEstadoPedido {
    
    public void guardarEstado(int idPedido, int idEstado) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Historial_Pedido (id_pedido, id_estado) VALUES (?, ?)";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmPedido = connection.prepareStatement(sql)) {
            pstmPedido.setInt(1, idPedido);
            pstmPedido.setInt(2, idEstado);
            
            pstmPedido.executeUpdate();
        }
    }
    
}
