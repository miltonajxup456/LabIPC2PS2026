/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Partida;

import Listas.ListaGenerica;
import OpcionesAdministrador.Estadisticas.EstadisticaEntregado;
import SQL.ConnectorDB;
import SQL.Sucursal.Sucursal;
import Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorEstadisticaEntregados {
    
    public ListaGenerica<EstadisticaEntregado> estadisticasPedidosUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        ListaGenerica<EstadisticaEntregado> entregados = new ListaGenerica<>();
        String sql = "Select usu.nombre, "
                + "COUNT(usu.id_usuario) AS pedidos_completados, "
                + "MAX(par.puntaje) AS mejor_puntuacion "
                + "FROM Usuario usu "
                + "JOIN Partida par ON usu.id_usuario = par.usuario "
                + "JOIN Pedido pe ON par.id_partida = pe.partida "
                + "JOIN Historial_Pedido his ON pe.numero_pedido = his.id_pedido "
                + "WHERE his.id_estado = 4 AND usu.id_usuario = ? "
                + "GROUP BY usu.id_usuario";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStat = connection.prepareStatement(sql)) {
            pstmStat.setInt(1, usuario.getIdUsuario());
            
            try (ResultSet resultSet = pstmStat.executeQuery()) {
                if (resultSet.next()) {
                    EstadisticaEntregado estadistica = new EstadisticaEntregado(
                            resultSet.getString("nombre"), 
                            resultSet.getInt("pedidos_completados"), 
                            resultSet.getInt("mejor_puntuacion"));
                    entregados.agregarElemento(estadistica);
                }
            }
        }
        return entregados;
    }
    
    public ListaGenerica<EstadisticaEntregado> estadisticaPedidosPorSucursal(Sucursal sucursal) throws SQLException, ClassNotFoundException {
        ListaGenerica<EstadisticaEntregado> entregados = new ListaGenerica<>();
        String sql = "Select usu.nombre, "
                + "COUNT(usu.id_usuario) AS pedidos_completados, "
                + "MAX(par.puntaje) AS mejor_puntuacion "
                + "FROM Usuario usu "
                + "JOIN Partida par ON usu.id_usuario = par.usuario "
                + "JOIN Pedido pe ON par.id_partida = pe.partida "
                + "JOIN Historial_Pedido his ON pe.numero_pedido = his.id_pedido "
                + "WHERE his.id_estado = 4 AND usu.sucursal = ? "
                + "GROUP BY usu.id_usuario";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStat = connection.prepareStatement(sql)) {
            pstmStat.setInt(1, sucursal.getIdSucursal());
            
            try (ResultSet resultSet = pstmStat.executeQuery()) {
                while (resultSet.next()) {
                    EstadisticaEntregado estadistica = new EstadisticaEntregado(
                            resultSet.getString("nombre"), 
                            resultSet.getInt("pedidos_completados"), 
                            resultSet.getInt("mejor_puntuacion"));
                    entregados.agregarElemento(estadistica);
                }
            }
        }
        return entregados;
    }
    
    public ListaGenerica<EstadisticaEntregado> estadisticasPedidosGlobales() throws SQLException, ClassNotFoundException {
        ListaGenerica<EstadisticaEntregado> entregados = new ListaGenerica<>();
        String sql = "Select usu.nombre, "
                + "COUNT(usu.id_usuario) AS pedidos_completados, "
                + "MAX(par.puntaje) AS mejor_puntuacion "
                + "FROM Usuario usu "
                + "JOIN Partida par ON usu.id_usuario = par.usuario "
                + "JOIN Pedido pe ON par.id_partida = pe.partida "
                + "JOIN Historial_Pedido his ON pe.numero_pedido = his.id_pedido "
                + "WHERE his.id_estado = 4 "
                + "GROUP BY usu.id_usuario;ç";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStat = connection.prepareStatement(sql)) {
            
            try (ResultSet resultSet = pstmStat.executeQuery()) {
                while (resultSet.next()) {
                    EstadisticaEntregado estadistica = new EstadisticaEntregado(
                            resultSet.getString("nombre"), 
                            resultSet.getInt("pedidos_completados"), 
                            resultSet.getInt("mejor_puntuacion"));
                    entregados.agregarElemento(estadistica);
                }
            }
        }
        return entregados;
    }
    
}
