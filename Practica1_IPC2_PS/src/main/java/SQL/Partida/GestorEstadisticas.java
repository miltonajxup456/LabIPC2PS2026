/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Partida;

import Listas.ListaGenerica;
import OpcionesAdministrador.Estadisticas.EstadisticaRanking;
import SQL.ConnectorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorEstadisticas {
    
    public ListaGenerica<EstadisticaRanking> getEstadisticasGlobales() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Usuario.nombre, Sucursal.nombre AS nombre_sucursal, Partida.puntaje, Partida.nivel_alcanzado "
                + "FROM Usuario "
                + "JOIN Sucursal ON Usuario.sucursal = Sucursal.id_sucursal "
                + "JOIN Partida ON Partida.usuario = Usuario.id_usuario "
                + "ORDER BY Partida.puntaje DESC";
        ListaGenerica<EstadisticaRanking> estadisticas = new ListaGenerica<>();
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStats = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmStats.executeQuery()) {
                while (resultSet.next()) {
                    EstadisticaRanking estadistica = new EstadisticaRanking(resultSet.getString("nombre"), resultSet.getString("nombre_sucursal"), 
                            resultSet.getInt("puntaje"), resultSet.getInt("nivel_alcanzado"));
                    estadisticas.agregarElemento(estadistica);
                }
            }
        }
        return estadisticas;
    }
    
    public ListaGenerica<EstadisticaRanking> getEstadisticasPorSucursal(int idSucursal) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Usuario.nombre, Sucursal.nombre AS nombre_sucursal, Partida.puntaje, Partida.nivel_alcanzado "
                + "FROM Usuario "
                + "JOIN Sucursal ON Usuario.sucursal = Sucursal.id_sucursal "
                + "JOIN Partida ON Partida.usuario = Usuario.id_usuario "
                + "WHERE Sucursal.id_sucursal = ? "
                + "ORDER BY Partida.puntaje DESC";
        ListaGenerica<EstadisticaRanking> estadisticas = new ListaGenerica<>();
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStats = connection.prepareStatement(sql)) {
            pstmStats.setInt(1, idSucursal);
            
            try (ResultSet resultSet = pstmStats.executeQuery()) {
                while (resultSet.next()) {
                    EstadisticaRanking estadistica = new EstadisticaRanking(resultSet.getString("nombre"), resultSet.getString("nombre_sucursal"), 
                            resultSet.getInt("puntaje"), resultSet.getInt("nivel_alcanzado"));
                    estadisticas.agregarElemento(estadistica);
                }
            }
        }
        return estadisticas;
    }
    
}
