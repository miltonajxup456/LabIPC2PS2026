/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Pago.PagoDB;
import Model.Pago.PagoRequest;
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
public class PagoDAO {
    
    private static final String ALL_PAGOS = "SELECT * FROM Pago";
    private static final String PAGOS_POR_RESERVACION = "SELECT pago.*, metodo.metodo AS metodo_pago "
            + "FROM Pago pago JOIN Metodo_Pago metodo ON pago.metodo = metodo.id_metodo_pago "
            + "WHERE num_reservacion = ?";
    private static final String PAGOS_POR_ID = "SELECT pago.*, metodo.metodo AS metodo_pago "
            + "FROM Pago pago JOIN Metodo_Pago metodo ON pago.metodo = metodo.id_metodo_pago "
            + "WHERE id_pago = ?";
    private static final String CREAR_REGISTRO_PAGO = "INSERT INTO Pago (monto_pagado, num_reservacion, metodo) VALUES (?,?,?)";
    
    public List<PagoDB> getPagosPorReservacion(int numeroReservacion) throws EntidadNoEncontradaException {
        List<PagoDB> pagosReservacion = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement pagos = connection.prepareStatement(PAGOS_POR_RESERVACION)) {
            pagos.setInt(1, numeroReservacion);
            try (ResultSet rs = pagos.executeQuery()) {
                while (rs.next()) {
                    pagosReservacion.add(armarPago(rs));
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al buscar pagos por reservacion "+e);
        }
        return pagosReservacion;
    }
    
    public PagoDB getPagoPorId(int idPago) throws EntidadNoEncontradaException {
        PagoDB pago = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement busqueda = connection.prepareStatement(PAGOS_POR_ID)) {
            busqueda.setInt(1, idPago);
            try (ResultSet rs = busqueda.executeQuery()) {
                if (rs.next()) {
                    pago = armarPago(rs);
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error al buscar Pago por id: "+e);
        }
        return pago;
    }
    
    public PagoDB crearRegistroPago(PagoRequest request) throws DatosInvalidosException {
        PagoDB pago = null;
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(CREAR_REGISTRO_PAGO, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insert.setDouble(1, request.getMontoPagado());
            insert.setInt(2, request.getNumReservacion());
            insert.setInt(3, request.getMetodo());
            
            insert.executeUpdate();
            
            try(ResultSet rs = insert.getGeneratedKeys()) {
                if (rs.next()) {
                    try {
                        int idPago = rs.getInt(1);
                        pago = getPagoPorId(idPago);
                    } catch (EntidadNoEncontradaException e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatosInvalidosException("Error al guardar el pago "+e);
        }
        return pago;
    }
    
    private PagoDB armarPago(ResultSet rs) throws SQLException {
        PagoDB pago = new PagoDB(rs.getInt("id_pago"), 
                rs.getDouble("monto_pagado"), 
                rs.getString("fecha"), 
                rs.getInt("num_reservacion"), 
                rs.getInt("metodo"), 
                rs.getString("metodo_pago"));
        return pago;
    }
    
}
