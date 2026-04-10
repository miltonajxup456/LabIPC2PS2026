/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Paquete.PaqueteRequest;
import Model.Paquete.PaqueteTuristicoDB;
import Model.Paquete.ReservacionesHechas;
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
public class PaqueteTuristicoDAO {
    
    //SELECT sp.*, paq.nombre AS nombre_paquete FROM Servicio_Paquete sp JOIN Paquete_Turistico paq ON sp.paquete = paq.id_paquete
    //SELECT sp.*, paq.nombre AS nombre_paquete, prov.nombre AS nombre_proveedor FROM Servicio_Paquete sp JOIN Paquete_Turistico paq ON sp.paquete = paq.id_paquete 
    //JOIN Proveedor prov ON sp.proveedor = prov.id_proveedor
    //SELECT paq.*, des.nombre AS nombre_destino FROM Paquete_Turistico paq JOIN Destino des ON paq.destino = des.id_destino
    private static final String ALL_PAQUETES = "SELECT paq.*, des.nombre AS nombre_destino "
            + "FROM Paquete_Turistico paq JOIN Destino des ON paq.destino = des.id_destino ORDER BY id_paquete";
    private static final String PAQUETE_POR_NOMBRE = "SELECT paq.*, des.nombre AS nombre_destino "
            + "FROM Paquete_Turistico paq JOIN Destino des ON paq.destino = des.id_destino "
            + "WHERE paq.nombre = ? ORDER BY id_paquete";
    private final String PAQUETES_DESTINO = "SELECT paq.*, des.nombre AS nombre_destino "
            + "FROM Paquete_Turistico paq "
            + "JOIN Destino des "
            + "ON paq.destino = des.id_destino "
            + "WHERE destino = ?";
    private static final String PAQUETE_POR_ID = "SELECT paq.*, des.nombre AS nombre_destino "
            + "FROM Paquete_Turistico paq "
            + "JOIN Destino des "
            + "ON paq.destino = des.id_destino "
            + "WHERE id_paquete = 1";
    private static final String INSERTAR_PAQUETE = "INSERT INTO Paquete_Turistico (nombre, duracion_dias, precio, capacidad, "
            + "descripcion, estado_paquete, destino) VALUES (?,?,?,?,?,?,?)";
    private static final String ACTUALIZAR_PAQUETE = "UPDATE Paquete_Turistico SET nombre = ?, duracion_dias = ?, precio = ?, "
            + "capacidad = ?, descripcion = ?, estado_paquete = ?, destino = ? WHERE id_paquete = ?";
    private static final String ELIMINAR_PAQUETE = "DELETE FROM Paquete_Turistico WHERE id_paquete = ?";
    private static final String PASAJEROS_PAQUETE = "SELECT paquete, SUM(cantidad_pasajeros) AS reservaciones_hechas FROM Reservacion GROUP BY paquete";
    
    public List<PaqueteTuristicoDB> getPaquetes() {
        List<PaqueteTuristicoDB> paquetes = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todos = connection.prepareStatement(ALL_PAQUETES)) {
            try (ResultSet rs = todos.executeQuery()) {
                while (rs.next()) {
                    paquetes.add(crearPaquete(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los paquetes "+e);
        }
        paquetes = agregarReservacionesHechas(paquetes);
        return paquetes;
    }
    
    public PaqueteTuristicoDB getPaquetePorId(int idPaquete) {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement paqueteId = connection.prepareStatement(PAQUETE_POR_ID)) {
            paqueteId.setInt(1, idPaquete);
            
            try (ResultSet rs = paqueteId.executeQuery()) {
                if (rs.next()) {
                    return crearPaquete(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error en Paquete Turistico por id" +e);
        }
        return null;
    }
    
    public PaqueteTuristicoDB paquetePorNombre(String nombre) {
        PaqueteTuristicoDB paquete = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement busqueda = connection.prepareStatement(PAQUETE_POR_NOMBRE)) {
            busqueda.setString(1, nombre);
            try (ResultSet rs = busqueda.executeQuery()) {
                if (rs.next()) {
                    paquete = crearPaquete(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar paquete por nombre "+e);
        }
        return paquete;
    }
    
    public List<PaqueteTuristicoDB> getPaquetesDestino(int idDestino) {
        List<PaqueteTuristicoDB> paquetes = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement paquetesDestino = connection.prepareStatement(PAQUETES_DESTINO)) {
            paquetesDestino.setInt(1, idDestino);
            
            try (ResultSet rs = paquetesDestino.executeQuery()) {
                while (rs.next()) {
                    paquetes.add(crearPaquete(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error en Destino Paquete Turistico " +e);
        }
        paquetes = agregarReservacionesHechas(paquetes);
        return paquetes;
    }
    
    public void crearPaquete(PaqueteRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement nuevo = connection.prepareStatement(INSERTAR_PAQUETE)) {
            nuevo.setString(1, request.getNombre());
            nuevo.setInt(2, request.getDuracionDias());
            nuevo.setDouble(3, request.getPrecio());
            nuevo.setInt(4, request.getCapacidad());
            nuevo.setString(5, request.getDescripcion());
            nuevo.setBoolean(6, true);
            nuevo.setInt(7, request.getIdDestino());
            
            nuevo.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al agregar paquete "+e);
        }
    }
    
    public void actualizarPaquete(PaqueteRequest request, int idPaquete) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PAQUETE)) {
            update.setString(1, request.getNombre());
            update.setInt(2, request.getDuracionDias());
            update.setDouble(3, request.getPrecio());
            update.setInt(4, request.getCapacidad());
            update.setString(5, request.getDescripcion());
            update.setBoolean(6, request.isEstadoPaquete());
            update.setInt(7, request.getIdDestino());
            update.setInt(8, idPaquete);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al actualizar paquete "+e);
        }
    }
    
    public void borrarPaquete(int idPaquete) throws EntidadNoEncontradaException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PAQUETE)) {
            delete.setInt(1, idPaquete);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al borrar paquete "+e);
        }
    }
    
    private List<PaqueteTuristicoDB> agregarReservacionesHechas(List<PaqueteTuristicoDB> paquetes) {
        List<ReservacionesHechas> reservaciones = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement update = connection.prepareStatement(PASAJEROS_PAQUETE)) {
            try (ResultSet rs = update.executeQuery()) {
                while (rs.next()) {
                    ReservacionesHechas reservacion = new ReservacionesHechas(rs.getInt("paquete"), rs.getInt("reservaciones_hechas"));
                    reservaciones.add(reservacion);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error a aggregar las reservaciones del paquete");
        }
        for (int i = 0; i < paquetes.size(); i++) {
            PaqueteTuristicoDB actual = paquetes.get(i);
            for (int j = 0; j < reservaciones.size(); j++) {
                ReservacionesHechas reservacion = reservaciones.get(j);
                if (actual.getIdPaquete() == reservacion.getPaquete()) {
                    actual.setReservacionesHechas(reservacion.getReservacionesHechas());
                }
            }
        }
        return paquetes;
    }
    
    private PaqueteTuristicoDB crearPaquete(ResultSet rs) throws SQLException {
        PaqueteTuristicoDB paquete = new PaqueteTuristicoDB(rs.getInt("id_paquete"), 
                rs.getString("nombre"), 
                rs.getInt("duracion_dias"), 
                rs.getDouble("precio"), 
                rs.getInt("capacidad"), 
                rs.getString("descripcion"), 
                rs.getBoolean("estado_paquete"), 
                rs.getInt("destino"), 
                rs.getString("nombre_destino"));
        return paquete;
    }
    
}
