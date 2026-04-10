/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.pdf.PagopdfDAO;
import DAO.sql.PagoDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Pago.PagoDB;
import Model.Pago.PagoRequest;
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
@WebServlet("/pago/*")
public class PagoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        PagoDAO pagodao = new PagoDAO();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        try {
            if (path == null || path.equals("/")) {

            } else {
                int idReservacion = Integer.parseInt(path.substring(1));
                List<PagoDB> pagosReservacion = pagodao.getPagosPorReservacion(idReservacion);
                response.getWriter().write(gson.toJson(pagosReservacion));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        PagoDAO pagodao = new PagoDAO();
        PagopdfDAO pagopdf = new PagopdfDAO();
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pago.pdf");
        
        PagoRequest pagorequest = gson.fromJson(request.getReader(), PagoRequest.class);
        
        try {
            PagoDB pagodb = pagodao.crearRegistroPago(pagorequest);
            pagopdf.generarPDF(pagodb, response.getOutputStream());
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    
}
