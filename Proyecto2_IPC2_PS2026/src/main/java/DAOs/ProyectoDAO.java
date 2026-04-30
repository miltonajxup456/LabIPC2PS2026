/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Proyecto.ProyectoDB;
import Modelos.Proyecto.ProyectoRequest;
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
public class ProyectoDAO {
    
    private static final String BUSCAR_PROYECTO = "SELECT proy.*, estado.id_estado, estado.tipo_estado FROM Proyecto proy JOIN Estado_Proyecto estado";
    private static final String BUSCAR_PROYECTO_CLIENTE = "SELECT proy.*, estado.id_estado, estado.tipo_estado FROM Proyecto proy JOIN Estado_Proyecto estado WHERE proy.id_proyecto = ?";
    private static final String CREAR_PROYECTO = "INSERT INTO Proyecto (titulo, descripcion, presupuesto, fecha_limite, cliente, categoria, estado) VALUES (?,?,?,?,?,?,?)";
    private static final String ACTUALIZAR_PROYECTO = "UPDATE Proyecto SET titulo = ?, descripcion = ?, presupuesto = ?, fecha_limite = ?, cliente = ?, categoria = ?, estado = ? WHERE id_proyecto = ?";
    private static final String ELIMINAR_PROYECTO = "DELETE Proyecto WHERE id_proyecto = ?";
    
    public List<ProyectoDB> getTodosLosProyectos() throws DataBaseException {
        List<ProyectoDB> proyectos = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_PROYECTO)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    proyectos.add(armarProyecto(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los proyectos "+e);
        }
        return proyectos;
    }
    
    
    public List<ProyectoDB> getProyectosCliente(String cliente) throws DataBaseException {
        List<ProyectoDB> proyectos = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_PROYECTO_CLIENTE)) {
            select.setString(1, cliente);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    proyectos.add(armarProyecto(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los proyectos "+e);
        }
        return proyectos;
    }
    
    public void crearProyecto(ProyectoRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_PROYECTO)) {
            insert.setString(1, request.getTitulo());
            insert.setString(2, request.getDescripcion());
            insert.setDouble(3, request.getPresupuesto());
            insert.setString(4, request.getFechaLimite());
            insert.setString(5, request.getCliente());
            insert.setInt(6, request.getCategoria());
            insert.setInt(7, 1);
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al crear proyecto "+e);
        }
    }
    
    public void actualizarProyecto(ProyectoRequest request, int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PROYECTO)) {
            update.setString(1, request.getTitulo());
            update.setString(2, request.getDescripcion());
            update.setDouble(3, request.getPresupuesto());
            update.setString(4, request.getFechaLimite());
            update.setString(5, request.getCliente());
            update.setInt(6, request.getCategoria());
            update.setInt(7, request.getEstado());
            update.setInt(8, idProyecto);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al acualizar Proyecto "+e);
        }
    }
    
    
    public void eliminarProyecto(int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PROYECTO)) {
            delete.setInt(1, idProyecto);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al acualizar Proyecto "+e);
        }
    }
    
    private ProyectoDB armarProyecto(ResultSet rs) throws SQLException {
        return new ProyectoDB(rs.getInt("id_proyecto"), rs.getString("titulo"), rs.getString("descripcion"), rs.getDouble("presupuesto"), 
                rs.getString("fecha_limite"), rs.getString("cliente"), rs.getInt("categoria"), 
                rs.getString("tipo_categoria"), rs.getInt("estado"), rs.getString("tipo_estado"));
    }
    
}
