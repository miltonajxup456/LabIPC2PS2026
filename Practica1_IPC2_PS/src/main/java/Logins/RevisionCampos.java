/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logins;

/**
 *
 * @author millin-115
 */
public class RevisionCampos {
    
    public boolean camposVacios(String nombreUsuario, String contraseña) {
        return nombreUsuario.isBlank() || contraseña.isBlank();
    }
    
}
