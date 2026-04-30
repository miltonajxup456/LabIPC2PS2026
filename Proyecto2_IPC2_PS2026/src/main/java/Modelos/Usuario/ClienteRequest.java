/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuario;

/**
 *
 * @author millin-115
 */
public class ClienteRequest extends UsuarioRequest {
    
    private String descripcionEmpresa;
    private String industriaPerteneciente;
    private String sitioWeb;

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
