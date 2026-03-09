/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_ipc2_ps;

/**
 *
 * @author millin-115
 */
public class Estado {
    
    private final int recibido;
    private final int preparando;
    private final int enHorno;
    private final int entregado;
    private final int rechazado;
    private final int noEntregado;
    
    public Estado() {
        this.recibido = 1;
        this.preparando = 2;
        this.enHorno = 3;
        this.entregado = 4;
        this.rechazado = 5;
        this.noEntregado = 6;
    }

    public int getRecibido() {
        return recibido;
    }

    public int getPreparando() {
        return preparando;
    }

    public int getEnHorno() {
        return enHorno;
    }

    public int getEntregado() {
        return entregado;
    }
    
    public int getRechazado() {
        return rechazado;
    }
    
    public int getNoEntregado() {
        return noEntregado;
    }
    
}
