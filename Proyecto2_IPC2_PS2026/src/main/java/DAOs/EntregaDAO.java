/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Entrega.EntregaDB;
import Modelos.Entrega.EntregaRequest;
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
public class EntregaDAO {
    
    private static final String ENTREGAS_PROYECTO = "SELECT * FROM Entrega_Proyecto WHERE proyecto = ? ORDER BY id_entrega DESC";
    private static final String ENTREGA_POR_ID = "SELECT * FROM Entrega_Proyecto WHERE id_entrega = ?";
    private static final String AGREGAR_ENTREGA = "INSERT INTO Entrega_Proyecto (descripcion, proyecto, freelancer) VALUES (?,?,?)";
    private static final String ACTUALIZAR_ENTREGA = "UPDATE Entrega_Proyecto SET path_archivo = ? WHERE id_entrega = ?";
    private static final String ACTUALIZAR_ESTADO_PROYECTO = "UPDATE Proyecto SET estado = 4 WHERE id_proyecto = ?";
    private static final String ELIMINAR_ENTREGA = "DELETE FROM Entrega_Proyecto WHERE id_entrega = ?";
    
    public List<EntregaDB> getEntregasProyecto(int idProyecto) throws DataBaseException {
        List<EntregaDB> entregas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(ENTREGAS_PROYECTO)) {
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    entregas.add(armarEntrega(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error en agregar entrega "+e);
        }
        return entregas;
    }
    
    public EntregaDB entregaPorId(int idEntrega) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(ENTREGA_POR_ID)) {
            select.setInt(1, idEntrega);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarEntrega(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer entrega por id "+e);
        }
        return null;
    }
    
    public EntregaDB entregarProyecto(EntregaRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_ENTREGA, PreparedStatement.RETURN_GENERATED_KEYS); 
                PreparedStatement select = connection.prepareStatement(ENTREGA_POR_ID)) {
            insert.setString(1, request.getDescripcion());
            insert.setInt(2, request.getProyecto());
            insert.setString(3, request.getFreelancer());
            
            insert.executeUpdate();
            try (ResultSet rs = insert.getGeneratedKeys()) {
                if (rs.next()) {
                    int idEntrega = rs.getInt(1);
                    select.setInt(1, idEntrega);
                    try (ResultSet rsEntrega = select.executeQuery()) {
                        if (rsEntrega.next()) {
                            return armarEntrega(rsEntrega);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error en agregar entrega "+e);
        }
        return null;
    }
    
    public void actualizarEntrega(String path, int idEntrega, int idProyecto) {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_ENTREGA);
                PreparedStatement updateProy = connection.prepareStatement(ACTUALIZAR_ESTADO_PROYECTO)) {
            update.setString(1, path);
            update.setInt(2, idEntrega);
            
            update.executeUpdate();
            updateProy.setInt(1, idProyecto);
            updateProy.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void eliminarEntrega(int idEntrega) {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_ENTREGA)) {
            delete.setInt(1, idEntrega);
            delete.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private EntregaDB armarEntrega(ResultSet rs) throws SQLException {
        return new EntregaDB(rs.getInt("id_entrega"), 
                rs.getString("descripcion"), 
                rs.getString("path_archivo"), 
                rs.getInt("proyecto"), 
                rs.getString("freelancer"));
    }
    
}
