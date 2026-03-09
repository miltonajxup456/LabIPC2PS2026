/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package FrontentCocina;

import ControlCocina.ControladorPartida;
import Excepciones.AgregarException;
import Excepciones.PedidosException;
import Pedidos.Pedido;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author millin-115
 */
public class PlantillaLista extends javax.swing.JPanel {
    
    private final ControladorPartida control;
    private final Pedido pedido;
    
    public PlantillaLista(ControladorPartida control, Pedido pedido, String nombreProducto) {
        initComponents();
        this.control = control;
        this.pedido = pedido;
        this.titulo.setText(nombreProducto);
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonRechazar = new javax.swing.JButton();
        texto2 = new javax.swing.JLabel();

        titulo.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        titulo.setText("Plantilla");

        botonAceptar.setFont(new java.awt.Font("Liberation Sans", 0, 22)); // NOI18N
        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(this::botonAceptarActionPerformed);

        botonRechazar.setFont(new java.awt.Font("Liberation Sans", 0, 22)); // NOI18N
        botonRechazar.setText("Rechazar");
        botonRechazar.addActionListener(this::botonRechazarActionPerformed);

        texto2.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        texto2.setText("Tiempo del Pedido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonRechazar))
                    .addComponent(texto2))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonRechazar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        try {
            control.agregarAceptado(pedido);
            desactivarBotones();
        } catch (AgregarException | SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRechazarActionPerformed
        try {
            control.cancelarPedido(pedido);
            desactivarBotones();
        } catch (PedidosException | SQLException | ClassNotFoundException e) {
            System.out.println("Error... "+e.getMessage());
        }
    }//GEN-LAST:event_botonRechazarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonRechazar;
    private javax.swing.JLabel texto2;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
    
    public void actualizarTiempo(int segundos) {
        texto2.setText("Tiempo del Pedido "+segundos+"s");
    }
    
    public void setTexto2(String texto) {
        texto2.setText(texto);
    }
    
    public void desactivarBotones() {
        botonAceptar.setVisible(false);
        botonRechazar.setVisible(false);
    }
    
}
