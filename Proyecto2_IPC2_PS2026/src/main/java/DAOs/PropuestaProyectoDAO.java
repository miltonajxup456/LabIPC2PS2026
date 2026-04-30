/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.PropuestaProyecto.PropuestaDB;
import Modelos.PropuestaProyecto.PropuestaRequest;
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
public class PropuestaProyectoDAO {
    
    private static final String PROPUESTAS_PROYECTO = ""
            + "SELECT prop.*, AVG(calif.calificacion) AS calificacion_promedio "
            + "FROM Propuesta_Proyecto JOIN Calificacion_Freelancer calif "
            + "ON prop.freelancer = calif.freelancer "
            + "WHERE prop.proyecto = ? "
            + "GROUP BY calif.freelancer";
    private static final String PROPUESTA_FREELANCER = ""
            + "SELECT prop.*, AVG(calif.calificacion) AS calificacion_promedio "
            + "FROM Propuesta_Proyecto JOIN Calificacion_Freelancer calif "
            + "ON prop.freelancer = calif.freelancer "
            + "WHERE prop.proyecto = ? AND freelancer = ? "
            + "GROUP BY calif.freelancer";
    private static final String CREAR_PROPUESTA = "INSERT INTO Propuesta_Proyecto (presentacion, monto_ofertado, plazo_entrega_propuesto, freelancer, proyecto)";
    private static final String ACTUALIZAR_PROPUESTA = "UPDATE Propuesta_Proyecto SET presentacion = ?, monto_ofertado = ?, plazo_entrega_proyecto = ? WHERE id_propuesta = ?";
    private static final String ELIMINAR_PROPUESTA = "DELETE Propuesta_Proyecto WHERE id_propuesta = ?";
    
    public List<PropuestaDB> getPropuestasProyecto(int idProyecto) throws DataBaseException {
        List<PropuestaDB> propuestas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PROPUESTAS_PROYECTO)) {
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    propuestas.add(armarPropuesta(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar propuestas Proyecto "+e);
        }
        return propuestas;
    }
    
    
    public PropuestaDB getPropuestaFrelancer(int idProyecto, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PROPUESTA_FREELANCER)) {
            select.setInt(1, idProyecto);
            select.setString(2, idFreelancer);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarPropuesta(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar propuesta Freelancer "+e);
        }
        return null;
    }
    
    public void actualizarPropuesta(PropuestaRequest request, int idPropuesta) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PROPUESTA)) {
            update.setString(1, request.getPresentacion());
            update.setDouble(2, request.getMontoOfertado());
            update.setInt(3, request.getPlazoEntrega());
            update.setInt(4, idPropuesta);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al actualizar propuesta "+e);
        }
    }
    
    public void eliminarPropuesta(int idPropuesta) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PROPUESTA)) {
            delete.setInt(1, idPropuesta);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al eliminar propuesta");
        }
    }
    
    public void crearPropuesta(PropuestaRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_PROPUESTA)) {
            insert.setString(1, request.getPresentacion());
            insert.setDouble(2, request.getMontoOfertado());
            insert.setInt(3, request.getPlazoEntrega());
            insert.setInt(4, request.getFreelancer());
            insert.setInt(5, request.getProyecto());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al crear propuesta "+e);
        }
    }
    
    private PropuestaDB armarPropuesta(ResultSet rs) throws SQLException {
        return new PropuestaDB(
                rs.getInt("id_propuesta"), 
                rs.getString("presentacion"), 
                rs.getDouble("monto_ofertado"), 
                rs.getInt("plazo_entrega_propuesto"), 
                rs.getInt("freelancer"), 
                rs.getInt("proyecto"), 
                rs.getDouble("calificacion_promedio"));
    }
    
}
