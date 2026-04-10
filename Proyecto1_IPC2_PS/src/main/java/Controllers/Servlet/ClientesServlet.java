/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.ClientesDAO;
import Exceptions.DatosInvalidosException;
import Model.Cliente.ClienteDB;
import Model.Cliente.ClienteRequest;
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
@WebServlet("/cliente/*")
public class ClientesServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClientesDAO clientesdao = new ClientesDAO();
        List<ClienteDB> todos = clientesdao.getClientes();
        
        Gson gson = new Gson();
        String todosGson = gson.toJson(todos);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        response.getWriter().write(todosGson);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        
        ClienteRequest clienteRequest = gson.fromJson(reader, ClienteRequest.class);
        ClientesDAO creacionCliente = new ClientesDAO();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            creacionCliente.crearClienteDB(clienteRequest);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().print("{\"error\":\"El usuario ya existe\"}");
        } catch (Exception e) {
            System.out.println(e);
            response.getWriter().print("{\"error\":\"Error interno al intentar guardar el Cliente\"}");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ClienteRequest clienteREquest = gson.fromJson(request.getReader(), ClienteRequest.class);
        ClientesDAO clientedao = new ClientesDAO();
        
        String path = request.getPathInfo();
        String dpiCliente = path.substring(1);
        
        try {
            clientedao.actualizarCliente(dpiCliente, clienteREquest);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String path = request.getPathInfo();
        
        String dpiCliente = path.substring(1);
        
        ClientesDAO borrarCliente = new ClientesDAO();
        
        try {
            borrarCliente.eliminarCliente(dpiCliente);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
