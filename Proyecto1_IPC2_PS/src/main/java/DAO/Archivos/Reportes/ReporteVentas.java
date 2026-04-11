/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Archivos.Reportes;

import Model.Reservacion.ReservacionDB;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

/**
 *
 * @author millin-115
 */
public class ReporteVentas {
    
    public void generarReporteVentas(List<ReservacionDB> reservaciones, String fechaInicial, String fechaFinal, OutputStream output) {
        try (PDDocument documento = new PDDocument()) {
            
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                
                contenido.setLeading(15);
                
                contenido.newLineAtOffset(100, 700);
                
                contenido.showText("Reportes de ventas de reservaciones entre fechas: "+fechaInicial+" y "+fechaFinal);
                contenido.newLine();
                contenido.newLine();
                
                for (int i = 0; i < reservaciones.size(); i++) {
                    ReservacionDB actual = reservaciones.get(i);
                    
                    contenido.showText("Nombre del paquete: "+actual.getNombrePaquete());
                    contenido.newLine();
                    contenido.showText("Agente de Registro: "+actual.getNombreCliente());
                    contenido.newLine();
                    contenido.showText("Dinero de la reservacion: "+actual.getCosto());
                    contenido.newLine();
                    contenido.showText("Lista de pasajeros");
                    contenido.newLine();
                    for (int j = 0; j < actual.getDpiPasajeros().size(); j++) {
                        contenido.showText(actual.getDpiPasajeros().get(j));
                    }
                    contenido.newLine();
                    contenido.newLine();
                }
            }
            
            //documento.save("/home/millin-115/LabIPC2PS2026/Proyecto1_IPC2_PS/target/Reportes PDF/reporte ventas.pdf");
            documento.save(output);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
