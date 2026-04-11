/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Archivos.Reportes;

import Model.Cancelacion.CancelacionDB;
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
public class ReporteCancelaciones {
    
    public void generarPdfCancelaciones(List<CancelacionDB> cancelaciones, String fechaInicial, String fechaFinal, OutputStream output) {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                
                contenido.setLeading(15);
                
                contenido.newLineAtOffset(100, 700);
                
                contenido.showText("Reportes de Cancelaciones de Reservaciones entre fechas: "+fechaInicial+" y "+fechaFinal);
                contenido.newLine();
                contenido.newLine();
                
                for (int i = 0; i < cancelaciones.size(); i++) {
                    CancelacionDB actual = cancelaciones.get(i);
                    
                    contenido.showText("Numero de Reservacion: "+actual.getNumeroReservacion());
                    contenido.newLine();
                    contenido.showText("Nombre del Paquete: "+actual.getNombrePaquete());
                    contenido.newLine();
                    contenido.showText("Fecha de Cancelacion: "+actual.getFechaCancelacion());
                    contenido.newLine();
                    contenido.showText("Porcentaje de Reembolso: "+actual.getPorcentajeReembolso());
                    contenido.newLine();
                    contenido.showText("Dinero Pagado: "+actual.getDineroPagado());
                    contenido.newLine();
                    
                    
                    contenido.newLine();
                    contenido.newLine();
                }
            }
            
            documento.save(output);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
