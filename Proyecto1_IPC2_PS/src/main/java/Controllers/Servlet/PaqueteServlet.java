/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.PaqueteTuristicoDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Paquete.PaqueteRequest;
import Model.Paquete.PaqueteTuristicoDB;
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
@WebServlet("/paquete/*")
public class PaqueteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PaqueteTuristicoDAO paquetedao = new PaqueteTuristicoDAO();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            List<PaqueteTuristicoDB> paquetes = paquetedao.getPaquetes();
            response.getWriter().write(gson.toJson(paquetes));
        } else {
        
            String[] partes = pathInfo.substring(1).split("/");

            switch (partes.length) {
                case 1:
                    int id = Integer.parseInt(partes[0]);
                    PaqueteTuristicoDB paquete = paquetedao.getPaquetePorId(id);
                    if (paquete != null) {
                        response.getWriter().write(gson.toJson(paquete));
                        response.setStatus(HttpServletResponse.SC_FOUND);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    }
                    break;
                case 2:
                    int idDestino = Integer.parseInt(partes[1]);
                    List<PaqueteTuristicoDB> paquetesDestino = paquetedao.getPaquetesDestino(idDestino);
                    response.getWriter().write(gson.toJson(paquetesDestino));
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        PaqueteRequest paqueteRequest = gson.fromJson(request.getReader(), PaqueteRequest.class);
        
        PaqueteTuristicoDAO paquetedao = new PaqueteTuristicoDAO();
        
        try {
            paquetedao.crearPaquete(paqueteRequest);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        PaqueteRequest paqueteRequest = gson.fromJson(request.getReader(), PaqueteRequest.class);
        
        String path = request.getPathInfo();
        int idPaquete = Integer.parseInt(path.substring(1));
        
        PaqueteTuristicoDAO paquetedao = new PaqueteTuristicoDAO();
        
        try {
            paquetedao.actualizarPaquete(paqueteRequest, idPaquete);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        int idPaquete = Integer.parseInt(path.substring(1));
        PaqueteTuristicoDAO paquetedao = new PaqueteTuristicoDAO();
        
        try {
            paquetedao.borrarPaquete(idPaquete);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
