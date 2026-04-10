/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.DestinoDAO;
import Exceptions.DatosInvalidosException;
import Model.Destino.DestinoDB;
import Model.Destino.DestinoRequest;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author millin-115
 */
@WebServlet("/destino/*")
public class DestinoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DestinoDAO destinodao = new DestinoDAO();
        List<DestinoDB> destinos = destinodao.getAllDestinos();
        
        Gson gson = new Gson();
        String jsonDestinos = gson.toJson(destinos);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonDestinos);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        
        DestinoRequest destinoReq = gson.fromJson(reader, DestinoRequest.class);
        
        DestinoDAO destinodao = new DestinoDAO();
        
        try {
            destinodao.crearDestino(destinoReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        DestinoRequest destinoRequest = gson.fromJson(request.getReader(), DestinoRequest.class);
        
        String path = request.getPathInfo();
        int id = Integer.parseInt(path.substring(1));
        
        DestinoDAO destinoDao = new DestinoDAO();
        
        try {
            destinoDao.actualizarDestino(id, destinoRequest);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        
        DestinoDB destinodb = gson.fromJson(request.getReader(), DestinoDB.class);
        
        DestinoDAO destinodao = new DestinoDAO();
        
        try {
            destinodao.borrarDestino(destinodb);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
}
