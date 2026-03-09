/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import ControlCocina.ControladorPartida;
import Excepciones.PedidosException;
import FrontentCocina.PlantillaLista;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class TiempoPedido extends Thread {
    
    private final ControladorPartida controlVentana;
    private final PlantillaLista plantilla;
    private int segundos;
    
    public TiempoPedido(ControladorPartida controlVentana, PlantillaLista plantilla) {
        this.controlVentana = controlVentana;
        this.plantilla = plantilla;
    }
    
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
    
    public int getSegundos() {
        return segundos;
    }
    
    @Override
    public void run() {
        comenzarPedido();
    }
    
    private void comenzarPedido() {
        segundos = controlVentana.getSegundosPartida();
        try {
            plantilla.actualizarTiempo(segundos);
            while (segundos > 0) {
                Thread.sleep(1000);
                actualizarTiempo();
                plantilla.actualizarTiempo(segundos);
            }
        } catch (InterruptedException e) {
            return;
        }
        
        try {
            controlVentana.noEntregado(plantilla);
        } catch (PedidosException | SQLException | ClassNotFoundException e) {
            System.out.println("Error... "+e.getMessage());
        }

    }
    
    private void actualizarTiempo() {
        segundos--;
    }
    
}
