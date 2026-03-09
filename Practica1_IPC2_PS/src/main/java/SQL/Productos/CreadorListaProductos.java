/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Productos;

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
public class CreadorListaProductos {
    
    public ListaGenerica<Producto> crearDesdeBD() {
        
        String sql = "SELECT id_producto, nombre FROM Producto;";
        ListaGenerica<Producto> productos = new ListaGenerica<>();
        
        try (Connection conn = ConnectorDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt("id_producto"), rs.getString("nombre"));
                    productos.agregarElemento(producto);
                }
            }
            
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ocurrio un error con la base de datos "+e.getMessage());
        }
        return productos;
    }
    
}
