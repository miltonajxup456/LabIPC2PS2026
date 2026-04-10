/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Servlet;

import DAO.sql.ReservacionDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Reservacion.ReservacionDB;
import Model.Reservacion.ReservacionRequest;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
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
@WebServlet("/reservacion/*")
public class ReservacionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        
        ReservacionDAO reservaciondao = new ReservacionDAO();
        
        try {
            if (path == null || path.equals("/")) {
                List<ReservacionDB> reservaciones = reservaciondao.getReservaciones();
                String reservacionesjson = gson.toJson(reservaciones);

                response.getWriter().write(reservacionesjson);
            } else {

                String[] partes = path.substring(1).split("/");
                if (partes[0].equals("dpi")) {
                    String dpi = partes[1];
                    
                    List<ReservacionDB> historial = reservaciondao.getReservacionesPorDPI(dpi);
                    response.getWriter().write(gson.toJson(historial));
                    
                } else if (partes[0].equals("fecha")) {
                    String fecha = partes[1];
                    
                    List<ReservacionDB> reservaciones = reservaciondao.getReservacionesFecha(fecha);
                    response.getWriter().write(gson.toJson(reservaciones));
                } else if (partes[0].equals("fecha-destino")) {
                    String fecha = partes[1];
                    String destino = partes[2];
                    int idDestino = Integer.parseInt(destino);
                    
                    List<ReservacionDB> reservaciones = reservaciondao.getReservacionesFechaDestino(fecha, idDestino);
                    response.getWriter().write(gson.toJson(reservaciones));
                } 
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ReservacionRequest reservacionRequest = gson.fromJson(request.getReader(), ReservacionRequest.class);
        
        ReservacionDAO reservaciondao = new ReservacionDAO();
        
        try {
            reservaciondao.crearReservacion(reservacionRequest);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        int id = Integer.parseInt(path.substring(1));
        
        ReservacionDAO reservaciondao = new ReservacionDAO();
        
        try {
            reservaciondao.borrarReservacion(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadNoEncontradaException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ReservacionRequest reservacionRequest = gson.fromJson(request.getReader(), ReservacionRequest.class);
        
        ReservacionDAO reservaciondao = new ReservacionDAO();
        
        try {
            reservaciondao.actualizarReservacion(reservacionRequest);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DatosInvalidosException e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
