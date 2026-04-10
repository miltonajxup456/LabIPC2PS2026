/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.ProveedorDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Proveedor.ProveedorDB;
import Model.Proveedor.ProveedorRequest;
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
@WebServlet("/proveedor/*")
public class ProveedorServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ProveedorDAO proveedordao = new ProveedorDAO();
        List<ProveedorDB> proveedores;
                
        String path = request.getPathInfo();
        
        try {
            if (path == null || path.equals("/")) {
                proveedores = proveedordao.getProveedores();
            } else {
                int idDestino = Integer.parseInt(path.substring(1));
                proveedores = proveedordao.getProveedoresPorPais(idDestino);
            }
            response.getWriter().write(gson.toJson(proveedores));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ProveedorRequest provRequest = gson.fromJson(request.getReader(), ProveedorRequest.class);
        
        ProveedorDAO proveedordao = new ProveedorDAO();
        
        try {
            proveedordao.agregarProveedor(provRequest);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        ProveedorDAO proveedordao = new ProveedorDAO();
        ProveedorRequest provRequest = gson.fromJson(request.getReader(), ProveedorRequest.class);
        
        String path = request.getPathInfo();
        int idProveedor = Integer.parseInt(path.substring(1));
        
        try {
            proveedordao.modificarProveedor(provRequest, idProveedor);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        int idProveedor = Integer.parseInt(path.substring(1));
        
        ProveedorDAO proveedordao = new ProveedorDAO();
        
        try {
            proveedordao.eliminarProveedor(idProveedor);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
