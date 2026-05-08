/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Rechazo.RechazoDB;
import Modelos.Rechazo.RechazoRequest;
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
public class RechazoDAO {
    
    private static final String RECHAZOS_PROYECTO = "SELECT * FROM Rechazo_Entrega WHERE proyecto = ? ORDER BY id_rechazo DESC";
    private static final String AGREGAR_RECHAZO = "INSERT INTO Rechazo_Entrega (respuesta, proyecto, freelancer) VALUES (?,?,?)";
    
    public List<RechazoDB> getRechazosProyecto(int idProyecto) throws DataBaseException {
        List<RechazoDB> rechazos = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection(); 
                PreparedStatement select = connection.prepareStatement(RECHAZOS_PROYECTO)) {
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    rechazos.add(armarRechazo(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer todos los rechazos "+e);
        }
        return rechazos;
    }
    
    public void agregarRechazo(RechazoRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection(); 
                PreparedStatement insert = connection.prepareStatement(AGREGAR_RECHAZO)) {
            insert.setString(1, request.getRespuesta());
            insert.setInt(2, request.getProyecto());
            insert.setString(3, request.getFreelancer());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar rechazo"+e);
        }
    }
    
    private RechazoDB armarRechazo(ResultSet rs) throws SQLException {
        return new RechazoDB(rs.getInt("id_rechazo"), rs.getString("respuesta"), rs.getString("fecha"), rs.getInt("proyecto"), rs.getString("freelancer"));
    }
    
}
