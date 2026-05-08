/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Historial.HistorialDB;
import Modelos.Historial.HistorialRequest;
import Modelos.Reporte.CategoriaMasActiva;
import Modelos.Reporte.GananciaFreelancer;
import Modelos.Reporte.GananciasPlataforma;
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
public class HistorialComisionDAO {
    
    private static final String HISTORIAL_FECHAS = "SELECT * FROM Historial_Comision ";
    private static final String MAS_INGRESOS_FREELANCER = ""
            + "SELECT freelancer, SUM(monto_proyecto * porcentaje_comision / 100) AS comision_total "
            + "FROM Historial_Comision_Proyecto "
            + "WHERE fecha BETWEEN ? AND ? "
            + "GROUP BY freelancer ORDER BY comision_total DESC LIMIT 5";
    private static final String CATEGORIA_MAS_ACTIVA = ""
            + "SELECT his.categoria, cat.nombre_categoria, COUNT(his.categoria) AS veces_solicitadas "
            + "FROM Historial_Comision_Proyecto his JOIN Categoria cat ON his.categoria = cat.id_categoria "
            + "WHERE fecha BETWEEN ? AND ? "
            + "GROUP BY his.categoria ORDER BY veces_solicitadas DESC LIMIT 5";
    private static final String INGRESOS_PLATAFORMA = ""
            + "SELECT COALESCE(SUM(monto_proyecto * porcentaje_comision / 100), 0) AS ganancias, COUNT(id_historial) AS contratos_completados "
            + "FROM Historial_Comision_Proyecto WHERE fecha BETWEEN ? AND ?";
    private static final String AGREGAR_REGISTRO_COMISION = ""
            + "INSERT INTO Historial_Comision_Proyecto (monto_proyecto, porcentaje_comision, cliente, freelancer, categoria, proyecto) VALUES (?,?,?,?,?,?)";
    
    public List<HistorialDB> getHistorialFechas(String fechaInicial, String fechaFinal) throws DataBaseException {
        List<HistorialDB> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(HISTORIAL_FECHAS)) {
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(armarHistorial(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar comision entre fechas "+e);
        }
        return historial;
    }
    
    public List<GananciaFreelancer> getGananciaFreelancerEntreFechas(String fechaInicial, String fechaFinal) throws DataBaseException {
        List<GananciaFreelancer> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(MAS_INGRESOS_FREELANCER)) {
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(new GananciaFreelancer(rs.getString("freelancer"), rs.getDouble("comision_total")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar ganancias freelancer entre fechas "+e);
        }
        return historial;
    }
    
    public List<CategoriaMasActiva> getCategoraEntreFechas(String fechaInicial, String fechaFinal) throws DataBaseException {
        List<CategoriaMasActiva> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(CATEGORIA_MAS_ACTIVA)) {
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(new CategoriaMasActiva(rs.getInt("categoria"), rs.getString("nombre_categoria"), rs.getInt("veces_solicitadas")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar ganancias freelancer entre fechas "+e);
        }
        return historial;
    }
    
    public GananciasPlataforma getGanaciasPlataformaEntreFechas(String fechaInicial, String fechaFinal) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(INGRESOS_PLATAFORMA)) {
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return new GananciasPlataforma(rs.getDouble("ganancias"), rs.getInt("contratos_completados"));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar ganancias freelancer entre fechas "+e);
        }
        return null;
    }
    
    public void agregarRegistro(HistorialRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_REGISTRO_COMISION)) {
            insert.setDouble(1, request.getMontoProyecto());
            insert.setInt(2, request.getPorcentajeComision());
            insert.setString(3, request.getCliente());
            insert.setString(4, request.getFreelancer());
            insert.setInt(5, request.getCategoria());
            insert.setInt(6, request.getProyecto());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar registro historial "+e);
        }
    }
    
    private HistorialDB armarHistorial(ResultSet rs) throws SQLException {
        return new HistorialDB(rs.getInt("id_historial"), rs.getDouble("monto_proyecto"), rs.getInt("porcentaje_comision"), 
                rs.getString("fecha"), rs.getString("cliente"), rs.getString("freelancer"), rs.getInt("categoria"));
    }
    
}
