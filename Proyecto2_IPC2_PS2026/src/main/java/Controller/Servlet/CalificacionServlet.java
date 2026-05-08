/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.CalificacionDAO;
import Exceptions.DataBaseException;
import Modelos.Calificacion.CalificacionDB;
import Modelos.Calificacion.CalificacionRequest;
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
@WebServlet("/calificacion/*")
public class CalificacionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CalificacionDAO dao = new CalificacionDAO();
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        response.setContentType("appication/json");
        response.setCharacterEncoding("UTF-8");
        try {
            if (instrucciones[0].equals("freelancer")) {
                String idFreelancer = instrucciones[1];
                List<CalificacionDB> calificaciones = dao.getCalificacionesFreelancer(idFreelancer);
                response.getWriter().write(gson.toJson(calificaciones));
            } else if (instrucciones[0].equals("proyecto")) {
                int idProyecto = Integer.parseInt(instrucciones[1]);
                CalificacionDB calificacion = dao.getCalificacionProyecto(idProyecto);
                response.getWriter().write(gson.toJson(calificacion));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CalificacionDAO dao = new CalificacionDAO();
        CalificacionRequest califReq = gson.fromJson(request.getReader(), CalificacionRequest.class);
        
        try {
            dao.agregarCalificacion(califReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CalificacionDAO dao = new CalificacionDAO();
        CalificacionRequest califReq = gson.fromJson(request.getReader(), CalificacionRequest.class);
        String path = request.getPathInfo().substring(1);
        
        try {
            int idCalificacion = Integer.parseInt(path);
            dao.modificarCalificacion(califReq, idCalificacion);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
