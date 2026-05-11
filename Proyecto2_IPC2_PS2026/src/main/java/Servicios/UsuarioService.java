/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.ComisionDAO;
import DAOs.HabilidadesUsuarioDAO;
import DAOs.HistorialComisionDAO;
import DAOs.PropuestaProyectoDAO;
import DAOs.RecargaDAO;
import DAOs.UsuarioDAO;
import Exceptions.DataBaseException;
import Exceptions.DataExistenteException;
import Exceptions.DataInexistenteException;
import Modelos.Comision.ComisionDB;
import Modelos.Habilidad.ListaHabilidades;
import Modelos.Historial.HistorialRequest;
import Modelos.PropuestaProyecto.PropuestaDB;
import Modelos.Proyecto.ProyectoRequest;
import Modelos.Recarga.RecargaRequest;
import Modelos.Usuario.ClienteDB;
import Modelos.Usuario.FreelancerDB;
import Modelos.Usuario.UsuarioDB;
import Modelos.Usuario.UsuarioRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author millin-115
 */
public class UsuarioService {
    
    private final UsuarioDAO daoUsu;
    
    public UsuarioService() {
        daoUsu = new UsuarioDAO();
    }
    
    public void crearAdministrador() throws DataBaseException {
        UsuarioDB admin = daoUsu.buscarPorNombreUsuario("Admin1");
        
        if (admin == null) {
            UsuarioRequest request = new UsuarioRequest();
            request.setNombreUsuario("Admin1");
            request.setNombre("Milton");
            request.setPasswordUser("1234");
            request.setCorreoElectronico("admin@gmail.com");
            request.setTelefono("12345678");
            request.setDireccion("Guatemala");
            request.setCui("87654321");
            request.setFechaNac("2000-01-01");
            request.setRol(1);
            daoUsu.crearUsuario(request);
        }
    }
    
    public void validarCrearUsuario(UsuarioRequest request) throws DataBaseException, DataExistenteException {
        UsuarioDB usuario = daoUsu.buscarPorNombreUsuario(request.getNombreUsuario());
        
        if (usuario != null) {
            throw new DataExistenteException("El nombre de Usuario "+request.getNombreUsuario()+" ya ha sido utilizado, elige otro");
        }
        
        daoUsu.crearUsuario(request);
    }
    
    public List<UsuarioDB> getUsuarios() throws DataBaseException, DataInexistenteException {
        List<UsuarioDB> usuarios = daoUsu.getUsuarios();
        int repeticiones = usuarios.size();
        for (int i = 0; i < repeticiones; i++) {
            UsuarioDB actual = usuarios.get(i);
            UsuarioDB completo = armarUsuario(actual, actual.getNombreUsuario());
            usuarios.set(i, completo);
        }
        return usuarios;
    }
    
    public UsuarioDB buscarUsuario(String idUsuario) throws DataBaseException, DataInexistenteException {
        UsuarioDB usuariodb = daoUsu.buscarPorNombreUsuario(idUsuario);
        
        if (usuariodb == null) {
            throw new DataInexistenteException("El usuario no existe");
        }
        
        usuariodb = armarUsuario(usuariodb, idUsuario);
        return usuariodb;
    }
    
    private UsuarioDB armarUsuario(UsuarioDB usuariodb, String idUsuario) throws DataBaseException, DataInexistenteException {
        if (usuariodb.getTipoRol().equals("CLIENTE")) {
            ClienteDB complemento = daoUsu.armarCliente(idUsuario);
            if (complemento != null) {
                ClienteDB cliente = new ClienteDB(usuariodb);
                cliente.setDescripcionEmpresa(complemento.getDescripcionEmpresa());
                cliente.setIndustriaPerteneciente(complemento.getIndustriaPerteneciente());
                cliente.setSitioWeb(complemento.getSitioWeb());
                return cliente;
            }
        } else if (usuariodb.getTipoRol().equals("FREELANCER")) {
            FreelancerDB complemento = daoUsu.armarFreelance(idUsuario);
            if (complemento != null) {
                FreelancerDB freelancer = new FreelancerDB(usuariodb);
                freelancer.setBiografia(complemento.getBiografia());
                freelancer.setTarifaReferencial(complemento.getTarifaReferencial());
                freelancer.setNivelExperiencia(complemento.getNivelExperiencia());
                freelancer.setTipoNivelExperiencia(complemento.getTipoNivelExperiencia());
                freelancer.setCalificacionPromedio(complemento.getCalificacionPromedio());
                return freelancer;
            }
        }
        return usuariodb;
    }
    
    public void revisarHabilidades(ListaHabilidades lista, String idFreelancer) throws DataBaseException {
        
        HabilidadesUsuarioDAO dao = new HabilidadesUsuarioDAO();
        List<Integer> habilidades = dao.getHabilidadesFreelancer(idFreelancer);
        
        List<Integer> request = lista.getListaIds();
        List<Integer> agregados = new ArrayList();
        List<Integer> eliminados = new ArrayList();
        
        for (int i = 0; i < request.size(); i++) {
            int id = request.get(i);
            if (!existeEnLista(habilidades, id)) {
                agregados.add(id);
            }
        }
        
        for (int i = 0; i < habilidades.size(); i++) {
            int id = habilidades.get(i);
            if (!existeEnLista(request, habilidades.get(i))) {
                eliminados.add(id);
            }
        }
        
        dao.agregarHabilidades(agregados, idFreelancer);
        
        dao.eliminarHabilidad(eliminados, idFreelancer);
    }
    
    private boolean existeEnLista(List<Integer> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) == id) {
                return true;
            }
        }
        return false;
    }
    
    public UsuarioDB agregarRecarga(RecargaRequest request) throws DataBaseException, DataInexistenteException {
        RecargaDAO daoRec = new RecargaDAO();
        
        UsuarioDB usuario = buscarUsuario(request.getCliente());
        
        double saldoActual = usuario.getSaldo() + request.getMonto();
        
        daoRec.agregarRegistro(request, saldoActual);
        
        usuario.setSaldo(saldoActual);
        
        return usuario;
    }
    
    public void actualizarSaldo(String instruccion, int idProyecto, ProyectoRequest request) throws DataBaseException, DataInexistenteException {
        PropuestaProyectoDAO daoProp = new PropuestaProyectoDAO();
        
        UsuarioDB cliente = buscarUsuario(request.getCliente());
        PropuestaDB propuesta = daoProp.getPropuestaFrelancer(idProyecto, request.getFreelancer());

        if (propuesta == null) {
            throw new DataInexistenteException("No existe una propuesta del freelancer " + request.getFreelancer() + " para el proyecto " + idProyecto);
        }

        if (instruccion.endsWith("proyecto-aceptado")) {
            double saldoActual = cliente.getSaldo() - propuesta.getMontoOfertado();
            revision(saldoActual);
            daoUsu.actualizarSaldoUsuario(saldoActual, cliente.getNombreUsuario());
        } else if (instruccion.equals("proyecto-cancelado")) {
            double saldoActual = cliente.getSaldo() + propuesta.getMontoOfertado();
            daoUsu.actualizarSaldoUsuario(saldoActual, cliente.getNombreUsuario());
        } else if (instruccion.equals("proyecto-completado")) {
            calcularPagoFreelancer(request, propuesta, idProyecto);
        } else {
            throw new DataInexistenteException("No se reconocio la instruccion");
        }
        
    }
    
    private void revision(double saldoActual) throws DataInexistenteException {
        if (saldoActual < 0) {
            throw new DataInexistenteException("Saldo insuficiente");
        }
    }
    
    private void calcularPagoFreelancer(ProyectoRequest request, PropuestaDB propuesta, int idProyecto) throws DataBaseException, DataInexistenteException {
        ComisionDAO daoCom = new ComisionDAO();
        HistorialComisionDAO daoHis = new HistorialComisionDAO();
        ComisionDB comision = daoCom.comisionPorId(request.getComision());
        
        double comisionPorProyecto = propuesta.getMontoOfertado() * (comision.getPorcentaje()/100.0);
        double pagoFreelancer = propuesta.getMontoOfertado() - comisionPorProyecto;
        
        UsuarioDB freelancer = buscarUsuario(request.getFreelancer());
        
        double saldoActual = freelancer.getSaldo() + pagoFreelancer;
        daoUsu.actualizarSaldoUsuario(saldoActual, freelancer.getNombreUsuario());
        
        HistorialRequest requestHis = new HistorialRequest();
        requestHis.setMontoProyecto(propuesta.getMontoOfertado());
        requestHis.setPorcentajeComision(comision.getPorcentaje());
        requestHis.setCliente(request.getCliente());
        requestHis.setFreelancer(request.getFreelancer());
        requestHis.setCategoria(request.getCategoria());
        requestHis.setProyecto(idProyecto);
        daoHis.agregarRegistro(requestHis);
    }
    
}
