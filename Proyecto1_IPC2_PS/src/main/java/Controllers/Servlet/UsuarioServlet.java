/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.UsuarioDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Login.UsuarioDB;
import Model.Login.UsuarioRequest;
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
@WebServlet("/usuario/*")
public class UsuarioServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        List<UsuarioDB> usuarios = usuariodao.getUsuarios();
        response.getWriter().write(gson.toJson(usuarios));
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioRequest usuarioRequest = gson.fromJson(request.getReader(), UsuarioRequest.class);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        try {
            usuariodao.crearUsuario(usuarioRequest);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDAO usuariodao = new UsuarioDAO();
        String path = request.getPathInfo();
        
        try {
            int idUsuario = Integer.parseInt(path.substring(1));
            usuariodao.eliminarUsuario(idUsuario);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioRequest usuarioRequest = gson.fromJson(request.getReader(), UsuarioRequest.class);
        
        String path = request.getPathInfo();
        int idUsuario = Integer.parseInt(path.substring(1));
    }
    
}
