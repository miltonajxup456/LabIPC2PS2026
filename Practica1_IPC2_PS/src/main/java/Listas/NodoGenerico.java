/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Listas;

/**
 *
 * @author millin-115
 */
public class NodoGenerico<T> {
    
    private NodoGenerico<T> siguiente;
    private T contenido;
    
    public NodoGenerico(T contenido) {
        this.contenido = contenido;
    }
    
    public void setSiguiente (NodoGenerico<T> siguiente) {
        this.siguiente = siguiente;
    }
    
    public NodoGenerico<T> getSiguiente() {
        return siguiente;
    }
    
    public T getContenido() {
        return contenido;
    }
    
    public void setContenido(T contenido) {
        this.contenido = contenido;
    }
    
}
