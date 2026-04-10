/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.pdf;

import Model.Pago.PagoDB;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

/**
 *
 * @author millin-115
 */
public class PagopdfDAO {
    
    public void generarPDF(PagoDB pago, OutputStream output) {
        try (PDDocument documento = new PDDocument() /*Crea un documento*/ ) {

            //Crea una pagina
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina) /*Se habilita la edicion de la pagina*/ ) {
                //Para escribir texto se debe iniciar un bloque
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                
                contenido.setLeading(15); //Espacio entre lineas
                
                contenido.newLineAtOffset(100, 700); //Posicionar el cursor de texto en la pagina
                
                //Escribir el bloque de texto
                contenido.showText("Detalles del pago Realizado");
                contenido.newLine();
                contenido.showText("Id del Pago: "+pago.getIdPago());
                contenido.newLine();
                contenido.showText("Metodo de Pago: "+pago.getMetodoPago());
                contenido.newLine();
                contenido.showText("Monto Pagado: "+pago.getMontoPagado());
                contenido.newLine();
                contenido.showText("Fecha del pago: "+pago.getFecha());
                contenido.newLine();
                contenido.showText("Para la reservacion con numero: "+pago.getNumReservacion());
                
                contenido.endText(); //Terminar el bloque de texto
            }
            
            //Guardar el documento en un archivo
            documento.save("/home/millin-115/LabIPC2PS2026/Proyecto1_IPC2_PS/target/Pagos en PDF/pago"+pago.getIdPago()+".pdf");
            documento.save(output);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}


//public void generarPDF(PagoDB pago) {
//    try {
//        //Crea un documento
//        PDDocument documento = new PDDocument();
//
//        //Crea una pagina
//        PDPage pagina = new PDPage();
//        documento.addPage(pagina);
//
//        //Se habilita la edicion de la pagina
//        PDPageContentStream contenido = new PDPageContentStream(documento, pagina);
//
//        //Para escribir texto se debe iniciar un bloque
//        contenido.beginText();
//
//        //contenido.setFont(PDType1Font.HELVETICA_BOLD, 18);
//
//        //Posicionar el cursor de texto en la pagina
//        contenido.newLineAtOffset(100, 700);
//
//        //Escribir el bloque de texto
//        contenido.showText("Texto");
//
//        //Terminar el bloque de texto
//        contenido.endText();
//
//        //Cerrar el flujo del contenido
//        contenido.close();
//
//        //Guardar el documento en un archivo
//        documento.save("pago.pdf");
//
//        //Cerrar el documento
//        documento.close();
//
//    } catch (IOException e) {
//        System.out.println(e.getMessage());
//    }
//}