/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.CategoriaDAO;
import Exceptions.DataBaseException;
import Exceptions.DataInexistenteException;
import Modelos.Categoria.CategoriaDB;

/**
 *
 * @author millin-115
 */
public class CategoriaService {
    
    public CategoriaDB buscarCategoria(int idCategoria) throws DataBaseException, DataInexistenteException {
        CategoriaDAO categoriadao = new CategoriaDAO();
        CategoriaDB categoria = categoriadao.getCategoriaProyecto(idCategoria);
        
        if (categoria == null) {
            throw new DataInexistenteException("La categoria con id "+idCategoria+" no existe");
        }
        return categoria;
    }
    
}
