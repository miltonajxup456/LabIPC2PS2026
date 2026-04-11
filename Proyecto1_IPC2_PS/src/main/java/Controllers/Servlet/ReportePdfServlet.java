/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.Archivos.Reportes.ReporteAgenteVentas;
import DAO.Archivos.Reportes.ReporteCancelaciones;
import DAO.Archivos.Reportes.ReporteGanancias;
import DAO.Archivos.Reportes.ReporteOcupacionDestino;
import DAO.Archivos.Reportes.ReportePaqueteMasVendido;
import DAO.Archivos.Reportes.ReportePaqueteMenosVentas;
import DAO.Archivos.Reportes.ReporteVentas;
import DAO.sql.CancelacionDAO;
import DAO.sql.ReporteDAO;
import Exceptions.EntidadNoEncontradaException;
import Model.Cancelacion.CancelacionDB;
import Model.OcupacionDestino.OcupacionDestinoDB;
import Model.Reservacion.ReservacionDB;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author millin-115
 */
@WebServlet("/pdf-reporte/*")
public class ReportePdfServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte.pdf");
        
        String path = request.getPathInfo();
        ReporteDAO reportedao = new ReporteDAO();
        
        try {
            if (path != null && !path.equals("/")) {
                String[] partes = path.substring(1).split("/");
                if (partes[0].equals("pdf-ventas")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getReservacionesEntreFechas(fechaInicial, fechaFinal);
                    ReporteVentas pdf = new ReporteVentas();
                    pdf.generarReporteVentas(reservaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-ganancias")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getReservacionesEntreFechas(fechaInicial, fechaFinal);
                    ReporteGanancias pdf = new ReporteGanancias();
                    pdf.generarPDF(reservaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-cancelaciones")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    CancelacionDAO cancelaciondao = new CancelacionDAO();
                    List<CancelacionDB> cancelaciones = cancelaciondao.getCancelacionesEntreFechas(fechaInicial, fechaFinal);
                    ReporteCancelaciones pdf = new ReporteCancelaciones();
                    pdf.generarPdfCancelaciones(cancelaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-agente-ventas")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getReservacionesDeAgenteRegistro(fechaInicial, fechaFinal);
                    ReporteAgenteVentas pdf = new ReporteAgenteVentas();
                    pdf.generarPDF(reservaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-agente-ganancias")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getGananciasDeAgenteRegistro(fechaInicial, fechaFinal);
                    ReporteGanancias pdf = new ReporteGanancias();
                    pdf.generarPDF(reservaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-paquete-mas-vendido")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getPaqueteMasVendido(fechaInicial, fechaFinal);
                    ReportePaqueteMasVendido pdf = new ReportePaqueteMasVendido();
                    pdf.generarPDF(reservaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-paquete-menos-vendido")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getPaqueteMenosVendido(fechaInicial, fechaFinal);
                    ReportePaqueteMenosVentas pdf = new ReportePaqueteMenosVentas();
                    pdf.generarPDF(reservaciones, fechaInicial, fechaFinal, response.getOutputStream());
                } else if (partes[0].equals("pdf-ocupacion-destino")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<OcupacionDestinoDB> destinos = reportedao.getOcupacionDestino(fechaInicial, fechaFinal);
                    ReporteOcupacionDestino pdf = new ReporteOcupacionDestino();
                    pdf.generarPDF(destinos, fechaInicial, fechaFinal, response.getOutputStream());
                }
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
}
