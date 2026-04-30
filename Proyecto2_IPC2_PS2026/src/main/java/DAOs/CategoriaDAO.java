/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Categoria.CategoriaDB;
import Modelos.Categoria.CategoriaRequest;
import Modelos.Habilidad.HabilidadDB;
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
public class CategoriaDAO {
    
    private static final String TODAS_LAS_CATEGORIAS = "SELECT * FROM Categoria";
    private static final String CATEGORIA_POR_ID = "SELECT * FROM Categoria WHERE id_categoria = ?";
    private static final String HABILIDADES_CATEGORIA = "SELECT hab_cat.*, hab.* FROM Habilidad_Categoria hab_cat JOIN Habilidad hab ON hab_cat.habilidad = hab.id_habilidad WHERE hab_cat.categoria = ?";
    private static final String CREAR_CATEGORIA = "INSERT INTO Categoria (nombre_categoria, descripcion)";
    private static final String ACTUALIZAR_CATEGORIA = "UPDATE Categoria SET nombre_categoria = ?, descripcion = ? WHERE id_categoria = ?";
    private static final String ELIMINAR_CATEGORIA = "DELETE Categoria WHERE id_categoria = ?";
    
    public List<CategoriaDB> getCategorias() throws DataBaseException {
        List<CategoriaDB> categorias = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(TODAS_LAS_CATEGORIAS)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    categorias.add(armarCategoria(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los categorias "+e);
        }
        return categorias;
    }
    
    
    public CategoriaDB getCategoriaProyecto(int idCategoria) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(CATEGORIA_POR_ID)) {
            select.setInt(1, idCategoria);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarCategoria(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar categoria por id "+e);
        }
        return null;
    }
    
    public List<HabilidadDB> getHabilidadesCategoria(int idCategoria) throws DataBaseException {
        List<HabilidadDB> habilidades = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(HABILIDADES_CATEGORIA)) {
            select.setInt(1, idCategoria);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(armarHabilidad(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar categoria por id "+e);
        }
        return habilidades;
    }
    
    public void crearCategoria(CategoriaRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_CATEGORIA)) {
            insert.setString(1, request.getNombre_categoria());
            insert.setString(2, request.getDescripcion());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al crear categoria "+e);
        }
    }
    
    public void actualizarCategoria(CategoriaRequest request, int idCategoria) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(ACTUALIZAR_CATEGORIA)) {
            insert.setString(1, request.getNombre_categoria());
            insert.setString(2, request.getDescripcion());
            insert.setInt(3, idCategoria);
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al crear categoria "+e);
        }
    }
    
    public void eliminarCategoria(int idCategoria) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(ELIMINAR_CATEGORIA)) {
            insert.setInt(1, idCategoria);
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al eliminar categoria "+e);
        }
    }
    
    private CategoriaDB armarCategoria(ResultSet rs) throws SQLException {
        return new CategoriaDB(rs.getInt("id_categoria"), rs.getString("nombre_categoria"), rs.getString("descripcion_categoria"), rs.getBoolean("habilitado"));
    }
    
    private HabilidadDB armarHabilidad(ResultSet rs) throws SQLException {
        return new HabilidadDB(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad"), rs.getString("descripcion_habilidad"));
    }
    
}
