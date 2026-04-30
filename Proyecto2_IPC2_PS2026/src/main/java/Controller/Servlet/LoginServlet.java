/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import Exceptions.DataBaseException;
import Exceptions.DataInexistenteException;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;
import Servicios.LoginService;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author millin-115
 */
@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioRequest usuarioRequest = gson.fromJson(request.getReader(), UsuarioRequest.class);
        LoginService service = new LoginService();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> resp = new HashMap<>();
        
        try {
            UsuarioDB usuariodb = service.validarContraseña(usuarioRequest);
            resp.put("mensaje", "ok");
            resp.put("data", usuariodb);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | DataInexistenteException e) {
            resp.put("mensaje", e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        
        response.getWriter().write(gson.toJson(resp));
    }   
    
}
