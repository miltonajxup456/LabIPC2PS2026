/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.UsuarioDAO;
import Exceptions.DataBaseException;
import Exceptions.DataExistenteException;
import Exceptions.DataInexistenteException;
import Modelos.Usuario.ClienteDB;
import Modelos.Usuario.FreelancerDB;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;

/**
 *
 * @author millin-115
 */
public class UsuarioService {
    
    public void validarCrearUsuario(UsuarioRequest request) throws DataBaseException, DataExistenteException {
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        UsuarioDB usuario = usuariodao.buscarPorNombreUsuario(request.getNombreUsuario());
        
        if (usuario != null) {
            throw new DataExistenteException("El nombre de Usuario "+request.getNombreUsuario()+" ya ha sido utilizado, elige otro");
        }
        
        usuariodao.crearUsuario(request);
    }
    
    public UsuarioDB buscarUsuario(String id) throws DataBaseException, DataInexistenteException {
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        UsuarioDB usuariodb = usuariodao.buscarPorNombreUsuario(id);
        
        if (usuariodb == null) {
            throw new DataInexistenteException("El usuario no existe");
        }
        
        if (usuariodb.getTipoRol().equals("CLIENTE")) {
            ClienteDB complemento = usuariodao.armarCliente(id);
            if (complemento != null) {
                ClienteDB cliente = new ClienteDB(usuariodb);
                cliente.setDescripcionEmpresa(complemento.getDescripcionEmpresa());
                cliente.setIndustriaPerteneciente(complemento.getIndustriaPerteneciente());
                cliente.setSitioWeb(complemento.getSitioWeb());
                return cliente;
            }
        } else if (usuariodb.getTipoRol().equals("FREELANCER")) {
            FreelancerDB complemento = usuariodao.armarFreelance(id);
            if (complemento != null) {
                FreelancerDB freelancer = new FreelancerDB(usuariodb);
                freelancer.setBiografia(complemento.getBiografia());
                freelancer.setTarifaReferencial(complemento.getTarifaReferencial());
                return freelancer;
            }
        }
        return usuariodb;
    }
    
}
