/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Habilidad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author millin-115
 */
public class ListaHabilidades {
    
    private List<HabilidadRequest> listaHabilidades;
    private List<Integer> listaIds;
    
    public ListaHabilidades() {
        listaHabilidades = new ArrayList<>();
        listaIds = new ArrayList<>();
    }

    public List<HabilidadRequest> getListaHabilidades() {
        return listaHabilidades;
    }

    public void setListaHabilidades(List<HabilidadRequest> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public List<Integer> getListaIds() {
        return listaIds;
    }

    public void setListaIds(List<Integer> listaIds) {
        this.listaIds = listaIds;
    }
    
}
