package Listas;

import Excepciones.ListaException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author millin-115
 * @param <T>
 */
public class ListaGenerica<T> {
    
    private int tamaño;
    private NodoGenerico<T> inicio;
    private NodoGenerico<T> fin;
    
    public int getTamaño() {
        return tamaño;
    }
    
    public void agregarElemento(T contenido) {
        NodoGenerico<T> nuevo = new NodoGenerico<> (contenido);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        tamaño++;
    }
    
    public T obtenerPorContenido(T contenido) throws ListaException {
        if (contenido == null) {
            throw new ListaException("Error... Se ingreso un elemento que es nulo");
        }
        NodoGenerico<T> actual = inicio;
        for (int i = 0; i < tamaño; i++) {
            if (actual.getContenido().equals(contenido)) {
                return actual.getContenido();
            }
            actual = actual.getSiguiente();
        }
        throw new ListaException("El elemento que se intenta buscar no esta en la lista");
    }
    
    public T obtenerConIndice(int indice) throws ListaException {
        NodoGenerico<T> contenidoBuscar = obtenerNodo(indice);
        return contenidoBuscar.getContenido();
    }
    
    public void reemplazarEnIndice(int index, T contenido) throws ListaException {
        NodoGenerico<T> nodo = obtenerNodo(index);
        nodo.setContenido(contenido);
    }
    
    public void eliminarConIndice(int indice) throws ListaException {
        verificarIndice(indice);
        
        if (indice == 0) {
            inicio = inicio.getSiguiente();
            if (inicio == null) {
                fin = null;
            }
        } else {
            NodoGenerico<T> anterior = obtenerNodo(indice - 1);
            NodoGenerico<T> nodoAEliminar = anterior.getSiguiente();
            NodoGenerico<T> siguiente = nodoAEliminar.getSiguiente();
            
            anterior.setSiguiente(siguiente);
            if (nodoAEliminar == fin) {
                fin = anterior;
            }
        }
        tamaño--;
    }
    
    public NodoGenerico<T> obtenerNodo (int indice) throws ListaException {
        verificarIndice(indice);
        
        NodoGenerico<T> actual = inicio;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }
    
    private void verificarIndice(int indice) throws ListaException {
        if (indice < 0) {
            throw new ListaException("No puedes buscar indices negativos");
        }
        else if (indice >= tamaño) {
            throw new ListaException("La lista unicamente cuenta con " + tamaño + " elementos");
        }
    }
    
}
