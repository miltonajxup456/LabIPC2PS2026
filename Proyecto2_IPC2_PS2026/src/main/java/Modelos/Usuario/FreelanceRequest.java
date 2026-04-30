/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuario;

/**
 *
 * @author millin-115
 */
public class FreelanceRequest extends UsuarioRequest {
    
    private String biografia;
    private double tarifaReferencial;
    private int nivelExperiencia;
    private String tipoNivelExperiencia;

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
