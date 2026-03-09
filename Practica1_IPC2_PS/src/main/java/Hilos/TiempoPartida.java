/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import ControlCocina.ControladorPartida;
import Excepciones.PedidosException;
import FrontentCocina.VentanaPizzaExpressTycoon;
import Pedidos.GeneracionPedido;
import Pedidos.Pedido;
import Pedidos.PedidosActivos;
import SQL.Partida.GestorGuardadoPartida;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class TiempoPartida extends Thread {
    
    private final VentanaPizzaExpressTycoon ventana;
    private final PedidosActivos activos;
    private GeneracionPedido generacionPedido;
    private final GestorGuardadoPartida gestorPartida;
    private final ControladorPartida controlPartida;
    private final int idPartida;
    private int minutos;
    private int segundos;
    
    public TiempoPartida(VentanaPizzaExpressTycoon ventana, PedidosActivos activos, GestorGuardadoPartida gestorPartida, ControladorPartida controlPartida,
            int idPartida) {
        this.ventana = ventana;
        this.activos = activos;
        this.gestorPartida = gestorPartida;
        this.controlPartida = controlPartida;
        this.idPartida = idPartida;
        this.minutos = 2;
        this.segundos = 0;
    }
    
    public void setGeneracionPedido(GeneracionPedido generacionPedido) {
        this.generacionPedido = generacionPedido;
    }
    
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
    
    public void setMinutosYSegundos(int minutos, int segundos) {
        this.minutos = minutos;
        this.segundos = segundos;
    }
    
    @Override
    public void run() {
        empezarTiempo();
    }
    
    private void empezarTiempo() {
        try {
            ventana.actualizarTiempo(minutos, segundos);
            while (minutos > 0 || segundos > 0) {
                Thread.sleep(1000);
                reducirTiempo();
                ventana.actualizarTiempo(minutos, segundos);
            }
        } catch (InterruptedException e) {
            //Se sale de la partida antes que el tiempo termine, no necesita mensaje
        }
        
        try {
            noEntregarPedidos();
        } catch (PedidosException e) {
            System.out.println("Error... "+e.getMessage());
        }
        
        guardarDatosDePartida();
        
    }
    
    private void noEntregarPedidos() throws PedidosException {
        ventana.terminarPartida();
        generacionPedido.interrupt();
        for (int i = 0; i < activos.getUltimoEspacio(); i++) {
            Pedido pedido = activos.getPedido(i);
            pedido.finalizarPedido();
        }
    }
    
    private void guardarDatosDePartida() {
        try {
            gestorPartida.actualizarPuntajeYNivel(idPartida, controlPartida.getPuntuacion(), controlPartida.getNivelAlcanzado());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error... "+e.getMessage());
        }
    }
    
    private void reducirTiempo() {
        segundos--;
        if (segundos < 0) {
            segundos = 59;
            minutos--;
        }
    }
    
}
