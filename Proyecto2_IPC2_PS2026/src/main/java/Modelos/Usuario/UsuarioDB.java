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
    
    protected String nombreUsuario;
    protected String nombre;
    protected String passwordUser;
    protected String correoElectronico;
    protected String telefono;
    protected String direccion;
    protected String cui;
    protected String fechaNac;
    protected String informacionUsuario;
    protected boolean baneo;
    protected double saldo;
    protected int rol;
    protected String tipoRol;
    
    public UsuarioDB(){}

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
