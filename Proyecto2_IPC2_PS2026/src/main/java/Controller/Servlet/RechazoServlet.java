/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.RechazoDAO;
import Exceptions.DataBaseException;
import Modelos.Rechazo.RechazoDB;
import Modelos.Rechazo.RechazoRequest;
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
@WebServlet("/rechazo/*")
public class RechazoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        RechazoDAO dao = new RechazoDAO();
        String path = request.getPathInfo().substring(1);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            int idProyecto = Integer.parseInt(path);
            List<RechazoDB> rechazos = dao.getRechazosProyecto(idProyecto);
            response.getWriter().write(gson.toJson(rechazos));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        RechazoDAO dao = new RechazoDAO();
        RechazoRequest rechazoReq = gson.fromJson(request.getReader(), RechazoRequest.class);
        try {
            dao.agregarRechazo(rechazoReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
