/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Cliente;

/**
 *
 * @author millin-115
 */
public class ClienteDB {
    
    private final String dpi;
    private final String nombre;
    private final String fechaNac;
    private final String telefono;
    private final String email;
    private final String nacionalidad;

    public ClienteDB(String dpi, String nombre, String fechaNac, String telefono, String email, String nacionalidad) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
    
}
