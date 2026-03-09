/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Partida;

import SQL.ConnectorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorGuardadoPartida {
    
    public int crearPartida(int idUsuario, int idSucursal, int puntaje, int nivelAlcanzado) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Partida (usuario, sucursal, puntaje, nivel_alcanzado) VALUES (?, ?, ?, ?)";
        int idPartida = 0;
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmPartida = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ) {
            pstmPartida.setInt(1, idUsuario);
            pstmPartida.setInt(2, idSucursal);
            pstmPartida.setInt(3, puntaje);
            pstmPartida.setInt(4, nivelAlcanzado);
            
            pstmPartida.executeUpdate();
            
            try (ResultSet resultSet = pstmPartida.getGeneratedKeys()) {
                if (resultSet.next()) {
                    idPartida = resultSet.getInt(1);
                }
            }
        }
        return idPartida;
    }
    
    public void actualizarPuntajeYNivel(int idPartida, int puntaje, int nivelAlcanzado) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Partida SET puntaje = ?, nivel_alcanzado = ? WHERE id_partida = ?";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmUpdate = connection.prepareStatement(sql)) {
            pstmUpdate.setInt(1, puntaje);
            pstmUpdate.setInt(2, nivelAlcanzado);
            pstmUpdate.setInt(3, idPartida);
            
            pstmUpdate.executeUpdate();
        }
    }
    
}
