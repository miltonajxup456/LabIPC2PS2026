/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Archivos.Reportes;

import Model.OcupacionDestino.OcupacionDestinoDB;
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
public class ReporteOcupacionDestino {
    
    public void generarPDF(List<OcupacionDestinoDB> reservaciones, String fechaInicial, String fechaFinal, OutputStream output) {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                
                contenido.setLeading(15);
                contenido.newLineAtOffset(100, 700);
                
                contenido.showText("Reportes Por la Ocupacion de un Destino entre fechas: "+fechaInicial+" y "+fechaFinal);
                contenido.newLine();
                contenido.newLine();
                
                for (int i = 0; i < reservaciones.size(); i++) {
                    OcupacionDestinoDB actual = reservaciones.get(i);
                    
                    contenido.showText("Id del Destino: "+actual.getIdDestino());
                    contenido.newLine();
                    contenido.showText("Nombre del Destino: "+actual.getNombreDestino());
                    contenido.newLine();
                    contenido.showText("Reservaciones para el destino: "+actual.getOcupacion());
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
