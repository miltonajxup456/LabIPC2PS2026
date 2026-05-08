/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.UsuarioDAO;
import Exceptions.DataBaseException;
import Exceptions.DataInexistenteException;
import Exceptions.LoginException;
import Modelos.Usuario.ClienteDB;
import Modelos.Usuario.FreelancerDB;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author millin-115
 */
public class LoginService {
    
    public UsuarioDB validarContraseña(UsuarioRequest request) throws DataBaseException, DataInexistenteException, LoginException {
        
        if (request.getPasswordUser() == null) {
            throw new DataBaseException("No se pudo Acceder al sistema");
        }
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        UsuarioDB usuariodb = usuariodao.buscarPorNombreUsuario(request.getNombreUsuario());
        
        if (usuariodb == null) {
            throw new DataInexistenteException("No se pudo acceder");
        }
        
        if (usuariodb.isBaneo()) {
            throw new LoginException("No se puede acceder a esta cuenta porque el Usuario ha sido inhabilitado por el adminsitrador");
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        boolean contraseñaCorrecta = encoder.matches(request.getPasswordUser(), usuariodb.getPasswordUser());
        if (!contraseñaCorrecta) {
            throw new DataInexistenteException("No se pudo acceder al sistema");
        }
        
        if (usuariodb.getTipoRol().equals("CLIENTE")) {
            ClienteDB complemento = usuariodao.armarCliente(usuariodb.getNombreUsuario());
            if (complemento != null) {
                ClienteDB cliente = new ClienteDB(usuariodb);
                cliente.setDescripcionEmpresa(complemento.getDescripcionEmpresa());
                cliente.setIndustriaPerteneciente(complemento.getIndustriaPerteneciente());
                cliente.setSitioWeb(complemento.getSitioWeb());
                return cliente;
            }
        } else if (usuariodb.getTipoRol().equals("FREELANCER")) {
            FreelancerDB complemento = usuariodao.armarFreelance(usuariodb.getNombreUsuario());
            if (complemento != null) {
                FreelancerDB freelancer = new FreelancerDB(usuariodb);
                freelancer.setBiografia(complemento.getBiografia());
                freelancer.setTarifaReferencial(complemento.getTarifaReferencial());
                freelancer.setNivelExperiencia(complemento.getNivelExperiencia());
                freelancer.setTipoNivelExperiencia(complemento.getTipoNivelExperiencia());
                return freelancer;
            }
        }
        
        return usuariodb;
        
    }
    
}
