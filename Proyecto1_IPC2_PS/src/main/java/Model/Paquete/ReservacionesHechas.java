/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Paquete;

/**
 *
 * @author millin-115
 */
public class ReservacionesHechas {
    
    private final int paquete;
    private final int reservacionesHechas;

    public ReservacionesHechas(int paquete, int reservacionesHechas) {
        this.paquete = paquete;
        this.reservacionesHechas = reservacionesHechas;
    }

    public int getPaquete() {
        return paquete;
    }

    public int getReservacionesHechas() {
        return reservacionesHechas;
    }
    
}
