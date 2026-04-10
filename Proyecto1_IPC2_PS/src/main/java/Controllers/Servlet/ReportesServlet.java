/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.ReporteDAO;
import Exceptions.EntidadNoEncontradaException;
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
@WebServlet("/reporte/*")
public class ReportesServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        
        ReporteDAO reportedao = new ReporteDAO();
        try {
            if (path != null && !path.equals("/")) {
                String[] partes = path.substring(1).split("/");
                if (partes[0].equals("entre-fechas")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getReservacionesEntreFechas(fechaInicial, fechaFinal);
                    response.getWriter().write(gson.toJson(reservaciones));
                } else if (partes[0].equals("agente-ventas")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getReservacionesDeAgenteRegistro(fechaInicial, fechaFinal);
                    response.getWriter().write(gson.toJson(reservaciones));
                } else if (partes[0].equals("ganancias")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getGananciasDeAgenteRegistro(fechaInicial, fechaFinal);
                    response.getWriter().write(gson.toJson(reservaciones));
                } else if (partes[0].equals("costo-paquete")) {
                    int idPaquete = Integer.parseInt(partes[1]);
                    
                    int costoPaquete = reportedao.getCostosPaquete(idPaquete);
                    response.getWriter().write(String.valueOf(costoPaquete));
                } else if (partes[0].equals("paquete-mas-vendido")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getPaqueteMasVendido(fechaInicial, fechaFinal);
                    response.getWriter().write(gson.toJson(reservaciones));
                } else if (partes[0].equals("paquete-menos-vendido")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<ReservacionDB> reservaciones = reportedao.getPaqueteMenosVendido(fechaInicial, fechaFinal);
                    response.getWriter().write(gson.toJson(reservaciones));
                } else if (partes[0].equals("ocupacion-destino")) {
                    String fechaInicial = partes[1];
                    String fechaFinal = partes[2];
                    
                    List<OcupacionDestinoDB> destinos = reportedao.getOcupacionDestino(fechaInicial, fechaFinal);
                    response.getWriter().write(gson.toJson(destinos));
                }
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
}
