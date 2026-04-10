/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio.Login;

import DAO.sql.UsuarioDAO;
import Exceptions.LoginInvalidoException;
import Model.Login.UsuarioDB;
import Model.Login.UsuarioRequest;

/**
 *
 * @author millin-115
 */
public class ValidadorLogin {
    
    public UsuarioDB validarLogin(UsuarioRequest solicitante) throws LoginInvalidoException {
        
        UsuarioDB usuario = buscarSolicitante(solicitante);
        
        if (!usuario.getPassword_user().equals(solicitante.getPassword_user())) {
            throw new LoginInvalidoException("La constraseña es incorrecta");
        }
        return usuario;
    }
    
    private UsuarioDB buscarSolicitante(UsuarioRequest loginRequest) throws LoginInvalidoException {
        
        UsuarioDAO logindb = new UsuarioDAO();
        
        UsuarioDB solicitante = logindb.consultarUsuario(loginRequest);
        
        if (solicitante == null) {
            throw new LoginInvalidoException("No existe un usuario registrado con ese nombre");
        }
        
        return solicitante;
    }
    
}
