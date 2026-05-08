/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Comision.ComisionDB;
import Modelos.Proyecto.PaqueteProyecto;
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
    
    //SELECT proy.*, estado.id_estado, estado.tipo_estado, cat.nombre_categoria FROM Proyecto proy JOIN Estado_Proyecto estado ON proy.estado = estado.id_estado JOIN Categoria cat ON proy.categoria = cat.id_categoria;
    private static final String BUSCAR_PROYECTOS = ""
            + "SELECT proy.*, estado.tipo_estado, cat.nombre_categoria "
            + "FROM Proyecto proy JOIN Estado_Proyecto estado ON proy.estado = estado.id_estado "
            + "JOIN Categoria cat ON proy.categoria = cat.id_categoria";
    private static final String BUSCAR_PROYECTO_CLIENTE = ""
            + "SELECT proy.*, estado.tipo_estado, cat.nombre_categoria "
            + "FROM Proyecto proy JOIN Estado_Proyecto estado ON proy.estado = estado.id_estado "
            + "JOIN Categoria cat ON proy.categoria = cat.id_categoria WHERE proy.cliente = ?";
    private static final String BUSCAR_PROYECTO_FREELANCER = ""
            + "SELECT proy.*, estado.tipo_estado, cat.nombre_categoria "
            + "FROM Proyecto proy JOIN Estado_Proyecto estado ON proy.estado = estado.id_estado "
            + "JOIN Categoria cat ON proy.categoria = cat.id_categoria WHERE proy.freelancer = ?";
    private static final String BUSCAR_PROYECTO_ID = ""
            + "SELECT proy.*, estado.tipo_estado, cat.nombre_categoria "
            + "FROM Proyecto proy JOIN Estado_Proyecto estado ON proy.estado = estado.id_estado "
            + "JOIN Categoria cat ON proy.categoria = cat.id_categoria WHERE proy.id_proyecto = ?";
    private static final String CREAR_PROYECTO = "INSERT INTO Proyecto (titulo, descripcion, presupuesto, fecha_limite, cliente, categoria, estado, comision) VALUES (?,?,?,?,?,?,?,?)";
    private static final String ACTUALIZAR_PROYECTO = "UPDATE Proyecto SET titulo = ?, descripcion = ?, presupuesto = ?, fecha_limite = ?, categoria = ? WHERE id_proyecto = ?";
    private static final String ACEPTAR_PROPUESTA = "UPDATE Proyecto SET estado = ?, freelancer = ? WHERE id_proyecto = ?";
    private static final String ACTUALIZAR_PROPUESTA = "UPDATE Propuesta_Proyecto SET estado = 2 WHERE proyecto = ? AND freelancer = ?";
    private static final String ACTUALIZAR_ESTADO_PROYECTO = "UPDATE Proyecto SET estado = ? WHERE id_proyecto = ?";
    private static final String ELIMINAR_PROYECTO = "DELETE FROM Proyecto WHERE id_proyecto = ?";
    
    public List<ProyectoDB> getTodosLosProyectos() throws DataBaseException {
        List<ProyectoDB> proyectos = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_PROYECTOS)) {
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
    
    public List<ProyectoDB> getProyectosFreelancer(String freelancer) throws DataBaseException {
        List<ProyectoDB> proyectos = new ArrayList<>();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_PROYECTO_FREELANCER)) {
            select.setString(1, freelancer);
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
    
    public ProyectoDB getProyectoPorId(int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(BUSCAR_PROYECTO_ID)) {
            select.setInt(1, idProyecto);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    return armarProyecto(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error al buscar todos los proyectos "+e);
        }
        return null;
    }
    
    public void crearProyecto(ProyectoRequest request) throws DataBaseException {
        ComisionDAO daoCom = new ComisionDAO();
        ComisionDB comision = daoCom.getUltimaComision();
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_PROYECTO)) {
            insert.setString(1, request.getTitulo());
            insert.setString(2, request.getDescripcion());
            insert.setDouble(3, request.getPresupuesto());
            insert.setString(4, request.getFechaLimite());
            insert.setString(5, request.getCliente());
            insert.setInt(6, request.getCategoria());
            insert.setInt(7, 1);
            insert.setInt(8, comision.getIdComision());
            
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
            update.setInt(5, request.getCategoria());
            update.setInt(6, idProyecto);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al acualizar Proyecto "+e);
        }
    }
    
    public void aceptarPropuesta(ProyectoRequest request, int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACEPTAR_PROPUESTA); 
                PreparedStatement update2 = connection.prepareStatement(ACTUALIZAR_PROPUESTA)) {
            update.setInt(1, request.getEstado());
            update.setString(2, request.getFreelancer());
            update.setInt(3, idProyecto);
            update.executeUpdate();
            
            update2.setInt(1, idProyecto);
            update2.setString(2, request.getFreelancer());
            update2.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al aceptar propuesta Proyecto "+e);
        }
    }
    
    public void actualizarEstadoProyecto(int estado, int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_ESTADO_PROYECTO)) {
            update.setInt(1, estado);
            update.setInt(2, idProyecto);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al cancelar propuesta Proyecto "+e);
        }
    }
    
    public void eliminarProyecto(int idProyecto) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PROYECTO)) {
            delete.setInt(1, idProyecto);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al eliminar Proyecto "+e);
        }
    }
    
    public void getPaquetesProyecto(String freelancer) throws DataBaseException {
        List<PaqueteProyecto> paquetes = new ArrayList<>();
        
    }
    
    private ProyectoDB armarProyecto(ResultSet rs) throws SQLException {
        return new ProyectoDB(rs.getInt("id_proyecto"), rs.getString("titulo"), rs.getString("descripcion"), rs.getDouble("presupuesto"), 
                rs.getString("fecha_limite"), rs.getString("cliente"), rs.getInt("categoria"), 
                rs.getString("nombre_categoria"), rs.getInt("estado"), rs.getString("tipo_estado"), rs.getString("freelancer"), rs.getInt("comision"));
    }
    
}
