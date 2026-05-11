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
    
    private static final String PROPUESTAS_ENVIADAS = ""
            + "SELECT pro.*, es.tipo_estado FROM Propuesta_Proyecto pro "
            + "JOIN Estado_Propuesta_Proyecto es ON pro.estado = es.id_estado "
            + "WHERE pro.fecha BETWEEN ? AND ? AND pro.freelancer = ? ";
    private static final String PARTE_PROPUESTAS = "SELECT pro.*, es.tipo_estado FROM Propuesta_Proyecto pro JOIN Estado_Propuesta_Proyecto es ON pro.estado = es.id_estado WHERE proyecto = ?";
    private static final String PARTE_PROPUESTA_FREELANCER = "SELECT pro.*, es.tipo_estado FROM Propuesta_Proyecto pro JOIN Estado_Propuesta_Proyecto es ON pro.estado = es.id_estado WHERE proyecto = ? AND pro.freelancer = ?";
    private static final String PARTE_CALIFICACION = "SELECT COALESCE(AVG(calificacion), 0) AS calificacion_promedio FROM Calificacion_Freelancer WHERE freelancer = ?";
    private static final String CREAR_PROPUESTA = "INSERT INTO Propuesta_Proyecto (presentacion, monto_ofertado, plazo_entrega_propuesto, freelancer, proyecto) VALUES (?,?,?,?,?)";
    private static final String ACTUALIZAR_PROPUESTA = "UPDATE Propuesta_Proyecto SET presentacion = ?, monto_ofertado = ?, plazo_entrega_propuesto = ? WHERE id_propuesta = ?";
    private static final String RECHAZAR_PROPUESTA = "UPDATE Propuesta_Proyecto SET estado = 3 WHERE id_propuesta = ?";
    private static final String ELIMINAR_PROPUESTA = "DELETE FROM Propuesta_Proyecto WHERE id_propuesta = ?";
    
    public List<PropuestaDB> getPropuestasEnviadas(String fechaInicial, String fechaFinal, String idFreelancer) throws DataBaseException {
        List<PropuestaDB> propuestas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PROPUESTAS_ENVIADAS)) {
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            select.setString(3, idFreelancer);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    propuestas.add(armarPropuesta(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar propuestas enviadas "+e.getMessage());
        }
        return propuestas;
    }
    
    public List<PropuestaDB> getPropuestasProyecto(int idProyecto) throws DataBaseException {
        List<PropuestaDB> propuestas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PARTE_PROPUESTAS);
                PreparedStatement promedio = connection.prepareStatement(PARTE_CALIFICACION)){
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    PropuestaDB propuesta = armarPropuesta(rs);
                    promedio.setString(1, propuesta.getFreelancer());
                    try (ResultSet rsProm = promedio.executeQuery()) {
                        if (rsProm.next()) {
                            propuesta.setCalificacionPromedio(rsProm.getDouble("calificacion_promedio"));
                        }
                    }
                    propuestas.add(propuesta);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar propuestas Proyecto "+e);
        }
        return propuestas;
    }
    
    
    public PropuestaDB getPropuestaFrelancer(int idProyecto, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PARTE_PROPUESTA_FREELANCER);
                PreparedStatement promedio = connection.prepareStatement(PARTE_CALIFICACION)) {
            select.setInt(1, idProyecto);
            select.setString(2, idFreelancer);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    PropuestaDB propuesta = armarPropuesta(rs);
                    promedio.setString(1, idFreelancer);
                    try (ResultSet rsProm = promedio.executeQuery()) {
                        if (rsProm.next()) {
                            propuesta.setCalificacionPromedio(rsProm.getDouble("calificacion_promedio"));
                        }
                    }
                    return propuesta;
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
    
    public void rechazarPropuesta(int idPropuesta) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(RECHAZAR_PROPUESTA)) {
            update.setInt(1, idPropuesta);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al rechazar propuesta "+e);
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
            insert.setString(4, request.getFreelancer());
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
                rs.getString("fecha"),
                rs.getString("freelancer"), 
                rs.getInt("proyecto"), 
                rs.getInt("estado"), 
                rs.getString("tipo_estado"));
    }
    
}
