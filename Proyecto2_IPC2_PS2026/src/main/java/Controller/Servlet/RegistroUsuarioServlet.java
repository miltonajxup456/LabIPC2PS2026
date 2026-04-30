/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.UsuarioDAO;
import Exceptions.DataBaseException;
import Exceptions.DataExistenteException;
import Exceptions.DataInexistenteException;
import Modelos.Habilidad.ListaHabilidades;
import Modelos.Usuario.ClienteRequest;
import Modelos.Usuario.FreelanceRequest;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;
import Servicios.UsuarioService;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author millin-115
 */
@WebServlet("/usuario/*")
public class RegistroUsuarioServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioService service = new UsuarioService();
        String path = request.getPathInfo();
        String idUsuario = path.substring(1);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            UsuarioDB usuario = service.buscarUsuario(idUsuario);
            response.getWriter().write(gson.toJson(usuario));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | DataInexistenteException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            String mensaje = "{ \"mensaje\": " +e.getMessage()+ "}";
            response.getWriter().write(gson.toJson(mensaje));
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioRequest usuarioRequest = gson.fromJson(request.getReader(), UsuarioRequest.class);
        
        UsuarioService service = new UsuarioService();
        
        try {
            service.validarCrearUsuario(usuarioRequest);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | DataExistenteException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioDAO usuariodao = new UsuarioDAO();
        String path = request.getPathInfo();
        
        String[] partes = path.split("/");
        
        try {
            if (partes[0].equals("actualizar-cliente")) {
                ClienteRequest clienteReq = gson.fromJson(request.getReader(), ClienteRequest.class);
                usuariodao.actualizarCliente(clienteReq, partes[1]);
            } else if (partes[0].equals("actualizar-freelancer")) {
                FreelanceRequest frelanceReq = gson.fromJson(request.getReader(), FreelanceRequest.class);
                usuariodao.actualizarFreelancer(frelanceReq, partes[1]);
            } else if (partes[0].equals("habilidades")) {
                ListaHabilidades lista = gson.fromJson(request.getReader(), ListaHabilidades.class);
                usuariodao.agregarHabilidades(lista, partes[1]);
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDAO usuariodao = new UsuarioDAO();
        String path = request.getPathInfo();
        String[] partes = path.split("/");
        
        try {
            if (partes[0].equals("habilidad")) {
                int idHabilidad = Integer.parseInt(partes[1]);
                String idFreelancer = partes[2];
                usuariodao.eliminarHabilidad(idHabilidad, idFreelancer);
            } else if (partes[0].equals("usuario")) {
                usuariodao.eliminarUsuario(partes[1]);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
