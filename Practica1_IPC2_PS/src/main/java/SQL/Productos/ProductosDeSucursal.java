/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL.Productos;

import Listas.ListaGenerica;
import SQL.ConnectorDB;
import Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ProductosDeSucursal {
    
    public ListaGenerica<Producto> buscar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Producto.id_producto, Producto.nombre "
                + "FROM Sucursal_Producto "
                + "JOIN Producto "
                + "ON Producto.id_producto = Sucursal_Producto.id_producto "
                + "WHERE Sucursal_Producto.id_sucursal = ?";
        
        ListaGenerica<Producto> productosSucursal = new ListaGenerica<>();
        
        try (Connection conn = ConnectorDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, usuario.getIdSucursal());
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt("id_producto"), rs.getString("nombre"), usuario.getIdSucursal());
                    productosSucursal.agregarElemento(producto);
                }
            }
        }
        return productosSucursal;
    }
    
}
