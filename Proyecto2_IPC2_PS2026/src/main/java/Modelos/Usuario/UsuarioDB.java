/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuario;

/**
 *
 * @author millin-115
 */
public class UsuarioDB {
    
    private final String nombreUsuario;
    private final String nombre;
    private final String passwordUser;
    private final String correoElectronico;
    private final String telefono;
    private final String direccion;
    private final String cui;
    private final String fechaNac;
    private final String informacionUsuario;
    private final boolean baneo;
    private final double saldo;
    private final int rol;
    private final String tipoRol;

    public UsuarioDB(String nombreUsuario, String nombre, String passwordUser, String correoElectronico, 
            String telefono, String direccion, String cui, String fechaNac, String informacionUsuario, 
            boolean baneo, double saldo, int rol, String tipoRol) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.passwordUser = passwordUser;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cui = cui;
        this.fechaNac = fechaNac;
        this.informacionUsuario = informacionUsuario;
        this.baneo = baneo;
        this.saldo = saldo;
        this.rol = rol;
        this.tipoRol = tipoRol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCui() {
        return cui;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getInformacionUsuario() {
        return informacionUsuario;
    }

    public boolean isBaneo() {
        return baneo;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getRol() {
        return rol;
    }

    public String getTipoRol() {
        return tipoRol;
    }
    
}
