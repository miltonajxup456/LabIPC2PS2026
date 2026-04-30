/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.PropuestaProyectoDAO;
import Exceptions.DataBaseException;
import Modelos.PropuestaProyecto.PropuestaDB;
import Modelos.PropuestaProyecto.PropuestaRequest;
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
@WebServlet("/propuesta/*")
public class PropuestaServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaProyectoDAO dao = new PropuestaProyectoDAO();
        
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (instrucciones[0].equals("propuestas-proyecto")) {
                int idProyecto = Integer.parseInt(instrucciones[1]);
                List<PropuestaDB> propuestas = dao.getPropuestasProyecto(idProyecto);
                response.getWriter().write(gson.toJson(propuestas));
            } else if (instrucciones[0].equals("propuesta-freelancer")) {
                int idProyecto = Integer.parseInt(instrucciones[1]);
                String idFreelancer = instrucciones[2];
                PropuestaDB propuesta = dao.getPropuestaFrelancer(idProyecto, idFreelancer);
                response.getWriter().write(gson.toJson(propuesta));
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
        PropuestaProyectoDAO dao = new PropuestaProyectoDAO();
        PropuestaRequest req = gson.fromJson(request.getReader(), PropuestaRequest.class);
        
        try {
            dao.crearPropuesta(req);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaProyectoDAO dao = new PropuestaProyectoDAO();
        PropuestaRequest req = gson.fromJson(request.getReader(), PropuestaRequest.class);
        
        String path = request.getPathInfo().substring(1);
        
        try {
            int idProyecto = Integer.parseInt(path);
            dao.actualizarPropuesta(req, idProyecto);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PropuestaProyectoDAO dao = new PropuestaProyectoDAO();
        
        String path = request.getPathInfo().substring(1);
        
        try {
            int idPropuesta = Integer.parseInt(path);
            dao.eliminarPropuesta(idPropuesta);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
