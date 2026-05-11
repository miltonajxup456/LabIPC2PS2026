/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReporteDAO;

import Modelos.Reporte.HistorialProyecto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author millin-115
 */
public class ReporteProyectosDAO {
    
    public void generarPDF(HttpServlet servlet, HttpServletResponse response, List<HistorialProyecto> historial) {
        try {
            
            InputStream reporte = servlet.getServletContext().getResourceAsStream("/reportes/ReporteHistorialProyectos.jasper");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(historial);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            
            response.setContentType("application/pdf");
            response.setContentLength(pdf.length);
            response.setHeader("Content-Disposition", "inline; filename=proyectos.pdf");
            
            OutputStream out = response.getOutputStream();
            out.write(pdf);
            out.flush();
            
        } catch (JRException | IOException e) {
            System.out.println("Error al genera pdf de Proyectos "+e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}
