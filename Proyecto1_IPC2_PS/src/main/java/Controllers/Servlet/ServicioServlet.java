/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.ServicioPaqueteDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.ServicioPaquete.ServicioPaqueteDB;
import Model.ServicioPaquete.ServicioPaqueteRequest;
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
@WebServlet("/servicio/*")
public class ServicioServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        int idPaquete = Integer.parseInt(path.substring(1));
        
        ServicioPaqueteDAO serviciodao = new ServicioPaqueteDAO();
        
        try {
            List<ServicioPaqueteDB> servicios = serviciodao.getServiciosPaquete(idPaquete);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(servicios));
        } catch (EntidadNoEncontradaException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ServicioPaqueteRequest servicioRequest = gson.fromJson(request.getReader(), ServicioPaqueteRequest.class);
        
        ServicioPaqueteDAO serviciodao = new ServicioPaqueteDAO();
        try {
            serviciodao.guardarServicio(servicioRequest);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            System.out.println(e);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        int idServicio = Integer.parseInt(path.substring(1));
        
        Gson gson = new Gson();
        ServicioPaqueteRequest servicioRequest = gson.fromJson(request.getReader(), ServicioPaqueteRequest.class);
        ServicioPaqueteDAO serviciodao = new ServicioPaqueteDAO();
        
        try {
            serviciodao.actualizarPaquete(servicioRequest, idServicio);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println(e);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        int idServicio = Integer.parseInt(path.substring(1));
        
        ServicioPaqueteDAO serviciodao = new ServicioPaqueteDAO();
        try {
            serviciodao.eliminarServicioDePaquete(idServicio);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println(e);
        }
    }
    
}
