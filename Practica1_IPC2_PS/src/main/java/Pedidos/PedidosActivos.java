/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pedidos;

import Excepciones.PedidosException;

/**
 *
 * @author millin-115
 */
public class PedidosActivos {
    
    private final Pedido[] pedidosActivos;
    private int ultimoEspacio;
    
    public PedidosActivos(int limitePedidos) {
        this.pedidosActivos = new Pedido[limitePedidos];
        this.ultimoEspacio = 0;
    }
    
    public int getLimite() {
        return pedidosActivos.length;
    }
    
    public int getUltimoEspacio() {
        return ultimoEspacio;
    }
    
    public void agregarPedido(Pedido pedido) throws PedidosException {
        if (ultimoEspacio >= pedidosActivos.length) {
            throw new PedidosException("La lista de pedidos esta llena");
        }
        pedidosActivos[ultimoEspacio] = pedido;
        pedido.setNumeroPedidoCola(ultimoEspacio);
        ultimoEspacio++;
    }
    
    public Pedido getPedido(int indice) throws PedidosException {
        revisarIndice(indice);
        return pedidosActivos[indice];
    }
    
    public void eliminarPedido(int indice) throws PedidosException {
        revisarIndice(indice);
        
        for (int i = indice; i < ultimoEspacio - 1 ; i++) {
            pedidosActivos[i] = pedidosActivos[i+1];
            pedidosActivos[i].setNumeroPedidoCola(i);
        }
        
        ultimoEspacio--;
        pedidosActivos[ultimoEspacio] = null;
    }
    
    private void revisarIndice(int indice) throws PedidosException {
        if (indice < 0 || indice >= ultimoEspacio) {
            throw new PedidosException("Indice fuera de rango permitido '"+indice+"'");
        }
    }
    
}
