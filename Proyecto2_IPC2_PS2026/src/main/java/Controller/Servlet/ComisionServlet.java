/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.ComisionDAO;
import Exceptions.DataBaseException;
import Modelos.Comision.ComisionDB;
import Modelos.Comision.ComisionRequest;
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
@WebServlet("/comision/*")
public class ComisionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ComisionDAO dao = new ComisionDAO();
        
        String path = request.getPathInfo().substring(1);
        String[] partes = path.split("/");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (partes[0].equals("ultima-comision")) {
                ComisionDB comision = dao.getUltimaComision();
                response.getWriter().write(gson.toJson(comision));
            } else if (partes[0].equals("historial")) {
                List<ComisionDB> historial = dao.getComisiones();
                response.getWriter().write(gson.toJson(historial));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ComisionDAO dao = new ComisionDAO();
        ComisionRequest comisionReq = gson.fromJson(request.getReader(), ComisionRequest.class);
        
        try {
            dao.agregarComision(comisionReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
