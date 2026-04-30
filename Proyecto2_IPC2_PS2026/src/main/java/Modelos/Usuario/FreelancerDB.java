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
    
    private String biografia;
    private double tarifaReferencial;
    private int nivelExperiencia;
    private String tipoNivelExperiencia;

    public FreelancerDB(UsuarioDB u){
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

    public FreelancerDB(String biografia, double tarifaReferencial, int nivelExperiencia, String tipoNivelExperiencia) {
        this.biografia = biografia;
        this.tarifaReferencial = tarifaReferencial;
        this.nivelExperiencia = nivelExperiencia;
        this.tipoNivelExperiencia = tipoNivelExperiencia;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public double getTarifaReferencial() {
        return tarifaReferencial;
    }

    public void setTarifaReferencial(double tarifaReferencial) {
        this.tarifaReferencial = tarifaReferencial;
    }

    public int getNivelExperiencia() {
        return nivelExperiencia;
    }

    public void setNivelExperiencia(int nivelExperiencia) {
        this.nivelExperiencia = nivelExperiencia;
    }

    public String getTipoNivelExperiencia() {
        return tipoNivelExperiencia;
    }

    public void setTipoNivelExperiencia(String tipoNivelExperiencia) {
        this.tipoNivelExperiencia = tipoNivelExperiencia;
    }

}
