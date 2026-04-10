/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import com.google.gson.Gson;
import DAO.sql.UsuarioDAO;
import Exceptions.LoginInvalidoException;
import Model.Login.UsuarioDB;
import Model.Login.UsuarioRequest;
import Servicio.Login.ValidadorLogin;
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
@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO logindb = new UsuarioDAO();
        List<UsuarioDB> usuarios = logindb.getUsuarios();
        
        Gson gson = new Gson();
        String json = gson.toJson(usuarios);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        
        UsuarioRequest userRequest = gson.fromJson(reader, UsuarioRequest.class);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ValidadorLogin validar = new ValidadorLogin();
        
        try {
            UsuarioDB usuario = validar.validarLogin(userRequest);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(usuario));
        } catch (LoginInvalidoException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println(e);
        }
    }
    
}
