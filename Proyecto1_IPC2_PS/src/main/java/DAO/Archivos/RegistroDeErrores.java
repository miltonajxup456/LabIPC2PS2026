/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Archivos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author millin-115
 */
public class RegistroDeErrores {
    
    private int camposVacios;
    private int creacionCorrecta;
    private int erroresUsuario;
    private int erroresDestino;
    private int erroresProveedor;
    private int erroresPaquete;
    private int erroresServicioPaquete;
    private int erroresCliente;
    private int erroresReservacion;
    private int erroresPago;
    private final List<String> listaErrores;
    
    public RegistroDeErrores() {
        listaErrores = new ArrayList<>();
    }
    
    public void agregarErrorGenerico(String error) {
        listaErrores.add(error);
        camposVacios++;
    }
    
    public void agregarCorrecto() {
        creacionCorrecta++;
    }
    
    public void agregarErrorUsuario(String error) {
        listaErrores.add(error);
        erroresUsuario++;
    }
    
    public void agregarErrorDestino(String error) {
        listaErrores.add(error);
        erroresDestino++;
    }
    
    public void agregarErrorProveedor(String error) {
        listaErrores.add(error);
        erroresProveedor++;
    }
    
    public void agregarErrorPaquete(String error) {
        listaErrores.add(error);
        erroresPaquete++;
    }
    
    public void agregarErrorServicioPaquete(String error) {
        listaErrores.add(error);
        erroresServicioPaquete++;
    }
    
    public void agregarErrorCliente(String error) {
        listaErrores.add(error);
        erroresCliente++;
    }
    
    public void agregarErrorReservacion(String error) {
        listaErrores.add(error);
        erroresReservacion++;
    }
    
    public void agregarErrorPago(String error) {
        listaErrores.add(error);
        erroresPago++;
    }
    
    public void mostrarErroresReales() {
        for (int i = 0; i < listaErrores.size(); i++) {
            System.out.println(listaErrores.get(i));
        }
    }

    public int getCamposVacios() {
        return camposVacios;
    }

    public int getCreacionCorrecta() {
        return creacionCorrecta;
    }

    public int getErroresUsuario() {
        return erroresUsuario;
    }

    public int getErroresDestino() {
        return erroresDestino;
    }

    public int getErroresProveedor() {
        return erroresProveedor;
    }

    public int getErroresPaquete() {
        return erroresPaquete;
    }

    public int getErroresServicioPaquete() {
        return erroresServicioPaquete;
    }

    public int getErroresCliente() {
        return erroresCliente;
    }

    public int getErroresReservacion() {
        return erroresReservacion;
    }

    public int getErroresPago() {
        return erroresPago;
    }

    public List<String> getListaErrores() {
        return listaErrores;
    }
    
}
