/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Calificacion.CalificacionDB;
import Modelos.Calificacion.CalificacionRequest;
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
public class CalificacionDAO {
    
    private static final String CALIFICACIONES_FREELANCER = "SELECT * FROM Calificacion_Freelancer WHERE freelancer = ?";
    private static final String CALIFICACION_PROYECTO = "SELECT * FROM Calificacion_Freelancer WHERE proyecto = ?";
    private static final String AGREGAR_CALIFICACION = "INSERT INTO Calificacion_Freelancer (calificacion, comentario, proyecto, freelancer) VALUES (?,?,?,?)";
    private static final String MODIFICAR_CALIFICACION = "UPDATE Calificacion_Freelancer SET calificacion = ?, comentario = ? WHERE id_calificacion = ?";
    
    public List<CalificacionDB> getCalificacionesFreelancer(String idFreelanceer) throws DataBaseException {
        List<CalificacionDB> calificaciones = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(CALIFICACIONES_FREELANCER)) {
            select.setString(1, idFreelanceer);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    calificaciones.add(armarCalificacion(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer calificacione freelancer "+e);
        }
        return calificaciones;
    }
    
    public CalificacionDB getCalificacionProyecto(int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(CALIFICACION_PROYECTO)) {
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarCalificacion(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar calificacion proyecto "+e);
        }
        return null;
    }
    
    public void agregarCalificacion(CalificacionRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_CALIFICACION)) {
            insert.setInt(1, request.getCalificacion());
            insert.setString(2, request.getComentario());
            insert.setInt(3, request.getProyecto());
            insert.setString(4, request.getFreelancer());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar calificacion "+e);
        }
    }
    
    public void modificarCalificacion(CalificacionRequest request, int idCalificacion) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(MODIFICAR_CALIFICACION)) {
            update.setInt(1, request.getCalificacion());
            update.setString(2, request.getComentario());
            update.setInt(3, idCalificacion);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al modificar calificacion "+e);
        }
    }
    
    private CalificacionDB armarCalificacion(ResultSet rs) throws SQLException {
        return new CalificacionDB(rs.getInt("id_calificacion"), rs.getInt("calificacion"), rs.getString("comentario"), rs.getInt("proyecto"), rs.getString("freelancer"));
    }
            
}
