/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.PropuestaCategoriaDAO;
import Exceptions.DataBaseException;
import Modelos.Propuestas.Propuesta;
import Modelos.Propuestas.PropuestaCategoriaDB;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author millin-115
 */
@WebServlet("/propuesta-categoria/*")
public class PropuestaCategoriaServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaCategoriaDAO dao = new PropuestaCategoriaDAO();
        
        response.setContentType("appication/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<PropuestaCategoriaDB> propuestas = dao.getPropuestasCategoria();
            response.getWriter().write(gson.toJson(propuestas));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaCategoriaDAO dao = new PropuestaCategoriaDAO();
        Propuesta propuestaReq = gson.fromJson(request.getReader(), Propuesta.class);
        
        try {
            dao.agregarPropuesta(propuestaReq); 
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaCategoriaDAO dao = new PropuestaCategoriaDAO();
        Propuesta propuestaReq = gson.fromJson(request.getReader(), Propuesta.class);
        String path = request.getPathInfo().substring(1);
        
        try {
            int idPropuesta = Integer.parseInt(path);
            dao.actualizarPropuesta(propuestaReq, idPropuesta);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PropuestaCategoriaDAO dao = new PropuestaCategoriaDAO();
        String path = request.getPathInfo().substring(1);
        
        try {
            int idPropuesta = Integer.parseInt(path);
            dao.eliminarPropuesta(idPropuesta);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
