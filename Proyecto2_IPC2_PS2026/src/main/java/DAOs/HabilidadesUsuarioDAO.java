/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author millin-115
 */
public class HabilidadesUsuarioDAO {
    
    private static final String HABILIDADES_FREELANCER = "SELECT * FROM Habilidad_Freelancer WHERE freelancer = ?";
    private static final String AGREGAR_HABILIDAD = "INSERT INTO Habilidad_Freelancer (habilidad, freelancer) VALUES (?,?)";
    private static final String ELIMINAR_HABILIDAD_FREELANCER = "DELETE FROM Habilidad_Freelancer WHERE habilidad = ? AND freelancer = ?";
    
    public List<Integer> getHabilidadesFreelancer(String idFreelancer) throws DataBaseException {
        List<Integer> habilidades = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(HABILIDADES_FREELANCER)) {
            select.setString(1, idFreelancer);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(rs.getInt("habilidad"));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer habilidades de freelancer "+e);
        }
        return habilidades;
    }
    
    public void agregarHabilidades(List<Integer> lista, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_HABILIDAD)) {
            for (int i = 0; i < lista.size(); i++) {
                int idHabilidad = lista.get(i);
                insert.setInt(1, idHabilidad);
                insert.setString(2, idFreelancer);
                
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar habilidades a freelancer "+e);
        }
    }
    
    public void eliminarHabilidad(List<Integer> lista, String idFreelancer) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_HABILIDAD_FREELANCER)) {
            for (int i = 0; i < lista.size(); i++) {
                delete.setInt(1, lista.get(i));
                delete.setString(2, idFreelancer);

                delete.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al eliminar habilidad de Freelancer "+e);
        }
    }
    
}
