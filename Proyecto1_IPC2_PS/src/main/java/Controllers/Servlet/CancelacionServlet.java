/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.CancelacionDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Cancelacion.CancelacionDB;
import Model.Cancelacion.CancelacionRequest;
import com.google.gson.Gson;
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
@WebServlet("/cancelacion/*")
public class CancelacionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CancelacionDAO cancelaciondao = new CancelacionDAO();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        
        try {
            if (path != null && !path.equals("/")) {
                String[] partes = path.substring(1).split("/");
                String fechaInicial = partes[0];
                String fechaFinal = partes[1];
                
                List<CancelacionDB> cancelaciones = cancelaciondao.getCancelacionesEntreFechas(fechaInicial, fechaFinal);
                response.getWriter().write(gson.toJson(cancelaciones));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CancelacionDAO cancelaciondao = new CancelacionDAO();
        CancelacionRequest cancelacionRequest = gson.fromJson(request.getReader(), CancelacionRequest.class);
        try {
            cancelaciondao.agregarReservacion(cancelacionRequest);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
