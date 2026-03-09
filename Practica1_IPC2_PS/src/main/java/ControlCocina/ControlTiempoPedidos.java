/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlCocina;

/**
 *
 * @author millin-115
 */
public class ControlTiempoPedidos {
    
    private int nivel;
    private int segundosNivel1;
    private int segundosNivel2;
    private int segundosNivel3;
    private int segundosPedido;
    private int pedidosParaNivel2;
    private int pedidosParaNivel3;
    private int pedidosCompletados;
    
    public ControlTiempoPedidos() {
        this.segundosNivel1 = 45;
        this.segundosNivel2 = 35;
        this.segundosNivel3 = 25;
        this.pedidosParaNivel2 = 8;
        this.pedidosParaNivel3 = 15;
    }
    
    public int getNivel() {
        return nivel;
    }
    
    public void setSegundosNivel1(int nuevoTiempo) {
        this.segundosNivel1 = nuevoTiempo;
    }
    
    public void setSegundosNivel2(int nuevoTiempo) {
        this.segundosNivel2 = nuevoTiempo;
    }
    
    public void setSegundosNivel3(int nuevoTiempo) {
        this.segundosNivel3 = nuevoTiempo;
    }
    
    public int getSegundosPartida() {
        return segundosPedido;
    }
    
    public void tiempoInicial() {
        segundosPedido = segundosNivel1;
        nivel = 1;
    }
    
    public void calcularTiempo() {
        pedidosCompletados++;
        if (pedidosCompletados >= pedidosParaNivel2 && pedidosCompletados < pedidosParaNivel3) {
            segundosPedido = segundosNivel2;
            nivel = 2;
        } else if (pedidosCompletados >= pedidosParaNivel3) {
            segundosPedido = segundosNivel3;
            nivel = 3;
        }
    }
    
    
    
}
