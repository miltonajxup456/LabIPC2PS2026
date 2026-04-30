/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author millin-115
 */
public class EntregaDAO {
    
    private static final String ENTREGAS_PROYECTO = "SELECT * FROM Entrega_Proyecto WHERE proyecto = ?";
    private static final String AGREGAR_ENTREGA = "INSERT INTO Entrega_Proyecto (descripcion, path_archivo, proyecto, freelancer) VALUES (?,?,?,?)";
    private static final String MODIFICAR_ENTREGA = "UPDATE Entrega_Proyecto SET descripcion = ? WHERE id_entrega = ?";
    
    
    
}
