/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.HabilidadDAO;
import Exceptions.DataBaseException;
import Modelos.Habilidad.HabilidadDB;
import Modelos.Habilidad.HabilidadRequest;
import Modelos.Habilidad.ListaHabilidades;
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
@WebServlet("/habilidad/*")
public class HabilidadServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        HabilidadDAO dao = new HabilidadDAO();
        
        String path = request.getPathInfo().substring(1);
        String[] partes = path.split("/");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (partes[0].equals("habilidades")) {
                List<HabilidadDB> habilidades = dao.getHabilidades();
                response.getWriter().write(gson.toJson(habilidades));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        HabilidadDAO dao = new HabilidadDAO();
        HabilidadRequest habilidadReq = gson.fromJson(request.getReader(), HabilidadRequest.class);
        
        try {
            dao.crearHabilidad(habilidadReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        HabilidadDAO dao = new HabilidadDAO();
        HabilidadRequest habilidadReq = gson.fromJson(request.getReader(), HabilidadRequest.class);
        
        String path = request.getPathInfo().substring(1);
        
        try {
            int idHabilidad = Integer.parseInt(path);
            dao.actualizarHabilidad(habilidadReq, idHabilidad);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HabilidadDAO dao = new HabilidadDAO();
        String path = request.getPathInfo().substring(1);
        
        try {
            int idHabilidad = Integer.parseInt(path);
            dao.eliminarHabilidad(idHabilidad);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
