/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlCocina;

import Excepciones.AgregarException;
import Excepciones.PedidosException;
import FrontentCocina.PlantillaLista;
import FrontentCocina.VentanaPizzaExpressTycoon;
import Pedidos.GeneracionPedido;
import Pedidos.Pedido;
import Pedidos.PedidosActivos;
import SQL.Partida.GestorGuardadoPartida;
import SQL.Pedido.GestorGuardadoPedido;
import SQL.Pedido.GestorEstadoPedido;
import com.mycompany.practica1_ipc2_ps.Estado;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class ControladorPartida {
    
    private final VentanaPizzaExpressTycoon ventana;
    private final PedidosActivos activos;
    private final ControlTiempoPedidos controlTiempo;
    private GeneracionPedido generacionPedido;
    private Pedido pedidoActivo;
    private boolean aceptadoOcupado;
    private int estadoPedidoActual;
    private int puntuacion;
    private final int puntosBase;
    private final int penalizacionRechazo;
    private int contadorCompletados;
    private int contadorRechazados;
    private int contadorNoEntregados;
    private final Estado estados;
    private final GestorGuardadoPedido gestorPedido;
    private final GestorEstadoPedido gestorEstado;
    private final GestorGuardadoPartida gestorPartida;
    private int idPartida;
    
    public ControladorPartida(VentanaPizzaExpressTycoon ventana, PedidosActivos activos, GestorGuardadoPedido gestorPedido, GestorGuardadoPartida gestorPartida) {
        this.ventana = ventana;
        this.activos = activos;
        this.controlTiempo = new ControlTiempoPedidos();
        this.estadoPedidoActual = 0;
        this.puntuacion = 0;
        this.puntosBase = 100;
        this.penalizacionRechazo = -30;
        this.estados = new Estado();
        this.gestorPedido = gestorPedido;
        this.gestorPartida = gestorPartida;
        this.gestorEstado = new GestorEstadoPedido();
    }
    
    public void setGeneracionPedido(GeneracionPedido generacionPedido) {
        this.generacionPedido = generacionPedido;
    }

    public int getContadorCompletados() {
        return contadorCompletados;
    }

    public int getContadorRechazados() {
        return contadorRechazados;
    }

    public int getContadorNoEntregados() {
        return contadorNoEntregados;
    }
    
    public int getSegundosPartida() {
        return controlTiempo.getSegundosPartida();
    }
    
    public int getPuntuacion() {
        return puntuacion;
    }
    
    public int getNivelAlcanzado() {
        return controlTiempo.getNivel();
    }
    
    public void reiniciarValores(int idPartida) {
        this.idPartida = idPartida;
        puntuacion = 0;
        contadorCompletados = 0;
        contadorRechazados = 0;
        contadorNoEntregados = 0;
        controlTiempo.tiempoInicial();
    }
    
    public void agregarAceptado(Pedido pedidoActivo) throws AgregarException, SQLException, ClassNotFoundException {
        if (aceptadoOcupado) {
            throw new AgregarException("No se puede aceptar dos pedidos al mismo tiempo");
        }
        this.pedidoActivo = pedidoActivo;
        aceptadoOcupado = true;
        ventana.cambiarAAceptado(pedidoActivo.getPlantilla());
        ventana.setEstadoPanel("Recibido");
        guardarEstadoPedido(pedidoActivo, estados.getRecibido());
    }
    
    public void cambiarEstado() throws PedidosException, SQLException, ClassNotFoundException {
        estadoPedidoActual++;
        if (!aceptadoOcupado) {
            estadoPedidoActual = 0;
            ventana.setEstadoPanel("");
        }
        
        else if (estadoPedidoActual == 1) {
            ventana.setEstadoPanel("Preparando");
            guardarEstadoPedido(pedidoActivo, estados.getPreparando());
        }
        else if (estadoPedidoActual == 2) {
            ventana.setEstadoPanel("En horno");
            guardarEstadoPedido(pedidoActivo, estados.getEnHorno());
        }
        else if (estadoPedidoActual == 3) {
            ventana.setEstadoPanel("Entregar");
        }
        else if (estadoPedidoActual >= 4) {
            calcularPuntos();
            continuarGeneracion();
            controlTiempo.calcularTiempo();
            activos.eliminarPedido(pedidoActivo.getNumeroPedidoCola());
            aceptadoOcupado = false;
            ventana.setEstadoPanel("Entregado");
            guardarEstadoPedido(pedidoActivo, estados.getEntregado());
            ventana.agregarConcluido(pedidoActivo.getPlantilla());
            contadorCompletados++;
            estadoPedidoActual = 0;
        }
    }
    
    public void cancelarPedido(Pedido pedido) throws PedidosException, SQLException, ClassNotFoundException {
        pedido.detenerCronometro();
        continuarGeneracion();
        activos.eliminarPedido(pedido.getNumeroPedidoCola());
        puntuacion += penalizacionRechazo;
        correccionPuntos();
        pedido.getPlantilla().setTexto2("Rechazado");
        guardarEstadoPedido(pedido, estados.getRechazado());
        ventana.setPuntos(puntuacion);
        ventana.agregarConcluido(pedido.getPlantilla());
        contadorRechazados++;
    }
    
    public void noEntregado(PlantillaLista plantilla) throws PedidosException, SQLException, ClassNotFoundException {
        Pedido pedido = plantilla.getPedido();
        continuarGeneracion();
        if (aceptadoOcupado) {
            aceptadoOcupado = false;
        }
        activos.eliminarPedido(pedido.getNumeroPedidoCola());
        puntuacion -= (puntosBase/2);
        correccionPuntos();
        plantilla.desactivarBotones();
        plantilla.setTexto2("No Entregado");
        guardarEstadoPedido(pedido, estados.getNoEntregado());
        gestorPartida.actualizarPuntajeYNivel(idPartida, puntuacion, controlTiempo.getNivel());
        ventana.setPuntos(puntuacion);
        ventana.agregarConcluido(plantilla);
        contadorNoEntregados++;
        ventana.escirbirNoEntregados(contadorNoEntregados);
    }
    
    private void calcularPuntos() {
        pedidoActivo.detenerCronometro();
        int tiempoRestante = pedidoActivo.getSegundosRestantes();
        int tiempoActual = controlTiempo.getSegundosPartida();
        if (tiempoRestante > tiempoActual/2) {
            puntuacion = puntuacion + (puntosBase/2);
        }
        puntuacion += puntosBase;
        pedidoActivo.getPlantilla().setTexto2("Entregado con "+tiempoRestante+" seg restantes");
        ventana.setPuntos(puntuacion);
    }
    
    private void continuarGeneracion() {
        if (activos.getUltimoEspacio() >= activos.getLimite()) {
            generacionPedido.interrupt();
        }
    }
    
    private void correccionPuntos() {
        if (puntuacion < 0) {
            puntuacion = 0;
        }
    }
    
    private void guardarEstadoPedido(Pedido pedido, int estadoPedido) throws SQLException, ClassNotFoundException {
        gestorPedido.actualizarEstado(pedido.getIdPedido(), estadoPedido);
        gestorEstado.guardarEstado(pedido.getIdPedido(), estadoPedido);
    }
}
