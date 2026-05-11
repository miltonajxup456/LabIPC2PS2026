/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.ProyectoDAO;
import Exceptions.DataBaseException;
import Exceptions.DataInexistenteException;
import Modelos.Proyecto.ProyectoRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author millin-115
 */
public class ProyectoService {
    
    private final ProyectoDAO daoProy;
    
    public ProyectoService() {
        daoProy = new ProyectoDAO();
    }
    
    public void modificarProyecto(ProyectoRequest request, int idProyecto) throws DataBaseException {
        daoProy.actualizarProyecto(request, idProyecto);
        List<Integer> habRequest = request.getHabilidades();
        List<Integer> habProyecto = daoProy.getHabilidades(idProyecto);
        List<Integer> agregados = new ArrayList<>();
        List<Integer> eliminados = new ArrayList<>();
        
        for (int i = 0; i < habRequest.size(); i++) {
            int idActual = habRequest.get(i);
            if (!existeEnLista(habProyecto, idActual)) {
                agregados.add(idActual);
            }
        }
        
        for (int i = 0; i < habProyecto.size(); i++) {
            int idActual = habProyecto.get(i);
            if (!existeEnLista(habRequest, idActual)) {
                eliminados.add(idActual);
            }
        }
        
        daoProy.agregarHabilidades(agregados, idProyecto);
        daoProy.eliminarHabiilidades(eliminados, idProyecto);
    }
    
    private boolean existeEnLista(List<Integer> lista, int idHabilidad) {
        for (int i = 0; i < lista.size(); i++) {
            int habLista = lista.get(i);
            if (habLista == idHabilidad) {
                return true;
            }
        }
        return false;
    }
            
    
}
