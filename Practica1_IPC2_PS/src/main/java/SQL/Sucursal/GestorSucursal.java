/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Sucursal;

import Listas.ListaGenerica;
import SQL.ConnectorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class GestorSucursal {
    
    public void crear(String nombreSucursal) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Sucursal (nombre) VALUES (?)";
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstmSucursal = connection.prepareStatement(sql)) {
            pstmSucursal.setString(1, nombreSucursal);
            
            pstmSucursal.executeUpdate();
        }
    }
    
    public ListaGenerica<Sucursal> getSucursales() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_sucursal, nombre FROM Sucursal";
        ListaGenerica<Sucursal> sucursales = new ListaGenerica<>();
        
        try (Connection connection = ConnectorDB.getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    Sucursal sucursal = new Sucursal(resultSet.getInt("id_sucursal"), resultSet.getString("nombre"));
                    sucursales.agregarElemento(sucursal);
                }
            }
        }
        return sucursales;
    }
    
}
