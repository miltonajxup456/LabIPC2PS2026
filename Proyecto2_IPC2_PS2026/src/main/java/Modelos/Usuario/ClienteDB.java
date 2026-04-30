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
    
    private String descripcionEmpresa;
    private String industriaPerteneciente;
    private String sitioWeb;
    
    public ClienteDB(UsuarioDB u) {
        this.nombreUsuario = u.getNombreUsuario();
        this.nombre = u.getNombre();
        this.passwordUser = u.getPasswordUser();
        this.correoElectronico = u.getCorreoElectronico();
        this.telefono = u.getTelefono();
        this.direccion = u.getDireccion();
        this.cui = u.getCui();
        this.fechaNac = u.getFechaNac();
        this.informacionUsuario = u.getInformacionUsuario();
        this.baneo = u.isBaneo();
        this.saldo = u.getSaldo();
        this.rol = u.getRol();
        this.tipoRol = u.getTipoRol();
    }

    public ClienteDB(String descripcionEmpresa, String industriaPerteneciente, String sitioWeb) {
        this.descripcionEmpresa = descripcionEmpresa;
        this.industriaPerteneciente = industriaPerteneciente;
        this.sitioWeb = sitioWeb;
    }

    public String getDescripcionEmpresa() {
        return descripcionEmpresa;
    }

    public void setDescripcionEmpresa(String descripcionEmpresa) {
        this.descripcionEmpresa = descripcionEmpresa;
    }

    public String getIndustriaPerteneciente() {
        return industriaPerteneciente;
    }

    public void setIndustriaPerteneciente(String industriaPerteneciente) {
        this.industriaPerteneciente = industriaPerteneciente;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

}
