/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Historial.HistorialDB;
import Modelos.Historial.HistorialRequest;
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
    //private static final String 
    //private static final String 
    private static final String AGREGAR_HISTORIAL = "INSERT INTO Historial_Comision (monto_proyecto, porcetaje_comision, cliente, freelancer) VALUES (?,?,?,?)";
    
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
    
    public void agregarRegistro(HistorialRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(HISTORIAL_FECHAS)) {
            insert.setDouble(1, request.getMontoProyecto());
            insert.setInt(2, request.getPorcentajeComision());
            insert.setString(3, request.getCliente());
            insert.setString(4, request.getFreelancer());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar registro historial "+e);
        }
    }
    
    private HistorialDB armarHistorial(ResultSet rs) throws SQLException {
        return new HistorialDB(rs.getInt("id_historial"), rs.getDouble("monto_proyecto"), rs.getInt("porcentaje_proyecto"), 
                rs.getString("fecha"), rs.getString("cliente"), rs.getString("freelancer"));
    }
    
}
