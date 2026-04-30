/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Habilidad.HabilidadDB;
import Modelos.Habilidad.HabilidadRequest;
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
public class HabilidadDAO {
    
    private static final String HABILIDADES_GUARDADAS = "SELECT * FROM Habilidad";
    private static final String CREAR_HABILIDAD = "INSERT INTO Habilidad (nombre_habilidad, descripcion_habilidad) VALUES (?,?)";
    private static final String ACTUALIZAR_HABILIDAD = "UPDATE Habilidad SET nombre_habilidad = ?, descripcion_habilidad = ? WHERE id_habilidad = ?";
    private static final String ELIMINAR_HABILIDAD = "DELETE Habilidad WHERE id_habilidad = ?";
    
    public List<HabilidadDB> getHabilidades() throws DataBaseException {
        List<HabilidadDB> habilidades = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(HABILIDADES_GUARDADAS)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(armarHabilidad(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los habilidades "+e);
        }
        return habilidades;
    }
    
    public void crearHabilidad(HabilidadRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_HABILIDAD)) {
            insert.setString(1, request.getNombreHabilidad());
            insert.setString(2, request.getDescripcion());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al crear habilidad "+e);
        }
    }
    
    public void actualizarHabilidad(HabilidadRequest request, int idHabilidad) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_HABILIDAD)) {
            update.setString(1, request.getNombreHabilidad());
            update.setString(2, request.getDescripcion());
            update.setInt(3, idHabilidad);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los categorias "+e);
        }
    }
    
    public void eliminarHabilidad(int idHabilidad) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_HABILIDAD)) {
            delete.setInt(1, idHabilidad);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los categorias "+e);
        }
    }
    
    public HabilidadDB armarHabilidad(ResultSet rs) throws SQLException {
        return new HabilidadDB(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad"), rs.getString("descripcion_habilidad"));
    }
    
}
