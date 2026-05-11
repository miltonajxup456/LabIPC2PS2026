/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.ReporteDAO;
import Exceptions.DataBaseException;
import Modelos.Reporte.CategoriaTrabajada;
import Modelos.Reporte.ContratoCompletado;
import Modelos.Reporte.GastoCategoria;
import Modelos.Reporte.HistorialProyecto;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import ReporteDAO.ReporteProyectosDAO;

/**
 *
 * @author millin-115
 */
@WebServlet("/reporte/*")
public class ReporteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ReporteDAO dao = new ReporteDAO();
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (instrucciones[0].equals("proyectos-publicados")) {
                String fechaInicial = instrucciones[1];
                String fechaFinal = instrucciones[2];
                String idCliente = instrucciones[3];
                List<HistorialProyecto> historial = dao.getHistorialProyecto(idCliente, fechaInicial, fechaFinal);
                response.getWriter().write(gson.toJson(historial));
            } else if (instrucciones[0].equals("proyectos-publicados-pdf")) {
                String fechaInicial = instrucciones[1];
                String fechaFinal = instrucciones[2];
                String idCliente = instrucciones[3];
                List<HistorialProyecto> historial = dao.getHistorialProyecto(idCliente, fechaInicial, fechaFinal);
                ReporteProyectosDAO daoRep = new ReporteProyectosDAO();
                daoRep.generarPDF(this, response, historial);
            } else if (instrucciones[0].equals("gasto-categoria")) {
                String fechaInicial = instrucciones[1];
                String fechaFinal = instrucciones[2];
                String idCliente = instrucciones[3];
                List<GastoCategoria> gastoCategorias = dao.getGastosCategoria(fechaInicial, fechaFinal, idCliente);
                response.getWriter().write(gson.toJson(gastoCategorias));
            } else if (instrucciones[0].equals("contratos-completados")) {
                String fechaInicial = instrucciones[1];
                String fechaFinal = instrucciones[2];
                String idCliente = instrucciones[3];
                List<ContratoCompletado> contratos = dao.getContratosCompletados(fechaInicial, fechaFinal, idCliente);
                response.getWriter().write(gson.toJson(contratos));
            } else if (instrucciones[0].equals("categorias-trabajadas")) {
                String idFreelancer = instrucciones[1];
                List<CategoriaTrabajada> categorias = dao.getCategoriasTrabajadas(idFreelancer);
                response.getWriter().write(gson.toJson(categorias));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
