/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Model.Destino.DestinoDB;
import Model.Destino.DestinoRequest;
import Model.Login.DestinoPaqueteRequest;
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
public class DestinoDAO {
    
    private final String ALL_DESTINOS = "SELECT * FROM Destino";
    private final String DESTINO_ID = "SELECT * FROM Destino WHERE id_destino = ?";
    private final String DESTINO_POR_NOMBRE = "SELECT * FROM Destino WHERE nombre = ?";
    private final String CREAR_DESTINO = "INSERT INTO Destino (nombre, pais, descripcion, clima_epoca, url_imagen) VALUES (?,?,?,?,?)";
    private final String BORRAR_DESTINO = "DELETE FROM Destino WHERE id_destino = ?";
    private final String ACTUALIZAR_DESTINO = "UPDATE Destino SET nombre = ?, pais = ?, descripcion = ?, clima_epoca = ?, url_imagen = ? WHERE id_destino = ?";
    
    public List<DestinoDB> getAllDestinos() {
        
        List<DestinoDB> destinos = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todos = connection.prepareStatement(ALL_DESTINOS)) {
            try (ResultSet rs = todos.executeQuery()) {
                while (rs.next()) {
                    destinos.add(crearDestino(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en Destino Base de Datos "+e);
        }
        return destinos;
    }
    
    public void actualizarDestino(int idDestino, DestinoRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_DESTINO)) {
            update.setString(1, request.getNombre());
            update.setString(2, request.getPais());
            update.setString(3, request.getDescripcion());
            update.setString(4, request.getClimaEpoca());
            update.setString(5, request.getUrl());
            update.setInt(6, idDestino);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al acualizar Destino"+e);
        }
    }
    
    public DestinoDB getDestinoPorID(DestinoPaqueteRequest requets) {
        DestinoDB destino = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement busqueda = connection.prepareStatement(DESTINO_ID)) {
            busqueda.setInt(1, requets.getIdDestino());
            
            try (ResultSet rs = busqueda.executeQuery()) {
                if (rs.next()) {
                    destino = crearDestino(rs);
                }
             }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error en DestinoID "+e);
        }
        return destino;
    }
    
    public DestinoDB existeNombreDestino(String nombre) {
        DestinoDB destino = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement busqueda = connection.prepareStatement(DESTINO_POR_NOMBRE)) {
            busqueda.setString(1, nombre);
            try (ResultSet rs = busqueda.executeQuery()) {
                if (rs.next()) {
                    destino = crearDestino(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar nombre de Destino "+e);
        }
        return destino;
    }
    
    public void crearDestino(DestinoRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_DESTINO)) {
            insert.setString(1, request.getNombre());
            insert.setString(2, request.getPais());
            insert.setString(3, request.getDescripcion());
            insert.setString(4, request.getClimaEpoca());
            insert.setString(5, request.getUrl());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            System.out.println();
            throw new DatosInvalidosException("Error al guardar destino "+e);
        }
    }
    
    public void borrarDestino(DestinoDB destino) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(BORRAR_DESTINO)) {
            delete.setInt(1, destino.getIdDestino());
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Ocurrio un error al borrar destino "+e);
        }
    }
    
    private DestinoDB crearDestino(ResultSet rs) throws SQLException {
        DestinoDB destino = new DestinoDB(rs.getInt("id_destino"), 
                rs.getString("nombre"), 
                rs.getString("pais"), 
                rs.getString("descripcion"), 
                rs.getString("clima_epoca"), 
                rs.getString("url_imagen"));
        return destino;
    }
    
}
