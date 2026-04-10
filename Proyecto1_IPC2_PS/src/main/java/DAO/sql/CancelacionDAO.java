/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Cancelacion.CancelacionDB;
import Model.Cancelacion.CancelacionRequest;
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
public class CancelacionDAO {
    
    private static final String GET_CANCELACIONES = "SELECT * FROM Cancelacion_Reservacion";
    private static final String RESERVACIONES_CANCELADAS = ""
            + "SELECT rsv.*, paq.nombre AS nombre_paquete, COALESCE(SUM(pago.monto_pagado), 0) AS dinero_pagado "
            + "FROM Reservacion rsv JOIN Paquete_Turistico paq "
            + "ON rsv.paquete = paq.id_paquete LEFT JOIN Pago pago ON rsv.numero_reservacion = pago.num_reservacion "
            + "WHERE estado = 3 AND fecha_viaje BETWEEN ? AND ? GROUP BY numero_reservacion";
    private static final String AGREGAR_CANCELACION = "INSERT INTO Cancelacion_Reservacion (id_reservacion, porcentaje_reembolso) VALUES (?,?)";
    
    public List<CancelacionDB> getCancelacionesEntreFechas(String fechaInicial, String fechaFinal) throws EntidadNoEncontradaException {
        List<CancelacionDB> cancelaciones = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement busqueda = connection.prepareStatement(RESERVACIONES_CANCELADAS)) {
            busqueda.setString(1, fechaInicial);
            busqueda.setString(2, fechaFinal);
            try (ResultSet rs = busqueda.executeQuery()) {
                while (rs.next()) {
                    cancelaciones.add(armarCancelacion(rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al buscar las cancelaciones por fechas "+e);
        }
        completarDetalles(cancelaciones);
        return cancelaciones;
    }
    
    private List<CancelacionRequest> getDetallesCancelacion() {
        List<CancelacionRequest> detalles = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement select = connection.prepareStatement(GET_CANCELACIONES)) {
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    detalles.add(armarDetalles(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en detalles de cancelacion"+e);
        }
        return detalles;
    }
    
    private List<CancelacionDB> completarDetalles(List<CancelacionDB> cancelaciones) {
        List<CancelacionRequest> detalles = getDetallesCancelacion();
        for (int i = 0; i < cancelaciones.size(); i++) {
            CancelacionDB actual = cancelaciones.get(i);
            for (int j = 0; j < detalles.size(); j++) {
                CancelacionRequest detalleActual = detalles.get(j);
                if (actual.getNumeroReservacion() == detalleActual.getIdReservacion()) {
                    actual.setFechaCancelacion(detalleActual.getFechaCancelacion());
                    actual.setPorcentajeReembolso(detalleActual.getPorcentaje());
                }
            }
        }
        return cancelaciones;
    }
    
    private CancelacionDB armarCancelacion(ResultSet rs) throws SQLException {
        CancelacionDB cancelacion = new CancelacionDB(rs.getInt("numero_reservacion"), 
                rs.getString("fecha_viaje"), 
                rs.getDouble("costo"), 
                rs.getString("nombre_paquete"), 
                rs.getDouble("dinero_pagado"));
        return cancelacion;
    }
    
    private CancelacionRequest armarDetalles(ResultSet rs) throws SQLException {
        CancelacionRequest detalle = new CancelacionRequest();
        detalle.setIdReservacion(rs.getInt("id_reservacion"));
        detalle.setPorcentaje(rs.getInt("porcentaje_reembolso"));
        detalle.setFechaCancelacion(rs.getString("fecha_cancelacion"));
        return detalle;
    }
    
    public void agregarReservacion(CancelacionRequest request) throws DatosInvalidosException {
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_CANCELACION)) {
            insert.setInt(1, request.getIdReservacion());
            insert.setInt(2, request.getPorcentaje());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al agregar cancelacion "+e);
        }
    }
    
}
