/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import DAOs.EntregaDAO;
import Exceptions.DataBaseException;
import Modelos.Entrega.EntregaDB;
import Modelos.Entrega.EntregaRequest;
import Servicios.EntregaService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author millin-115
 */
@WebServlet("/entrega/*")
@MultipartConfig
public class EntregaServlet extends HttpServlet {
    
    private static final String PATH = "/home/millin-115/Entregas-Proyecto2/";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        Gson gson = new Gson();
        EntregaDAO dao = new EntregaDAO();
        String path = request.getPathInfo().substring(1);
        String[] instrucciones = path.split("/");
        
        try {
            if (instrucciones[0].equals("entregas-proyecto")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                int idProyecto = Integer.parseInt(instrucciones[1]);
                List<EntregaDB> entregas = dao.getEntregasProyecto(idProyecto);
                response.getWriter().write(gson.toJson(entregas));
            } else if (instrucciones[0].endsWith("entrega")) {
                EntregaService service = new EntregaService();
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment; filename=entrega.zip");
                int idEntrega = Integer.parseInt(instrucciones[1]);
                EntregaDB entrega = dao.entregaPorId(idEntrega);
                service.descargaDeEntrega(response.getOutputStream(), entrega.getPathArchivo());
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataBaseException | NumberFormatException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        Gson gson = new Gson();
        EntregaDAO dao = new EntregaDAO();
        Part jsonPart = request.getPart("registro-entrega");
        Part partFile = request.getPart("entrega");
        
        EntregaRequest entregaReq = gson.fromJson(new InputStreamReader(jsonPart.getInputStream()), EntregaRequest.class);
        
        int idEntrega = 0;
        
        try (InputStream input = partFile.getInputStream();) {
            
            EntregaDB entrega = dao.entregarProyecto(entregaReq);
            idEntrega = entrega.getIdEntrega();
            
            String carpeta = "Entregas Proyecto " + entrega.getProyecto();
            String pathCarpeta = PATH + carpeta;
            File file = new File(pathCarpeta);
            if (!file.exists()) {
                file.mkdirs();
            }
            
            String nombreArchivo = "Entrega_" + entrega.getFreelancer() + "_" + entrega.getIdEntrega() + ".zip";
            File archivo = new File(pathCarpeta, nombreArchivo);
            
            try (FileOutputStream outPut = new FileOutputStream(archivo)) {
                byte[] buffer = new byte[8192];
                int byteReader;
                
                while ((byteReader = input.read(buffer)) != -1) {
                    outPut.write(buffer, 0, byteReader);
                }
            }
            dao.actualizarEntrega(archivo.getAbsolutePath(), idEntrega, entrega.getProyecto());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            System.out.println(e);
            dao.eliminarEntrega(idEntrega);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la entrega");
        }
    }
    
}
