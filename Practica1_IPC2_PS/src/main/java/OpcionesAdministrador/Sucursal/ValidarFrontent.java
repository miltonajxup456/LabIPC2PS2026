/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpcionesAdministrador.Sucursal;

/**
 *
 * @author millin-115
 */
public class ValidarFrontent {
    
    
    public static boolean estaVacio(String texto) {
        return texto.isBlank();
    }
    
    public static boolean revisionValido(String texto, int limite) {
        return texto.length() <= limite;
    }
    
}
