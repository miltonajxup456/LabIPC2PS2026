/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Login;

/**
 *
 * @author millin-115
 */
public class UsuarioDB {
    
    private final int idUsuario;
    private final String nombre;
    private String password_user;
    private final int rol;

    public UsuarioDB(int idUsuario, String nombre, int rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
    }

    public UsuarioDB(int idUsuario, String nombre, String contraseña, int rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.password_user = contraseña;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword_user() {
        return password_user;
    }

    public int getRol() {
        return rol;
    }
    
}
