/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.Archivos.LectorArchivosDAO;
import DAO.Archivos.RegistroDeErrores;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
@WebServlet("/archivo/*")
@MultipartConfig
public class ArchivosServlet extends HttpServlet {
    
    private static final String PATH = "/home/millin-115/LabIPC2PS2026/Proyecto1_IPC2_PS/target";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        
        Part partfile = request.getPart("archivo");
        String fileName = partfile.getSubmittedFileName();
        
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        
        InputStream input = partfile.getInputStream();
        LectorArchivosDAO lector = new LectorArchivosDAO();
        RegistroDeErrores errores = lector.leerArchivoTexto(input);
        
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(errores));
        
        //partfile.write(PATH + "/" +fileName);
    }
    
}
