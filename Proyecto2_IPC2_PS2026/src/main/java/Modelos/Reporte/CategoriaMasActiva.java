/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Reporte;

/**
 *
 * @author millin-115
 */
public class CategoriaMasActiva {
    
    private final int categoria;
    private final String nombreCategoria;
    private final int vecesSolicitadas;

    public CategoriaMasActiva(int categoria, String nombreCategoria, int vecesSolicitadas) {
        this.categoria = categoria;
        this.nombreCategoria = nombreCategoria;
        this.vecesSolicitadas = vecesSolicitadas;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getVecesSolicitadas() {
        return vecesSolicitadas;
    }
    
}
