/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Partida;

import Listas.ListaGenerica;
import OpcionesAdministrador.Estadisticas.EstadisticaHistorial;
import SQL.ConnectorDB;
import Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorEstadisticasHistorial {
    
    public ListaGenerica<EstadisticaHistorial> estadisticasPedidosUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        ListaGenerica<EstadisticaHistorial> entregados = new ListaGenerica<>();
        String sql = "Select usu.nombre, "
                + "COUNT(usu.id_usuario) AS pedidos_completados, "
                + "MAX(par.puntaje) AS mejor_puntuacion "
                + "FROM Usuario usu "
                + "JOIN Partida par ON usu.id_usuario = par.usuario "
                + "JOIN Pedido pe ON par.id_partida = pe.partida "
                + "JOIN Historial_Pedido his ON pe.numero_pedido = his.id_pedido "
                + "WHERE his.id_estado = 4 AND usu.id_usuario = ? "
                + "GROUP BY usu.id_usuario "
                + "ORDER BY pedidos_completados DESC";
        
        String sql2 = "Select Usuario.id_usuario, SUM(Partida.puntaje) AS puntos_acumulados "
                + "FROM Usuario "
                + "JOIN Partida ON Usuario.id_usuario = Partida.usuario "
                + "WHERE Usuario.id_usuario = ? "
                + "GROUP BY Usuario.id_usuario";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStat = connection.prepareStatement(sql);
                PreparedStatement pstmPuntos = connection.prepareStatement(sql2)) {
            pstmStat.setInt(1, usuario.getIdUsuario());
            pstmPuntos.setInt(1, usuario.getIdUsuario());
            
            try (ResultSet resultSet = pstmStat.executeQuery(); 
                    ResultSet rsPuntos = pstmPuntos.executeQuery()) {
                if (resultSet.next()) {
                    EstadisticaHistorial estadistica = new EstadisticaHistorial(
                            resultSet.getString("nombre"), 
                            resultSet.getInt("pedidos_completados"), 
                            resultSet.getInt("mejor_puntuacion"));
                    entregados.agregarElemento(estadistica);
                    if (rsPuntos.next()) {
                        int puntosAcumulados = rsPuntos.getInt("puntos_acumulados");
                        estadistica.setPuntosAcumulados(puntosAcumulados);
                    }
                }
                
            }
        }
        return entregados;
    }
    
    public ListaGenerica<EstadisticaHistorial> estadisticaPedidosPorSucursal(Usuario usuario) throws SQLException, ClassNotFoundException {
        ListaGenerica<EstadisticaHistorial> entregados = new ListaGenerica<>();
        String sql = "Select usu.nombre, "
                + "COUNT(usu.id_usuario) AS pedidos_completados, "
                + "MAX(par.puntaje) AS mejor_puntuacion "
                + "FROM Usuario usu "
                + "JOIN Partida par ON usu.id_usuario = par.usuario "
                + "JOIN Pedido pe ON par.id_partida = pe.partida "
                + "JOIN Historial_Pedido his ON pe.numero_pedido = his.id_pedido "
                + "WHERE his.id_estado = 4 AND usu.sucursal = ? "
                + "GROUP BY usu.id_usuario "
                + "ORDER BY pedidos_completados DESC";
        
        String sql2 = "Select Usuario.id_usuario, SUM(Partida.puntaje) AS puntos_acumulados "
                + "FROM Usuario "
                + "JOIN Partida ON Usuario.id_usuario = Partida.usuario "
                + "WHERE Usuario.sucursal = ? "
                + "GROUP BY Usuario.id_usuario";
        System.out.println("id sucursal "+usuario.getIdSucursal());
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStat = connection.prepareStatement(sql); 
                PreparedStatement pstmPuntos = connection.prepareStatement(sql2)) {
            pstmStat.setInt(1, usuario.getIdSucursal());
            pstmPuntos.setInt(1, usuario.getIdSucursal());
            
            try (ResultSet resultSet = pstmStat.executeQuery();
                    ResultSet rsPuntos = pstmPuntos.executeQuery()) {
                while (resultSet.next()) {
                    EstadisticaHistorial estadistica = new EstadisticaHistorial(
                            resultSet.getString("nombre"), 
                            resultSet.getInt("pedidos_completados"), 
                            resultSet.getInt("mejor_puntuacion"));
                    entregados.agregarElemento(estadistica);
                    if (rsPuntos.next()) {
                        estadistica.setPuntosAcumulados(rsPuntos.getInt("puntos_acumulados"));
                    }
                }
            }
        }
        return entregados;
    }
    
    public ListaGenerica<EstadisticaHistorial> estadisticasPedidosGlobales() throws SQLException, ClassNotFoundException {
        ListaGenerica<EstadisticaHistorial> entregados = new ListaGenerica<>();
        String sql = "Select usu.nombre, "
                + "COUNT(usu.id_usuario) AS pedidos_completados, "
                + "MAX(par.puntaje) AS mejor_puntuacion "
                + "FROM Usuario usu "
                + "JOIN Partida par ON usu.id_usuario = par.usuario "
                + "JOIN Pedido pe ON par.id_partida = pe.partida "
                + "JOIN Historial_Pedido his ON pe.numero_pedido = his.id_pedido "
                + "WHERE his.id_estado = 4 "
                + "GROUP BY usu.id_usuario "
                + "ORDER BY pedidos_completados DESC";
        
        String sql2 = "Select Usuario.id_usuario, SUM(Partida.puntaje) AS puntos_acumulados "
                + "FROM Usuario "
                + "JOIN Partida ON Usuario.id_usuario = Partida.usuario "
                + "GROUP BY Usuario.id_usuario";
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmStat = connection.prepareStatement(sql); 
                PreparedStatement pstmPuntos = connection.prepareStatement(sql2)) {
            
            try (ResultSet resultSet = pstmStat.executeQuery();
                    ResultSet rsPuntos = pstmPuntos.executeQuery()) {
                while (resultSet.next()) {
                    EstadisticaHistorial estadistica = new EstadisticaHistorial(
                            resultSet.getString("nombre"), 
                            resultSet.getInt("pedidos_completados"), 
                            resultSet.getInt("mejor_puntuacion"));
                    entregados.agregarElemento(estadistica);
                    if (rsPuntos.next()) {
                        estadistica.setPuntosAcumulados(rsPuntos.getInt("puntos_acumulados"));
                    }
                }
            }
        }
        return entregados;
    }
    
}
