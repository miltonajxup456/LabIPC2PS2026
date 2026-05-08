/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.EntregaLinkDAO;
import Exceptions.DataBaseException;
import Modelos.Entrega.EntregaLinkDB;
import Modelos.Entrega.EntregaLinkRequest;
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
@WebServlet("/entrega-link/*")
public class EntregaLinkServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        EntregaLinkDAO dao = new EntregaLinkDAO();
        String path = request.getPathInfo().substring(1);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int idProyecto = Integer.parseInt(path);
            List<EntregaLinkDB> entregas = dao.entregasProyecto(idProyecto);
            response.getWriter().write(gson.toJson(entregas));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        EntregaLinkDAO dao = new EntregaLinkDAO();
        EntregaLinkRequest entregaReq = gson.fromJson(request.getReader(), EntregaLinkRequest.class);
        
        try {
            dao.agregarEntrega(entregaReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
