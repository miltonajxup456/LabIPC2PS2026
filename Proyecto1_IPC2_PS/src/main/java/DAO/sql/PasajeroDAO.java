/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.sql;

import ConexionDB.DBConnectionSingleton;
import Exceptions.EntidadNoEncontradaException;
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
public class PasajeroDAO {
    
    private static final String PASAJEROS_POR_DPI = "SELECT dpi FROM Cliente WHERE dpi = ?";
    
    public List<String> validarYObtenerPasajeros(String[] pasajeros) throws EntidadNoEncontradaException {
        if (pasajeros == null) {
            throw new EntidadNoEncontradaException("La lista esta no existe");
        }
        List<String> dpiPasajeros = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getConnection();
                PreparedStatement busqueda = connection.prepareStatement(PASAJEROS_POR_DPI)) {
            for (int i = 0; i < pasajeros.length; i++) {
                busqueda.setString(1, pasajeros[i]);
                try (ResultSet rs = busqueda.executeQuery()) {
                    if (rs.next()) {
                        dpiPasajeros.add(rs.getString("dpi"));
                    } else {
                        throw new EntidadNoEncontradaException("No existe el pasajero con DPI "+pasajeros[i]);
                    }
                }
            }
        } catch (SQLException e) {
            throw new EntidadNoEncontradaException("Error interno "+e);
        }
        return dpiPasajeros;
    }
    
}
