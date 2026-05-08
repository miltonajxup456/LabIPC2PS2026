/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Reporte.CategoriaTrabajada;
import Modelos.Reporte.ContratoCompletado;
import Modelos.Reporte.GastoCategoria;
import Modelos.Reporte.HistorialProyecto;
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
public class ReporteDAO {
    /*
    gasto
    SELECT cat.nombre_categoria, SUM(his.monto_proyecto) AS total FROM Historial_Comision_Proyecto his JOIN Proyecto proy ON his.proyecto = proy.id_proyecto JOIN Categoria cat ON his.categoria = cat.id_categoria WHERE proy.estado = 5 AND his.fecha BETWEEN '2026-01-01' AND '2026-03-01' GROUP BY his.categoria, cat.nombre_categoria;
    SELECT cat.nombre_categoria, SUM(his.monto_proyecto) AS total FROM Historial_Comision_Proyecto his JOIN Categoria cat ON his.categoria = cat.id_categoria WHERE his.fecha BETWEEN ? AND ? AND his.cliente = ? GROUP BY his.categoria, cat.nombre_categoria;
    contratos
    SELECT proy.*, his.monto_proyecto - his.monto_proyecto * his.porcentaje_comision / 100 AS monto_recibido, cal.calificacion FROM Proyecto proy JOIN Historial_Comision_Proyecto his ON proy.id_proyecto = his.proyecto LEFT JOIN Calificacion_Freelancer cal ON proy.id_proyecto = cal.proyecto;
    
    SELECT cat.nombre_categoria, COUNT(his.categoria) AS cantidad_contratos, SUM(his.monto_proyecto - his.monto_proyecto * his.porcentaje_comision / 100) AS total_ingresos FROM Historial_Comision_Proyecto his JOIN Categoria cat ON his.categoria = cat.id_categoria WHERE his.freelancer = 'Milton3' GROUP BY his.categoria ORDER BY cantidad_contratos DESC LIMIT 5;
    */
    private static final String PROYECTOS_PUBLICADOS = ""
            + "SELECT proy.*, es.tipo_estado FROM Proyecto proy "
            + "JOIN Estado_Proyecto es ON proy.estado = es.id_estado "
            + "WHERE cliente = ? AND fecha_publicacion BETWEEN ? AND ?";
//    private static final String GASTO_POR_CATEGORIA_CLIENTE = ""
//            + "SELECT cat.nombre_categoria, SUM(his.monto_proyecto) AS total "
//            + "FROM Historial_Comision_Proyecto his JOIN Proyecto proy ON his.proyecto = proy.id_proyecto "
//            + "JOIN Categoria cat ON his.categoria = cat.id_categoria "
//            + "WHERE proy.estado = 5 AND his.cliente = ?  AND his.fecha BETWEEN ? AND ? GROUP BY his.categoria, cat.nombre_categoria";
    private static final String GASTO_POR_CATEGORIA_CLIENTE = ""
            + "SELECT cat.nombre_categoria, SUM(his.monto_proyecto) AS total "
            + "FROM Historial_Comision_Proyecto his JOIN Categoria cat ON his.categoria = cat.id_categoria "
            + "WHERE his.cliente = ? AND his.fecha BETWEEN ? AND ? GROUP BY his.categoria, cat.nombre_categoria";
    private static final String CONTRATOS_COMPLETADOS = ""
            + "SELECT his.cliente, his.proyecto, proy.titulo, his.monto_proyecto - his.monto_proyecto * his.porcentaje_comision / 100 AS monto_recibido, cal.calificacion "
            + "FROM Proyecto proy JOIN Historial_Comision_Proyecto his ON proy.id_proyecto = his.proyecto "
            + "LEFT JOIN Calificacion_Freelancer cal ON proy.id_proyecto = cal.proyecto "
            + "WHERE his.freelancer = ? AND his.fecha BETWEEN ? AND ?";
    private static final String TOP_CATEGORIAS = ""
            + "SELECT cat.nombre_categoria, COUNT(his.categoria) AS cantidad_contratos, "
            + "SUM(his.monto_proyecto - his.monto_proyecto * his.porcentaje_comision / 100) AS total_ingresos "
            + "FROM Historial_Comision_Proyecto his JOIN Categoria cat ON his.categoria = cat.id_categoria "
            + "WHERE his.freelancer = ? GROUP BY his.categoria ORDER BY cantidad_contratos DESC LIMIT 5";
    
    public List<HistorialProyecto> getHistorialProyecto(String idCliente, String fechaInicial, String fechaFinal) throws DataBaseException {
        List<HistorialProyecto> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PROYECTOS_PUBLICADOS)) {
            select.setString(1, idCliente);
            select.setString(2, fechaInicial);
            select.setString(3, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(new HistorialProyecto(rs.getInt("estado"), rs.getString("tipo_estado"), rs.getDouble("presupuesto"), rs.getString("freelancer")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer Historial de Proyectos "+e.getMessage());
        }
        return historial;
    }
    
    public List<GastoCategoria> getGastosCategoria(String fechaInicial, String fechaFinal, String idCliente) throws DataBaseException {
        List<GastoCategoria> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(GASTO_POR_CATEGORIA_CLIENTE)) {
            select.setString(1, idCliente);
            select.setString(2, fechaInicial);
            select.setString(3, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(new GastoCategoria(rs.getString("nombre_categoria"), rs.getDouble("total")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer Historial de Proyectos "+e.getMessage());
        }
        return historial;
    }
    
    public List<ContratoCompletado> getContratosCompletados(String fechaInicial, String fechaFinal, String idCliente) throws DataBaseException {
        List<ContratoCompletado> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(CONTRATOS_COMPLETADOS)) {
            select.setString(1, idCliente);
            select.setString(2, fechaInicial);
            select.setString(3, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(new ContratoCompletado(rs.getString("cliente"), rs.getInt("proyecto"), rs.getString("titulo"), rs.getDouble("monto_recibido"), rs.getInt("calificacion")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer Historial de Proyectos "+e.getMessage());
        }
        return historial;
    }
    
    public List<CategoriaTrabajada> getCategoriasTrabajadas(String idFreelancer) throws DataBaseException {
        List<CategoriaTrabajada> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(TOP_CATEGORIAS)) {
            select.setString(1, idFreelancer);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(new CategoriaTrabajada(rs.getString("nombre_categoria"), rs.getInt("cantidad_contratos"), rs.getDouble("total_ingresos")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer Historial de Proyectos "+e.getMessage());
        }
        return historial;
    }
    
}
