/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.CategoriaDAO;
import Exceptions.DataBaseException;
import Exceptions.DataInexistenteException;
import Modelos.Categoria.CategoriaDB;
import Modelos.Categoria.CategoriaRequest;
import Modelos.Habilidad.HabilidadDB;
import Servicios.CategoriaService;
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
@WebServlet("/categoria/*")
public class CategoriaServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CategoriaService service = new CategoriaService();
        CategoriaDAO categoriadao = new CategoriaDAO();
        
        String path = request.getPathInfo().substring(1);
        String[] partes = path.split("/");
        
        response.setContentType("application");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (partes[0].equals("categorias")) {
                List<CategoriaDB> categorias = categoriadao.getCategorias();
                response.getWriter().write(gson.toJson(categorias));
            } else if (partes[0].equals("por-id")) {
                int idCategoria = Integer.parseInt(partes[1]);
                CategoriaDB categoria = service.buscarCategoria(idCategoria);
                response.getWriter().write(gson.toJson(categoria));
            } else if (partes[0].equals("habilidades-proyecto")) {
                int idCategoria = Integer.parseInt(partes[1]);
                List<HabilidadDB> habilidades = categoriadao.getHabilidadesCategoria(idCategoria);
                response.getWriter().write(gson.toJson(habilidades));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | DataInexistenteException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaRequest categoriaReq = gson.fromJson(request.getReader(), CategoriaRequest.class);
        
        try {
            dao.crearCategoria(categoriaReq);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaRequest categoriaReq = gson.fromJson(request.getReader(), CategoriaRequest.class);
        
        String path = request.getPathInfo().substring(1);
        
        try {
            int idCategoria = Integer.parseInt(path);
            dao.actualizarCategoria(categoriaReq, idCategoria);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoriaDAO dao = new CategoriaDAO();
        String path = request.getPathInfo().substring(1);
        try {
            int idCategoria = Integer.parseInt(path);
            dao.eliminarCategoria(idCategoria);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
    
}
