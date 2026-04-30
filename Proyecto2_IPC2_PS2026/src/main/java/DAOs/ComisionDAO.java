/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Comision.ComisionDB;
import Modelos.Comision.ComisionRequest;
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
public class ComisionDAO {
    
    private static final String ULTIMA_COMISION = "SELECT * FROM Comision ORDER BY id_historial DESC LIMIT 1";
    private static final String HISTORIAL_COMISION = "SELECT * FROM Comision ORDER BY id_historial DESC";
    private static final String COMISION_POR_ID = "SELECT * FROM Comision WHERE id_comision = ?";
    private static final String AGREGAR_COMISION = "INSERT INTO Comision (porcentaje) VALUES (?)";
    private static final String ACTUALIZAR_COMISION = "UPDATE Comision SET fecha_final = CURRENT_TIMESTAMP WHERE id_comision = ?";
    
    public ComisionDB getUltimaComision() throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(ULTIMA_COMISION)) {
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarComision(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar ultima comision "+e);
        }
        return null;
    }
    
    public List<ComisionDB> getComisiones() throws DataBaseException {
        List<ComisionDB> historial = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(HISTORIAL_COMISION)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    historial.add(armarComision(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer historial comisiones "+e);
        }
        return historial;
    }
    
    public void agregarComision(ComisionRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_COMISION, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insert.setInt(1, request.getPorcentaje());
            
            insert.executeUpdate();
            try (ResultSet rs = insert.getGeneratedKeys()) {
                if (rs.next()) {
                    int ultimoId = rs.getInt(1);
                    ComisionDB comision = null;
                    while (comision == null) {
                        int anterior = ultimoId - 1;
                        comision = comisionPorId(anterior);
                        if (comision != null) {
                            actualizarComision(anterior);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar comision "+e);
        }
    }
    
    private ComisionDB comisionPorId(int idComision) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(AGREGAR_COMISION, PreparedStatement.RETURN_GENERATED_KEYS)) {
            select.setInt(1, idComision);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarComision(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar comision "+e);
        }
        return null;
    }
    
    private void actualizarComision(int idComision) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(ACTUALIZAR_COMISION)) {
            select.setInt(1, idComision);
            
            select.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al actualizar comision "+e);
        }
    }
    
    private ComisionDB armarComision(ResultSet rs) throws SQLException {
        return new ComisionDB(rs.getInt("id_comision"), rs.getInt("porcentaje"), rs.getString("fecha_inicio"), rs.getString("fecha_final"));
    }
    
}
