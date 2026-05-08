/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Entrega.EntregaLinkDB;
import Modelos.Entrega.EntregaLinkRequest;
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
public class EntregaLinkDAO {
    
    private static final String ENTREGAS_PROYECTO = "SELECT * FROM Entrega_Proyecto_Link WHERE proyecto = ? ORDER BY id_entrega DESC";
    private static final String AGREGAR_ENTREGA = "INSERT INTO Entrega_Proyecto_Link (descripcion, link, proyecto, freelancer) VALUES (?,?,?,?)";
    private static final String ACTUALIZAR_ESTADO_PROYECTO = "UPDATE Proyecto SET estado = 4 WHERE id_proyecto = ?";
    
    public List<EntregaLinkDB> entregasProyecto(int idProyecto) throws DataBaseException {
        List<EntregaLinkDB> entregas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection(); 
                PreparedStatement select = connection.prepareStatement(ENTREGAS_PROYECTO)) {
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    entregas.add(armarEntrega(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar entregas link "+e);
        }
        return entregas;
    }
    
    public void agregarEntrega(EntregaLinkRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection(); 
                PreparedStatement insert = connection.prepareStatement(AGREGAR_ENTREGA);
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_ESTADO_PROYECTO)) {
            insert.setString(1, request.getDescripcion());
            insert.setString(2, request.getLink());
            insert.setInt(3, request.getProyecto());
            insert.setString(4, request.getFreelancer());
            insert.executeUpdate();
            
            update.setInt(1, request.getProyecto());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al agregar entrega link "+e);
        }
    }
    
    private EntregaLinkDB armarEntrega(ResultSet rs) throws SQLException {
        return new EntregaLinkDB(rs.getInt("id_entrega"), rs.getString("descripcion"), rs.getString("link"), rs.getInt("proyecto"), rs.getString("freelancer"));
    }
    
}
