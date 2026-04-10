/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Reservacion.ReservacionDB;
import Model.Reservacion.ReservacionRequest;
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
public class ReservacionDAO {
    /*
    SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado FROM Reservacion rsv JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete LEFT JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion GROUP BY numero_reservacion;
    
    SELECT rsv.*, cli.nombre AS nombre_cliente FROM Reservacion rsv JOIN Pasajeros_Reservacion pasrsv ON rsv.numero_reservacion = pasrsv.id_reservacion JOIN Cliente cli ON pasrsv.id_pasajero = cli.dpi WHERE cli.dpi = "1234567890123";"
    
    SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado FROM Reservacion rsv JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete LEFT JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion JOIN Destino des ON paq.destino = des.id_destino WHERE des.id_destino = 1 AND fecha_viaje = "2026-03-26" GROUP BY numero_reservacion
    
    SELECT rsv.*, paq.nombre FROM Reservacion rsv JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete WHERE estado = 2 AND fecha_viaje BETWEEN "2026-03-25" AND "2026-03-30" ORDER BY fecha_viaje;
    
    SELECT agente_de_registro, COUNT(agente_de_registro) AS reservaciones_hechas, GROUP_CONCAT(numero_reservacion) AS numero_reservacion FROM Reservacion WHERE fecha_viaje BETWEEN "2026-02-01" AND "2026-03-30" GROUP BY agente_de_registro ORDER BY reservaciones_hechas DESC LIMIT 1;
    */
    
    private static final String RESERVACIONES_PAQUETE_PAGO = "SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado "
            + "FROM Reservacion rsv "
            + "JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete LEFT "
            + "JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion "
            + "GROUP BY numero_reservacion";
    private static final String RESERVACION_POR_ID = "SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado "
            + "FROM Reservacion rsv "
            + "JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete LEFT "
            + "JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion "
            + "WHERE numero_reservacion = ?";
    private static final String RESERVACION_POR_DPI = "SELECT rsv.*, paq.nombre AS nombre_paquete, cli.nombre AS nombre_cliente "
            + "FROM Reservacion rsv JOIN Paquete_Turistico paq ON paq.id_paquete = rsv.paquete "
            + "JOIN Pasajeros_Reservacion pasrsv ON rsv.numero_reservacion = pasrsv.id_reservacion "
            + "JOIN Cliente cli ON pasrsv.id_pasajero = cli.dpi WHERE cli.dpi = ?";
    private static final String RESERVACION_POR_FECHA = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado "
            + "FROM Reservacion rsv JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "LEFT JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion "
            + "JOIN Destino des ON paq.destino = des.id_destino "
            + "WHERE fecha_viaje = ? "
            + "GROUP BY numero_reservacion";
    private static final String RESERVACION_POR_FECHA_DESTINO = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado "
            + "FROM Reservacion rsv JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "LEFT JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion "
            + "JOIN Destino des ON paq.destino = des.id_destino "
            + "WHERE des.id_destino = ? AND fecha_viaje = ? "
            + "GROUP BY numero_reservacion";
    
    private static final String CREAR_RESERVACION = "INSERT INTO Reservacion (fecha_viaje, cantidad_pasajeros, costo, agente_de_registro, paquete, estado) "
            + "VALUES (?,?,?,?,?,?)";
    private static final String ACTUALIZAR_RESERVACION = "UPDATE Reservacion SET estado = ? WHERE numero_reservacion = ?";
    private static final String BORRAR_RESERVACION = "DELETE FROM Reservacion WHERE numero_reservacion = ?";
    private static final String PASAJEROS_RESERVACION = "SELECT id_pasajero FROM Pasajeros_Reservacion WHERE id_reservacion = ?";
    private static final String GUARDAR_PASAJEROS_RESERVACION = "INSERT INTO Pasajeros_Reservacion (id_reservacion, id_pasajero) VALUES (?,?)";
    
    public List<ReservacionDB> getReservaciones() {
        List<ReservacionDB> reservaciones = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement todos = connection.prepareStatement(RESERVACIONES_PAQUETE_PAGO)) {
            try (ResultSet rs = todos.executeQuery()) {
                while (rs.next()) {
                    reservaciones.add(armarReservacion(connection, rs));
                }
            } 
        } catch (SQLException e) {
            System.out.println("Ocurrio un problema con todas las reservaciones "+e);
        }
        return reservaciones;
    }
    
    public List<ReservacionDB> getReservacionesPorDPI(String dpi) throws EntidadNoEncontradaException {
        List<ReservacionDB> historial = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement reservPordpi = connection.prepareStatement(RESERVACION_POR_DPI)) {
            reservPordpi.setString(1, dpi);
            try (ResultSet rs = reservPordpi.executeQuery()) {
                while (rs.next()) {
                    historial.add(armarHistorialReservaciones(connection, rs));
                }
            } 
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error con reservacion por DPI "+e);
        }
        return historial;
    }
    
    public List<ReservacionDB> getReservacionesFechaDestino(String fecha, int idDestino) throws EntidadNoEncontradaException {
        List<ReservacionDB> historial = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement unico = connection.prepareStatement(RESERVACION_POR_FECHA_DESTINO)) {
            unico.setInt(1, idDestino);
            unico.setString(2, fecha);
            try (ResultSet rs = unico.executeQuery()) {
                while (rs.next()) {
                    historial.add(armarReservacion(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error con reservaciones por fecha-destino "+e);
        }
        return historial;
    }
    
    public List<ReservacionDB> getReservacionesFecha(String fecha) throws EntidadNoEncontradaException {
        List<ReservacionDB> reservaciones = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement unico = connection.prepareStatement(RESERVACION_POR_FECHA)) {
            unico.setString(1, fecha);
            try (ResultSet rs = unico.executeQuery()) {
                while (rs.next()) {
                    reservaciones.add(armarReservacion(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error con reservaciones por fecha "+e);
        }
        return reservaciones;
    }
    
    public ReservacionDB reservacionPorId(int numReservacion) {
        ReservacionDB reservacion = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement unico = connection.prepareStatement(RESERVACION_POR_ID)) {
            unico.setInt(1, numReservacion);
            try (ResultSet rs = unico.executeQuery()) {
                if (rs.next()) {
                    reservacion = armarReservacion(connection, rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la reservacion por ID "+e);
        }
        return reservacion;
    }
    
    
    
    public void crearReservacion(ReservacionRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement insert = connection.prepareStatement(CREAR_RESERVACION, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insert.setString(1, request.getFechaViaje());
                insert.setInt(2, request.getCantPasajeros());
                insert.setDouble(3, request.getCosto());
                insert.setInt(4, request.getAgenteDeRegistro());
                insert.setInt(5, request.getPaquete());
                insert.setInt(6, 1);

                insert.executeUpdate();

                try (ResultSet rs = insert.getGeneratedKeys()) {
                    if (rs.next()) {
                        int numeroReservacion = rs.getInt(1);
                        for (int i = 0; i < request.getDpiPasajeros().size(); i++) {
                            guardarPasajero(connection, numeroReservacion, request.getDpiPasajeros().get(i));
                        }
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DatosInvalidosException("Error al guardar al pasajero "+e);
            }
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al guardar reservacion "+e);
        }
    }
    
    public void actualizarReservacion(ReservacionRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement actualizar = connection.prepareStatement(ACTUALIZAR_RESERVACION)) {
            actualizar.setInt(1, request.getEstado());
            actualizar.setInt(2, request.getNumReservacion());
            
            actualizar.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al actualizar reservacion "+e);
        }
    }
    
    public void borrarReservacion(int idReservacion) throws EntidadNoEncontradaException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement delete = connection.prepareStatement(BORRAR_RESERVACION)) {
            delete.setInt(1, idReservacion);
            
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al borrar reservacion "+e);
        }
    }
    
    public ReservacionDB armarReservacion(Connection connection, ResultSet rs) throws SQLException {
        ReservacionDB reservacion = new ReservacionDB(rs.getInt("numero_reservacion"), 
                rs.getString("fecha_viaje"), 
                rs.getString("fecha_creacion"), 
                rs.getInt("cantidad_pasajeros"), 
                rs.getDouble("costo"), 
                rs.getInt("agente_de_registro"), 
                rs.getInt("paquete"), 
                rs.getInt("estado"), 
                rs.getDouble("dinero_pagado"), 
                rs.getString("nombre_paquete"));
        reservacion.setDpiPasajeros(getPasajeros(connection, reservacion.getNumReservacion()));
        return reservacion;
    }
    
    private ReservacionDB armarHistorialReservaciones(Connection connection, ResultSet rs) throws SQLException {
        ReservacionDB reservacion = new ReservacionDB(rs.getInt("numero_reservacion"), 
                rs.getString("fecha_viaje"), 
                rs.getString("fecha_creacion"), 
                rs.getInt("cantidad_pasajeros"), 
                rs.getDouble("costo"), 
                rs.getInt("agente_de_registro"), 
                rs.getInt("paquete"), 
                rs.getInt("estado"),
                rs.getString("nombre_paquete"), 
                rs.getString("nombre_cliente"));
        reservacion.setDpiPasajeros(getPasajeros(connection, reservacion.getNumReservacion()));
        return reservacion;
    }
    
    public List<String> getPasajeros(Connection connection, int numeroReservacion) {
        List<String> pasajerosReservacion = new ArrayList<>();
        try (PreparedStatement pasajeros = connection.prepareStatement(PASAJEROS_RESERVACION)) {
            pasajeros.setInt(1, numeroReservacion);
            
            try (ResultSet rs = pasajeros.executeQuery()) {
                while (rs.next()) {
                    pasajerosReservacion.add(rs.getString("id_pasajero"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un problema con los pasajeros de la reservacion "+e);
        }
        return pasajerosReservacion;
    }
    
    private void guardarPasajero(Connection connection, int numeroReservacion, String dpiPasajero) throws DatosInvalidosException {
        try (PreparedStatement pasajeros = connection.prepareStatement(GUARDAR_PASAJEROS_RESERVACION)) {
            pasajeros.setInt(1, numeroReservacion);
            pasajeros.setString(2, dpiPasajero);
            
            pasajeros.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al guardar pasajero "+e);
        }
    }
    
}
