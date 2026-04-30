/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Recarga.RecargaDB;
import Modelos.Recarga.RecargaRequest;
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
public class RecargaDAO {
    
    private static final String TODOS_LOS_REGISTROS = "SELECT * FROM Recarga";
    private static final String RECARGAS_POR_CLIENTE = "SELECT * FROM Recarga WHERE cliente = ?";
    private static final String AGREGAR_REGISTRO_RECARGA = "INSERT INTO Recarga (monto, cliente) VALUES (?,?)";
    
    public List<RecargaDB> getTodosLosRegistros() throws DataBaseException {
        List<RecargaDB> recargas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(TODOS_LOS_REGISTROS)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    recargas.add(armarRecarga(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer registros recarga "+e);
        }
        return recargas;
    }
    
    
    public List<RecargaDB> getRegistrosCliente(String idCliente) throws DataBaseException {
        List<RecargaDB> recargas = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(RECARGAS_POR_CLIENTE)) {
            select.setString(1, idCliente);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    recargas.add(armarRecarga(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer registros recarga cliente "+e);
        }
        return recargas;
    }
    
    public void agregarRegistro(RecargaRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(AGREGAR_REGISTRO_RECARGA)) {
            select.setDouble(1, request.getMonto());
            select.setString(2, request.getCliente());
            
            select.executeUpdate(); 
        } catch (SQLException e) {
            throw new DataBaseException("Error al traer registros recarga cliente "+e);
        }
    }
    
    private RecargaDB armarRecarga(ResultSet rs) throws SQLException {
        return new RecargaDB (rs.getInt("id_recarga"), rs.getDouble("monto"), rs.getString("fecha"), rs.getString("cliente"));
    }
    
}
