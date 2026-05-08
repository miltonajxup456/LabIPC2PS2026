/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import ConexionDB.DBConnexionSingleton;
import Exceptions.DataBaseException;
import Modelos.Historial.HistorialRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class HistorialComisonProyectoDAO {
    
    private static final String AGREGAR_REGISTRO_COMISION = "INSERT INTO Historial_Comision_Proyecto (monto_proyecto, porcentaje_comision, cliente, freelancer) VALUES (?,?,?,?)";
    
    public void agregarRegistroComision(HistorialRequest request) throws DataBaseException {
        try (Connection connection = DBConnexionSingleton.getConnection();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_REGISTRO_COMISION)) {
            insert.setDouble(1, request.getMontoProyecto());
            insert.setInt(2, request.getPorcentajeComision());
            insert.setString(3, request.getCliente());
            insert.setString(4, request.getFreelancer());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Error al generar registro comision proyecto "+e);
        }
    }
}
