/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pedidos;

import ControlCocina.ControladorPartida;
import Excepciones.ListaException;
import Excepciones.PedidosException;
import FrontentCocina.PlantillaLista;
import FrontentCocina.VentanaPizzaExpressTycoon;
import Hilos.TiempoPedido;
import Listas.ListaGenerica;
import SQL.Pedido.GestorGuardadoPedido;
import SQL.Productos.Producto;
import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author millin-115
 */
public class GeneracionPedido extends Thread {
    
    //Tiempo generado aleatoriamente en un rango de tiempo
    private final VentanaPizzaExpressTycoon ventana;
    private final ControladorPartida control;
    private final ListaGenerica<Producto> productosDisponibles;
    private final PedidosActivos activos;
    private final GestorGuardadoPedido gestorPedido;
    private int segMaximos;
    private int segMinimos;
    private final int limitePedidos;
    private final int idPartida;
    private boolean esPrimero;
    private boolean pausa;
    
    public GeneracionPedido(VentanaPizzaExpressTycoon ventana, ControladorPartida control, ListaGenerica<Producto> productosDisponibles, 
            PedidosActivos activos, GestorGuardadoPedido gestorPedido, int limitePedidos, int idPartida) {
        this.ventana = ventana;
        this.control = control;
        this.productosDisponibles = productosDisponibles;
        this.activos = activos;
        this.gestorPedido = gestorPedido;
        this.segMaximos = 8;
        this.segMinimos = 4;
        this.limitePedidos = limitePedidos;
        this.idPartida = idPartida;
        this.esPrimero = true;
    }
    
    public void setSegundos(int segMaximos, int segMinimos) throws PedidosException {
        if (segMaximos < 0 || segMinimos < 0) {
            throw new PedidosException("No se puede ingresar segundos negativos");
        }
        this.segMaximos = segMaximos;
        this.segMinimos = segMinimos;
    }
    
    public boolean getPausa() {
        return pausa;
    }
    
    @Override
    public void run() {
        generarTiempo();
    }
    
    public void generarTiempo() {
        Random random = new Random();
        while (ventana.getPartidaActiva()) {
            
            if (activos.getUltimoEspacio() >= limitePedidos) {
                simulacionDePausa();
            }
            
            int tiempo;
            if (esPrimero) {
                tiempo = 0;
                esPrimero = false;
            } else {
                tiempo = random.nextInt(segMinimos, segMaximos+1);
            }
            
            try {
                Thread.sleep(tiempo*1000);
            } catch (InterruptedException e) {
                return;
            }
            
            try {
                generarPedido();
            } catch (ListaException | PedidosException | SQLException | ClassNotFoundException e) {
                System.out.println("Ocurrio un error... "+e.getMessage());
            }
        }
    }
    
    private void generarPedido() throws ListaException, PedidosException, SQLException, ClassNotFoundException {
        Random random = new Random();
        int numeroPedido = random.nextInt(productosDisponibles.getTamaño());
        Producto producto = productosDisponibles.obtenerConIndice(numeroPedido);
        String nombre = producto.getNombre();
        int idPedido = gestorPedido.crearPedido(producto.getIdProducto(), idPartida);
        
        Pedido pedido = new Pedido(idPedido);
        PlantillaLista plantilla = new PlantillaLista(control, pedido, nombre);
        pedido.setPlantilla(plantilla);
        ventana.agregarPedido(plantilla);
        activos.agregarPedido(pedido);
        
        TiempoPedido tiempoPedido = new TiempoPedido(control, plantilla);
        pedido.setTiempoPedido(tiempoPedido);
        pedido.iniciarCronometro();
        
    }
    
    private void simulacionDePausa() {
        pausa = true;
        while (pausa) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                pausa = false;
            }
        }
    }
    
}
