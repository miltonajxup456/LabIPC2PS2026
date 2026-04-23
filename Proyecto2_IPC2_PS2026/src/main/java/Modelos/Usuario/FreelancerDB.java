/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuario;

/**
 *
 * @author millin-115
 */
public class FreelancerDB extends UsuarioDB {
    
    private final String biografia;
    private final double tarifaReferencial;
    
    public FreelancerDB(String nombreUsuario, String nombre, String passwordUser, String correoElectronico, 
            String telefono, String direccion, String cui, String fechaNac, String informacionUsuario, 
            boolean baneo, double saldo, int rol, String tipoRol, 
            String biografia, double tarifaReferencial){
        super(nombreUsuario, nombre, passwordUser, correoElectronico, 
                telefono, direccion, cui, fechaNac, informacionUsuario, 
                baneo, saldo, rol, tipoRol);
        this.biografia = biografia;
        this.tarifaReferencial = tarifaReferencial;
    }

    public String getBiografia() {
        return biografia;
    }

    public double getTarifaReferencial() {
        return tarifaReferencial;
    }
    
}
