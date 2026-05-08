/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.CancelacionDAO;
import Exceptions.DataBaseException;
import Modelos.Cancelacion.CancelacionDB;
import Modelos.Cancelacion.CancelacionRequest;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author millin-115
 */
@WebServlet("/cancelacion/*")
public class CancelacionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CancelacionDAO dao = new CancelacionDAO();
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            int idProyecto = Integer.parseInt(instrucciones[0]);
            String idFreelancer = instrucciones[1];
            CancelacionDB cancelacion = dao.getCancelacion(idProyecto, idFreelancer);
            response.getWriter().write(gson.toJson(cancelacion));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CancelacionDAO dao = new CancelacionDAO();
        CancelacionRequest cancelacionReq = gson.fromJson(request.getReader(), CancelacionRequest.class);
        try {
            dao.agregarCancelacion(cancelacionReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
