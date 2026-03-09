/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Menus;

import ControlCocina.OrganizadorDeCocina;
import Excepciones.ListaException;
import Excepciones.PartidaException;
import FrontentCocina.VentanaPizzaExpressTycoon;
import Logins.Frontent.VentanaEleccionUsuario;
import OpcionesAdministrador.Estadisticas.ColocarEstadisticasPedidos;
import OpcionesSucursalProducto.Frontent.VentanaRanking;
import Usuario.Usuario;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author millin-115
 */
public class VentanaMenuUsuario extends javax.swing.JFrame {
    
    private final OrganizadorDeCocina organizadorCocina;
    private final VentanaPizzaExpressTycoon ventanaCocina;
    private VentanaEleccionUsuario eleccionUsuario;
    private VentanaRanking ventanaRanking;
    private ColocarEstadisticasPedidos colocarStats;
    private Usuario usuario;
    
    public VentanaMenuUsuario(OrganizadorDeCocina organizadorCocina) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.organizadorCocina = organizadorCocina;
        this.ventanaCocina = organizadorCocina.getVentanaCocina();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void setEleccionUsuario(VentanaEleccionUsuario eleccionUsuario) {
        this.eleccionUsuario = eleccionUsuario;
    }
    
    public void setOpcionesEstadisticas(VentanaRanking ventanaRanking, ColocarEstadisticasPedidos colocarStats) {
        this.ventanaRanking = ventanaRanking;
        this.colocarStats = colocarStats;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        botonRegresar = new javax.swing.JButton();
        botonIniciarPartida = new javax.swing.JButton();
        botonHistorial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botonRegresar.setFont(new java.awt.Font("Liberation Sans", 0, 22)); // NOI18N
        botonRegresar.setText("Regresar");
        botonRegresar.addActionListener(this::botonRegresarActionPerformed);

        botonIniciarPartida.setFont(new java.awt.Font("Liberation Sans", 0, 22)); // NOI18N
        botonIniciarPartida.setText("Iniciar Partida");
        botonIniciarPartida.addActionListener(this::botonIniciarPartidaActionPerformed);

        botonHistorial.setFont(new java.awt.Font("Liberation Sans", 0, 22)); // NOI18N
        botonHistorial.setText("Ver Historial");
        botonHistorial.addActionListener(this::botonHistorialActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(252, Short.MAX_VALUE)
                .addComponent(botonRegresar)
                .addGap(29, 29, 29))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonIniciarPartida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(botonRegresar)
                .addGap(49, 49, 49)
                .addComponent(botonIniciarPartida)
                .addGap(42, 42, 42)
                .addComponent(botonHistorial)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonIniciarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarPartidaActionPerformed
        try {
            organizadorCocina.inicializarPartida();
            ventanaCocina.setVentanaAnterior(this);
            this.setVisible(false);
        } catch (SQLException | ClassNotFoundException | PartidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_botonIniciarPartidaActionPerformed

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed
        this.setVisible(false);
        eleccionUsuario.setVisible(true);
    }//GEN-LAST:event_botonRegresarActionPerformed

    private void botonHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHistorialActionPerformed
//        try {
//            colocarStats.colocarEstadisticaUsuario(usuario);
//            this.setVisible(false);
//            ventanaRanking.setTituloEstadisticas("Estadisticas de la sucursal "+usuario.getNombreSucursal());
//            ventanaRanking.setVentanaAnterior(this);
//            ventanaRanking.setVisible(true);
//        } catch (SQLException | ClassNotFoundException | ListaException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
    }//GEN-LAST:event_botonHistorialActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonHistorial;
    private javax.swing.JButton botonIniciarPartida;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
    public void mostrar() {
        this.setVisible(true);
    }
    
}
