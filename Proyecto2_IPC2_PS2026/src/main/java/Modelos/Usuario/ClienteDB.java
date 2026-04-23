/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuario;

/**
 *
 * @author millin-115
 */
public class ClienteDB extends UsuarioDB {
    
    private final String descripcionEmpresa;
    private final String industriaPerteneciente;
    private final String sitioWeb;
    
    public ClienteDB(String nombreUsuario, String nombre, String passwordUser, String correoElectronico, 
            String telefono, String direccion, String cui, String fechaNac, String informacionUsuario, 
            boolean baneo, double saldo, int rol, String tipoRol, 
            String descripcionEmpresa, String industriaPerteneciente, String sitioWeb) {
        super(nombreUsuario, nombre, passwordUser, correoElectronico, 
                telefono, direccion, cui, fechaNac, informacionUsuario,
                baneo, saldo, rol, tipoRol);
        this.descripcionEmpresa = descripcionEmpresa;
        this.industriaPerteneciente = industriaPerteneciente;
        this.sitioWeb = sitioWeb;
    }

    public String getDescripcionEmpresa() {
        return descripcionEmpresa;
    }

    public String getIndustriaPerteneciente() {
        return industriaPerteneciente;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }
    
}
