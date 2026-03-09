/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pedidos;

import FrontentCocina.PlantillaLista;
import Hilos.TiempoPedido;

/**
 *
 * @author millin-115
 */
public class Pedido {
    
    private final int idPedido;
    private PlantillaLista plantilla;
    private TiempoPedido tiempoPedido;
    private int numeroPedidoCola;
    
    public Pedido(int idPedido) {
        this.idPedido = idPedido;
    }
    
    public int getIdPedido() {
        return idPedido;
    }
    
    public PlantillaLista getPlantilla() {
        return plantilla;
    }
    
    public void setPlantilla(PlantillaLista plantilla) {
        this.plantilla = plantilla;
    }
    
    public void setNumeroPedidoCola(int numeroPedidoCola) {
        this.numeroPedidoCola = numeroPedidoCola;
    }
    
    public int getNumeroPedidoCola() {
        return numeroPedidoCola;
    }

    public void setTiempoPedido(TiempoPedido tiempoPedido) {
        this.tiempoPedido = tiempoPedido;
    }
    
    public void iniciarCronometro() {
        tiempoPedido.start();
    }
    
    public void detenerCronometro() {
        tiempoPedido.interrupt();
    }
    
    public int getSegundosRestantes() {
        return tiempoPedido.getSegundos();
    }
    
    public void finalizarPedido() {
        tiempoPedido.setSegundos(0);
    }
    
}
