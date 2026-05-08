/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.ProyectoDAO;
import Exceptions.DataBaseException;
import Exceptions.DataInexistenteException;
import Modelos.Proyecto.ProyectoDB;
import Modelos.Proyecto.ProyectoRequest;
import Servicios.UsuarioService;
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
@WebServlet("/proyecto/*")
public class ProyectoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ProyectoDAO proyectodao = new ProyectoDAO();
        
        response.setContentType("appllication/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo().substring(1);
        String[] partes = path.split("/");
        
        try {
            if (partes[0].equals("todos-los-proyectos")) {
                List<ProyectoDB> proyectos = proyectodao.getTodosLosProyectos();
                response.getWriter().write(gson.toJson(proyectos));
            } else if (partes[0].equals("cliente")) {
                List<ProyectoDB> proyectos = proyectodao.getProyectosCliente(partes[1]);
                response.getWriter().write(gson.toJson(proyectos));
            } else if (partes[0].equals("freelancer")) {
                List<ProyectoDB> proyectos = proyectodao.getProyectosFreelancer(partes[1]);
                response.getWriter().write(gson.toJson(proyectos));
            } else if (partes[0].equals("paquete-proyecto")) {
                //List<PaqueteProyecto> paquete = 
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
        ProyectoDAO proyectodao = new ProyectoDAO();
        ProyectoRequest proyectoReq = gson.fromJson(request.getReader(), ProyectoRequest.class);
        
        try {
            proyectodao.crearProyecto(proyectoReq);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        UsuarioService service = new UsuarioService();
        ProyectoDAO proyectodao = new ProyectoDAO();
        ProyectoRequest proyectoReq = gson.fromJson(request.getReader(), ProyectoRequest.class);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        try {
            int idProyecto = Integer.parseInt(instrucciones[1]);
            
            if (instrucciones[0].equals("actualizacion")) {
                proyectodao.actualizarProyecto(proyectoReq, idProyecto);
            } else if (instrucciones[0].equals("proyecto-aceptado")) {
                service.actualizarSaldo(instrucciones[0], idProyecto, proyectoReq);
                proyectodao.aceptarPropuesta(proyectoReq, idProyecto);
            } else if (instrucciones[0].equals("proyecto-rechazado")) {
                proyectodao.actualizarEstadoProyecto(3, idProyecto);
            } else if (instrucciones[0].equals("proyecto-completado")) {
                service.actualizarSaldo(instrucciones[0], idProyecto, proyectoReq);
                proyectodao.actualizarEstadoProyecto(5, idProyecto);
            } else if (instrucciones[0].equals("proyecto-cancelado")) {
                service.actualizarSaldo(instrucciones[0], idProyecto, proyectoReq);
                proyectodao.actualizarEstadoProyecto(6, idProyecto);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ProyectoDB proyecto = proyectodao.getProyectoPorId(idProyecto);
            response.getWriter().write(gson.toJson(proyecto));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | DataInexistenteException | NumberFormatException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProyectoDAO proyectodao = new ProyectoDAO();
        
        String path = request.getPathInfo();
        int idProyecto = Integer.parseInt(path.substring(1));
        
        try {
            proyectodao.eliminarProyecto(idProyecto);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
