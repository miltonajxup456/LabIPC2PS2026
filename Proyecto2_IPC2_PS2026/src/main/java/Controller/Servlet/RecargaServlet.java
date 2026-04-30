/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.RecargaDAO;
import Exceptions.DataBaseException;
import Modelos.Recarga.RecargaDB;
import Modelos.Recarga.RecargaRequest;
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
@WebServlet("/recarga/*")
public class RecargaServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        RecargaDAO dao = new RecargaDAO();
        
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (instrucciones[0].equals("todos-los-registros")) {
                List<RecargaDB> recargas = dao.getTodosLosRegistros();
                response.getWriter().write(gson.toJson(recargas));
            } else if (instrucciones[0].equals("registros-cliente")) {
                String idCliente = instrucciones[1];
                List<RecargaDB> recargas = dao.getRegistrosCliente(idCliente);
                response.getWriter().write(gson.toJson(recargas));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        RecargaDAO dao = new RecargaDAO();
        RecargaRequest req = gson.fromJson(request.getReader(), RecargaRequest.class);
        
        try {
            dao.agregarRegistro(req);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
