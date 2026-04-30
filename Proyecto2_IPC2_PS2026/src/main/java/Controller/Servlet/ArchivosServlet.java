/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author millin-115
 */
@WebServlet("/archivos/*")
public class ArchivosServlet extends HttpServlet {
    
    private static final String PATH = "/home/millin-115/Entregas-Proyecto2/";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        Part partFile = request.getPart("entrega");
        
        String path = request.getPathInfo();
        String[] informacion = path.split("/");
        String idProyecto = informacion[0];
        
        String pathCarpeta = PATH + "Proyecto " + idProyecto;
        File file = new File(pathCarpeta);
        if (!file.exists()) {
            file.mkdirs();
        }
        
        
        
        String nombreArchivo = "Entrega " + "" + ".zip"; 
        File archivo = new File(file, nombreArchivo);
        if (!archivo.exists()) {
            archivo.mkdirs();
        }
        
        InputStream input = partFile.getInputStream();
    }
    
}
