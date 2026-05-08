/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Modelos.Propuestas.Propuesta;
import Modelos.Propuestas.PropuestaHabilidad;
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
public class PropuestaHabilidadDAO {
    
    private static final String PROPUESTAS_HABILDAD = "SELECT * FROM Propuesta_Habilidad WHERE aprobado = false";
    private static final String AGREGAR_PROPUESTA = "INSERT INTO Propuesta_Habilidad (nombre, descripcion) VALUES (?,?)";
    private static final String MODIFICAR_PROPUESTA = "UPDATE Propuesta_Categoria SET nombre = ?, descripcion = ? WHERE id_propuesta_habilidad = ?";
    private static final String ELIMINAR_PROPUESTA = "DELETE FROM Propuesta_Categoria WHERE id_propuesta_habilidad = ?";
    
    public List<PropuestaHabilidad> getPropuestasHabilidad() throws SQLException {
        List<PropuestaHabilidad> propuestas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(PROPUESTAS_HABILDAD)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    propuestas.add(armarPropuesta(rs));
                }
            }
        }
        return propuestas;
    }
    
    public void agregarPropuesta(Propuesta propuesta) throws SQLException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_PROPUESTA)) {
            insert.setString(1, propuesta.getNombre());
            insert.setString(2, propuesta.getDescripcion());
            insert.executeUpdate();
        }
    }
    
    public void actualizarPropuesta(Propuesta propuesta, int idPropuesta) throws SQLException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(MODIFICAR_PROPUESTA)) {
            update.setString(1, propuesta.getNombre());
            update.setString(2, propuesta.getDescripcion());
            update.setInt(1, idPropuesta);
            update.executeUpdate();
        }
    }
    
    
    public void eliminarPropuesta(int idPropuest) throws SQLException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PROPUESTA)) {
            delete.setInt(1, idPropuest);
            delete.executeUpdate();
        }
    }
    
    private PropuestaHabilidad armarPropuesta(ResultSet rs) throws SQLException {
        return new PropuestaHabilidad(rs.getInt("id_propuesta_habilidad"), rs.getString("nombre"), rs.getString("descripcion"), rs.getBoolean("aprobado"));
    }
    
    
}
