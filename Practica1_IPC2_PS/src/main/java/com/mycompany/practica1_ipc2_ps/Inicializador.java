/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_ipc2_ps;

import ControlCocina.OrganizadorDeCocina;
import Listas.ListaGenerica;
import Logins.ControlDeUsuario;
import Logins.Frontent.VentanaIngresoUsuario;
import Logins.Frontent.VentanaEleccionUsuario;
import Menus.VentanaMenuAdministrador;
import Menus.VentanaMenuSuperAdmin;
import Menus.VentanaMenuUsuario;
import OpcionesSucursalProducto.ColocarPlantillas;
import OpcionesSucursalProducto.Frontent.VentanaCrearProducto;
import OpcionesSucursalProducto.Frontent.VentanaHabilitarProductos;
import OpcionesSucursalProducto.Frontent.VentanaEstadisticas;
import OpcionesSucursalProducto.OpcionCrearProducto;
import OpcionesSuperAdmin.Frontent.VentanaCambiarUsuario;
import OpcionesSuperAdmin.Frontent.VentanaCrearSucursal;
import OpcionesSuperAdmin.Frontent.VentanaCrearUsuario;
import OpcionesSuperAdmin.Sucursal.ColocarPlantillasSuperAdmin;
import SQL.Productos.CreadorListaProductos;
import SQL.Productos.Producto;
import SQL.Sucursal.GestorSucursal;
import SQL.Usuario.GestorUsuario;

/**
 *
 * @author millin-115
 */
public class Inicializador {
    
    private VentanaCrearProducto ventanaCrearProducto;
    private VentanaEstadisticas ventanaEstadisticas;
    private VentanaMenuSuperAdmin menuSuperAdmin;
    private VentanaMenuAdministrador menuAdmin;
    private VentanaMenuUsuario menuUsuario;
    
    public void iniciarPrograma() {
        
        CreadorListaProductos creadorProductos = new CreadorListaProductos();
        ListaGenerica<Producto> todosLosProductos = creadorProductos.crearDesdeBD();
        
        OrganizadorDeCocina organizadorCocina = new OrganizadorDeCocina();
        
        opcionesSuperAdmin(todosLosProductos, organizadorCocina);
        
        opcionesAdministrador(todosLosProductos);
        
        ventanasLogin(organizadorCocina, todosLosProductos);
        
    }
    
    private void opcionesSuperAdmin(ListaGenerica<Producto> todosLosProductos, OrganizadorDeCocina organizadorCocina) {
        
        GestorSucursal gestorSucursal = new GestorSucursal();
        GestorUsuario gestorUsuario = new GestorUsuario();
        OpcionCrearProducto opcionCrear = new OpcionCrearProducto(todosLosProductos);
        ventanaCrearProducto = new VentanaCrearProducto(opcionCrear);
        ColocarPlantillasSuperAdmin plantillasSuperAdmin = new ColocarPlantillasSuperAdmin(gestorSucursal, gestorUsuario);
        
        menuSuperAdmin = new VentanaMenuSuperAdmin(ventanaCrearProducto, organizadorCocina);
        VentanaCrearSucursal crearSucursal = new VentanaCrearSucursal(menuSuperAdmin, gestorSucursal);
        menuSuperAdmin.setCrearSucursal(crearSucursal);
        VentanaCrearUsuario crearUsuario = new VentanaCrearUsuario(menuSuperAdmin, gestorUsuario);
        menuSuperAdmin.setOpcionesCrearUsuario(crearUsuario, plantillasSuperAdmin);
        VentanaCambiarUsuario cambiarUsuario = new VentanaCambiarUsuario(menuSuperAdmin, gestorUsuario);
        menuSuperAdmin.setVentanaCambiarUsuario(cambiarUsuario);
        ventanaEstadisticas = new VentanaEstadisticas();
        menuSuperAdmin.setOpcionesRanking(ventanaEstadisticas);
        
    }
    
    private void opcionesAdministrador(ListaGenerica<Producto> todosLosProductos) {
        
        VentanaHabilitarProductos ventanaEleccionProductos = new VentanaHabilitarProductos();
        ColocarPlantillas colocarPlantillas = new ColocarPlantillas(ventanaEleccionProductos, todosLosProductos);
        
        
        menuAdmin = new VentanaMenuAdministrador(ventanaEleccionProductos, colocarPlantillas, ventanaCrearProducto);
        ventanaEleccionProductos.setMenuAdmin(menuAdmin);
        menuAdmin.setOpcionesEstadisticas(ventanaEstadisticas);
        
    }
    
    private void ventanasLogin(OrganizadorDeCocina organizadorCocina, ListaGenerica<Producto> todosLosProductos) {
        
        menuUsuario = new VentanaMenuUsuario(organizadorCocina);
        menuUsuario.setOpcionesEstadisticas(ventanaEstadisticas);
        
        ControlDeUsuario controlUsuario = new ControlDeUsuario(menuSuperAdmin, menuAdmin, menuUsuario, organizadorCocina, todosLosProductos);
        VentanaIngresoUsuario ingreso = new VentanaIngresoUsuario(controlUsuario);
        VentanaEleccionUsuario eleccionUsuario = new VentanaEleccionUsuario(ingreso);
        ingreso.setEleccionUsuario(eleccionUsuario);
        
        menuSuperAdmin.setEleccionUsuario(eleccionUsuario);
        menuAdmin.setEleccionUsuario(eleccionUsuario);
        menuUsuario.setEleccionUsuario(eleccionUsuario);
        
        eleccionUsuario.setVisible(true);
    }
    
}
