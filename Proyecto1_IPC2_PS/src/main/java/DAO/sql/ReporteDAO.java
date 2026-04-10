/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.EntidadNoEncontradaException;
import Model.OcupacionDestino.OcupacionDestinoDB;
import Model.Reservacion.ReservacionDB;
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
public class ReporteDAO {
    
    private final ReservacionDAO reservaciondao;
    
    public ReporteDAO() {
        reservaciondao = new ReservacionDAO();
    }
    
    private static final String RESERVACION_ENTRE_FECHAS = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, usu.nombre AS nombre_registrador "
            + "FROM Reservacion rsv "
            + "JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "JOIN Usuario usu ON rsv.agente_de_registro = usu.id_usuario "
            + "WHERE estado = 2 AND fecha_viaje BETWEEN ? AND ? ORDER BY fecha_viaje";
    private static final String AGENTE_CON_MAS_VENTAS = ""
            + "SELECT agente_de_registro, COUNT(agente_de_registro) AS reservaciones_hechas, GROUP_CONCAT(numero_reservacion) AS numero_reservacion "
            + "FROM Reservacion WHERE fecha_viaje BETWEEN ? AND ? GROUP BY agente_de_registro ORDER BY reservaciones_hechas DESC LIMIT 1";
    private static final String RESERVACIONES_DE_AGENTE = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, usu.nombre AS nombre_registrador "
            + "FROM Reservacion rsv "
            + "JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "JOIN Usuario usu ON rsv.agente_de_registro = usu.id_usuario "
            + "WHERE agente_de_registro = ? AND fecha_viaje BETWEEN ? AND ? ORDER BY fecha_viaje";
    private static final String AGENTE_CON_MAS_GANANCIAS = ""
            + "SELECT agente_de_registro, COUNT(agente_de_registro) AS reservaciones_completadas "
            + "FROM Reservacion WHERE estado = 2 AND fecha_viaje BETWEEN ? AND ? GROUP BY agente_de_registro ORDER BY reservaciones_completadas DESC LIMIT 1";
    private static final String GANANCIAS_RESERVACIONES = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, usu.nombre AS nombre_registrador "
            + "FROM Reservacion rsv "
            + "JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "JOIN Usuario usu ON rsv.agente_de_registro = usu.id_usuario "
            + "WHERE estado = 2 AND agente_de_registro = ? AND fecha_viaje BETWEEN ? AND ? ORDER BY fecha_viaje";
    private static final String COSTOS_PAQUETE = "SELECT costo FROM Servicio_Paquete WHERE paquete = ?";
    private static final String PAQUETE_MAS_VENDIDO = ""
            + "SELECT paquete, COUNT(paquete) AS paquete_mas_vendido "
            + "FROM Reservacion WHERE fecha_viaje BETWEEN ? AND ? GROUP BY paquete ORDER BY paquete_mas_vendido DESC LIMIT 1";
    private static final String RESERVACIONES_PAQUETE = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado "
            + "FROM Reservacion rsv JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "LEFT JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion "
            + "WHERE paquete = ? AND fecha_viaje BETWEEN ? AND ? GROUP BY numero_reservacion";
    private static final String PAQUETE_MENOS_VENDIDO = ""
            + "SELECT paquete, COUNT(paquete) AS paquete_mas_vendido "
            + "FROM Reservacion WHERE fecha_viaje BETWEEN ? AND ? GROUP BY paquete ORDER BY paquete_mas_vendido ASC LIMIT 1";
    private static final String OCUPACION_DESTINO = ""
            + "SELECT des.id_destino, des.nombre AS nombre_destino, COUNT(des.id_destino) AS ocupacion "
            + "FROM Reservacion rsv "
            + "JOIN Paquete_Turistico paq ON rsv.paquete = paq.id_paquete "
            + "JOIN Destino des ON paq.destino = des.id_destino "
            + "WHERE rsv.fecha_viaje BETWEEN ? AND ? GROUP BY des.id_destino ORDER BY ocupacion DESC";
    
    public List<ReservacionDB> getReservacionesEntreFechas(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<ReservacionDB> historial = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement porFecha = connection.prepareStatement(RESERVACION_ENTRE_FECHAS)) {
            porFecha.setString(1, fechaInicial);
            porFecha.setString(2, fechaFinal);
            try (ResultSet rs = porFecha.executeQuery()) {
                while (rs.next()) {
                    historial.add(armarReservacionFechas(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al buscar Reservacion por Fechas "+e);
        }
        return historial;
    }
    
    public List<ReservacionDB> getReservacionesDeAgenteRegistro(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<ReservacionDB> reservaciones = new ArrayList<>();
        int agenteDeRegistro = 0;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement agente = connection.prepareStatement(AGENTE_CON_MAS_VENTAS);
                PreparedStatement reservacionesAgente = connection.prepareStatement(RESERVACIONES_DE_AGENTE)) {
            agente.setString(1, fechaInicial);
            agente.setString(2, fechaFinal);
            try (ResultSet rs = agente.executeQuery()) {
                if (rs.next()) {
                    agenteDeRegistro = rs.getInt("agente_de_registro");
                }
            }
            reservacionesAgente.setInt(1, agenteDeRegistro);
            reservacionesAgente.setString(2, fechaInicial);
            reservacionesAgente.setString(3, fechaFinal);
            try (ResultSet rs = reservacionesAgente.executeQuery()) {
                while (rs.next()) {
                    reservaciones.add(armarReservacionFechas(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al obtener reservaciones de agente de registro "+e);
        }
        return reservaciones;
    }
    
    public List<ReservacionDB> getGananciasDeAgenteRegistro(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<ReservacionDB> reservaciones = new ArrayList<>();
        int agenteDeRegistro = 0;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement agente = connection.prepareStatement(AGENTE_CON_MAS_GANANCIAS);
                PreparedStatement ganancias = connection.prepareStatement(GANANCIAS_RESERVACIONES)) {
            agente.setString(1, fechaInicial);
            agente.setString(2, fechaFinal);
            try (ResultSet rs = agente.executeQuery()) {
                if (rs.next()) {
                    agenteDeRegistro = rs.getInt("agente_de_registro");
                }
            }
            ganancias.setInt(1, agenteDeRegistro);
            ganancias.setString(2, fechaInicial);
            ganancias.setString(3, fechaFinal);
            try (ResultSet rs = ganancias.executeQuery()) {
                while (rs.next()) {
                    reservaciones.add(armarReservacionFechas(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al obtener ganancias de agente de registro "+e);
        }
        return reservaciones;
    }
    
    public int getCostosPaquete(int idPaquete) throws EntidadNoEncontradaException {
        int costoPaquete = 0;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement costo = connection.prepareStatement(COSTOS_PAQUETE)) {
            costo.setInt(1, idPaquete);
            try (ResultSet rs = costo.executeQuery()) {
                while (rs.next()) {
                    costoPaquete += rs.getInt("costo");
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al buscar costos de paquete");
        }
        return costoPaquete;
    }
    
    public List<ReservacionDB> getPaqueteMasVendido(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<ReservacionDB> reservaciones = new ArrayList<>();
        int paquete = 0;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement agente = connection.prepareStatement(PAQUETE_MAS_VENDIDO);
                PreparedStatement reservacionPaq = connection.prepareStatement(RESERVACIONES_PAQUETE)) {
            agente.setString(1, fechaInicial);
            agente.setString(2, fechaFinal);
            try (ResultSet rs = agente.executeQuery()) {
                if (rs.next()) {
                    paquete = rs.getInt("paquete");
                }
            }
            reservacionPaq.setInt(1, paquete);
            reservacionPaq.setString(2, fechaInicial);
            reservacionPaq.setString(3, fechaFinal);
            try (ResultSet rs = reservacionPaq.executeQuery()) {
                while (rs.next()) {
                    reservaciones.add(reservaciondao.armarReservacion(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al obtener ganancias de agente de registro "+e);
        }
        return reservaciones;
    }
    
    
    public List<ReservacionDB> getPaqueteMenosVendido(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<ReservacionDB> reservaciones = new ArrayList<>();
        int paquete = 0;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement agente = connection.prepareStatement(PAQUETE_MENOS_VENDIDO);
                PreparedStatement reservacionPaq = connection.prepareStatement(RESERVACIONES_PAQUETE)) {
            agente.setString(1, fechaInicial);
            agente.setString(2, fechaFinal);
            try (ResultSet rs = agente.executeQuery()) {
                if (rs.next()) {
                    paquete = rs.getInt("paquete");
                }
            }
            reservacionPaq.setInt(1, paquete);
            reservacionPaq.setString(2, fechaInicial);
            reservacionPaq.setString(3, fechaFinal);
            try (ResultSet rs = reservacionPaq.executeQuery()) {
                while (rs.next()) {
                    reservaciones.add(reservaciondao.armarReservacion(connection, rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al obtener ganancias de agente de registro "+e);
        }
        return reservaciones;
    }
    
    public List<OcupacionDestinoDB> getOcupacionDestino(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<OcupacionDestinoDB> ocupacion = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement destinos = connection.prepareStatement(OCUPACION_DESTINO)) {
            destinos.setString(1, fechaInicial);
            destinos.setString(2, fechaFinal);
            try (ResultSet rs = destinos.executeQuery()) {
                while (rs.next()) {
                    ocupacion.add(new OcupacionDestinoDB(rs.getInt("id_destino"), rs.getString("nombre_destino"), rs.getInt("ocupacion")));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al buscar ocupacion de destinos "+e);
        }
        return ocupacion;
    }
    
    private ReservacionDB armarReservacionFechas(Connection connection, ResultSet rs) throws SQLException {
        ReservacionDB reservacion = new ReservacionDB(rs.getInt("numero_reservacion"), 
                rs.getString("fecha_viaje"), 
                rs.getString("fecha_creacion"), 
                rs.getInt("cantidad_pasajeros"), 
                rs.getDouble("costo"), 
                rs.getInt("agente_de_registro"), 
                rs.getInt("paquete"), 
                rs.getInt("estado"), 
                rs.getString("nombre_paquete"), 
                rs.getString("nombre_registrador"));
        reservacion.setDpiPasajeros(reservaciondao.getPasajeros(connection, reservacion.getNumReservacion()));
        return reservacion;
    }
    
}
