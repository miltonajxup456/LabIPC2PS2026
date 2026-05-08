/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Cancelacion.CancelacionDB;
import Modelos.Cancelacion.CancelacionRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class CancelacionDAO {
    
    private static final String GET_CANCELACION = "SELECT * FROM Cancelacion_Proyecto WHERE proyecto = ? AND freelancer = ?";
    private static final String AGREGAR_CANCELACION = "INSERT INTO Cancelacion_Proyecto (motivo, proyecto, freelancer) VALUES (?,?,?)";
    
    public CancelacionDB getCancelacion(int idProyecto, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection(); 
                PreparedStatement select = connection.prepareStatement(GET_CANCELACION)) {
            select.setInt(1, idProyecto);
            select.setString(2, idFreelancer);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarCancelacion(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer cancelacion "+e);
        }
        return null;
    }
    
    public void agregarCancelacion(CancelacionRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection(); 
                PreparedStatement insert = connection.prepareStatement(AGREGAR_CANCELACION)) {
            insert.setString(1, request.getMotivo());
            insert.setInt(2, request.getProyecto());
            insert.setString(3, request.getFreelancer());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar Cancelacion "+e);
        }
    }
    
    private CancelacionDB armarCancelacion(ResultSet rs) throws SQLException {
        return new CancelacionDB(rs.getInt("id_cancelacion"), rs.getString("motivo"), rs.getInt("proyecto"), rs.getString("freelancer"));
    }
    
}
