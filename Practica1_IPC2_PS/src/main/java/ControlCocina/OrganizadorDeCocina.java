/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlCocina;

import Excepciones.PartidaException;
import FrontentCocina.VentanaPizzaExpressTycoon;
import Pedidos.GeneracionPedido;
import Hilos.TiempoPartida;
import Listas.ListaGenerica;
import Pedidos.PedidosActivos;
import SQL.Partida.GestorGuardadoPartida;
import SQL.Pedido.GestorGuardadoPedido;
import SQL.Productos.Producto;
import Usuario.Usuario;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class OrganizadorDeCocina {
    
    private final VentanaPizzaExpressTycoon ventanaCocina;
    private final GestorGuardadoPartida gestorPartida;
    private final GestorGuardadoPedido gestorPedido;
    private ListaGenerica<Producto> productosDisponibles;
    private Usuario usuario;
    private final int limitePedidos;
    private final PedidosActivos activos;
    private final ControladorPartida controlPartida;
    private GeneracionPedido generacionPedido;
    private TiempoPartida tiempoPartida;
    private int idPartida;
    
    public OrganizadorDeCocina() {
        this.ventanaCocina = new VentanaPizzaExpressTycoon();
        this.gestorPartida = new GestorGuardadoPartida();
        this.gestorPedido = new GestorGuardadoPedido();
        this.limitePedidos = 5;
        this.activos = new PedidosActivos(limitePedidos);
        this.controlPartida = new ControladorPartida(ventanaCocina, activos, gestorPedido, gestorPartida);
    }
    
    public VentanaPizzaExpressTycoon getVentanaCocina() {
        return ventanaCocina;
    }
      
    public void setProductosDisponibles(ListaGenerica<Producto> productosDisponibles) {
        this.productosDisponibles = productosDisponibles;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void inicializarPartida() throws SQLException, ClassNotFoundException, PartidaException {
        
        existenciaPedidos();
        
        crearPartidaParaBaseDatos();
        
        prepararPartida();
        
        generacionDePedidos();
        
    }
    
    private void existenciaPedidos() throws PartidaException {
        if (productosDisponibles.getTamaño() <= 0) {
            throw new PartidaException("No se puede iniciar una partida porque no existen productos registrados en esta sucursal");
        }
    }
    
    private void crearPartidaParaBaseDatos() throws SQLException, ClassNotFoundException {
        idPartida = gestorPartida.crearPartida(usuario.getIdUsuario(), usuario.getIdSucursal(), 0, 0);
    }
    
    private void prepararPartida() {
        
        controlPartida.reiniciarValores(idPartida);
        ventanaCocina.setControlVentana(controlPartida);
        tiempoPartida = new TiempoPartida(ventanaCocina, activos, gestorPartida, controlPartida, idPartida);
        ventanaCocina.setTiempo(tiempoPartida);
        
        ventanaCocina.comenzarPartida();
        tiempoPartida.start();
        
    }
    
    private void generacionDePedidos() {
        generacionPedido = new GeneracionPedido(ventanaCocina, controlPartida, productosDisponibles, activos, gestorPedido, limitePedidos, idPartida);
        controlPartida.setGeneracionPedido(generacionPedido);
        tiempoPartida.setGeneracionPedido(generacionPedido);
        generacionPedido.start();
    }
    
}
