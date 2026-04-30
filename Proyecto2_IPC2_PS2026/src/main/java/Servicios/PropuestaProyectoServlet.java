/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

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
@WebServlet("/propuesta-proyecto/*")
public class PropuestaProyectoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaProyectoDAO propuestadao = new PropuestaProyectoDAO();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        int idProyecto = Integer.parseInt(path.substring(1));
        
        try {
            List<PropuestaDB> propuestas = propuestadao.getPropuestasProyecto(idProyecto);
            response.getWriter().write(gson.toJson(propuestas));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaRequest propuestaReq = gson.fromJson(request.getReader(), PropuestaRequest.class);
        PropuestaProyectoDAO propuestadao = new PropuestaProyectoDAO();
        
        try {
            propuestadao.crearPropuesta(propuestaReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaRequest propuestaReq = gson.fromJson(request.getReader(), PropuestaRequest.class);
        PropuestaProyectoDAO propuestadao = new PropuestaProyectoDAO();
        
        String path = request.getPathInfo();
        int idProyecto = Integer.parseInt(path.substring(1));
        
        try {
            propuestadao.actualizarPropuesta(propuestaReq, idProyecto);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PropuestaProyectoDAO propuestadao = new PropuestaProyectoDAO();
        
        String path = request.getPathInfo();
        int idPropuesta = Integer.parseInt(path.substring(1));
        
        try {
            propuestadao.eliminarPropuesta(idPropuesta);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
