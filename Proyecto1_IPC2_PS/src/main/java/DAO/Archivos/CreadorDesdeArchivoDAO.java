/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Archivos;

import DAO.sql.ClientesDAO;
import DAO.sql.DestinoDAO;
import DAO.sql.PagoDAO;
import DAO.sql.PaqueteTuristicoDAO;
import DAO.sql.PasajeroDAO;
import DAO.sql.ProveedorDAO;
import DAO.sql.ReservacionDAO;
import DAO.sql.ServicioPaqueteDAO;
import DAO.sql.UsuarioDAO;
import Exceptions.DatosInvalidosException;
import Exceptions.EntidadNoEncontradaException;
import Model.Cliente.ClienteRequest;
import Model.Destino.DestinoDB;
import Model.Destino.DestinoRequest;
import Model.Login.UsuarioDB;
import Model.Login.UsuarioRequest;
import Model.Pago.PagoRequest;
import Model.Paquete.PaqueteRequest;
import Model.Paquete.PaqueteTuristicoDB;
import Model.Proveedor.ProveedorDB;
import Model.Proveedor.ProveedorRequest;
import Model.Reservacion.ReservacionDB;
import Model.Reservacion.ReservacionRequest;
import Model.ServicioPaquete.ServicioPaqueteRequest;
import java.util.List;

/**
 *
 * @author millin-115
 */
public class CreadorDesdeArchivoDAO {
    
    private final RegistroDeErrores registroErrores;
    private final UsuarioDAO usuariodao;
    private final DestinoDAO destinodao;
    private final ProveedorDAO proveedordao;
    private final PaqueteTuristicoDAO paquetedao;
    private final ServicioPaqueteDAO serviciodao;
    private final ClientesDAO clientedao;
    private final PasajeroDAO pasajerodao;
    private final ReservacionDAO reservaciondao;
    private final PagoDAO pagodao;
    
    public CreadorDesdeArchivoDAO() {
        this.registroErrores = new RegistroDeErrores();
        this.usuariodao = new UsuarioDAO();
        this.destinodao = new DestinoDAO();
        this.proveedordao = new ProveedorDAO();
        this.paquetedao = new PaqueteTuristicoDAO();
        this.serviciodao = new ServicioPaqueteDAO();
        this.clientedao = new ClientesDAO();
        this.pasajerodao = new PasajeroDAO();
        this.reservaciondao = new ReservacionDAO();
        this.pagodao = new PagoDAO();
    }
    
    public RegistroDeErrores getRegistroErrores() {
        return registroErrores;
    }
    
    public void filtrarLinea(String linea) {
        if (linea.isBlank() || linea.isEmpty()) {
            return;
        }
        String[] tipo = linea.split("\\(");
        String[] datosLimpios = limpiarDatos(tipo[1]);
        for (String datoLimpio : datosLimpios) {
            if (datoLimpio.isBlank()) {
                registroErrores.agregarErrorGenerico("No se pueden permite dejar campos Vacios");
                return;
            }
        }
        
        if (tipo[0].equalsIgnoreCase("USUARIO")) {
            crearUsuario(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("DESTINO")) {
            crearDestino(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("PROVEEDOR")) {
            crearProveedor(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("PAQUETE")) {
            crearPaquete(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("SERVICIO_PAQUETE")) {
            crearServicioPaquete(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("CLIENTE")) {
            crearCliente(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("RESERVACION")) {
            crearReservacion(datosLimpios);
        } else if (tipo[0].equalsIgnoreCase("PAGO")) {
            crearPago(datosLimpios);
        } else {
            System.out.println(tipo[0]+" no coincide con una estructura esperada");
        }
    }
    
    private void crearUsuario(String[] datos) {
        UsuarioRequest request = new UsuarioRequest();
        request.setNombre(datos[0]);
        if (datos[1].length() < 6) {
            registroErrores.agregarErrorUsuario("La constraseña debe ser de por lo menos 6 caracteres");
            return;
        }
        request.setPassword_user(datos[1]);
        int rol;
        try {
            rol = Integer.parseInt(datos[2]);
        } catch (NumberFormatException e) {
            return;
        }
        if (rol <= 0 || rol > 3) {
            registroErrores.agregarErrorUsuario("El rol es invalido");
            return;
        }
        request.setRol(rol);
        try {
            usuariodao.crearUsuario(request);
            registroErrores.agregarCorrecto();
        } catch (DatosInvalidosException e) {
            registroErrores.agregarErrorUsuario("Error al agregar Usuario: No pueden haber dos usuarios con el mismo nombre");
        }
    }
    
    private void crearDestino(String[] datosLimpios) {
        DestinoRequest request = new DestinoRequest();
        request.setNombre(datosLimpios[0]);
        request.setPais(datosLimpios[1]);
        request.setDescripcion(datosLimpios[2]);
        try {
            destinodao.crearDestino(request);
            registroErrores.agregarCorrecto();
        } catch (DatosInvalidosException e) {
            registroErrores.agregarErrorDestino("Los nombres de los Destinos no se pueden repetir");
        }
    }
    
    private void crearProveedor(String[] datos) {
        ProveedorRequest request = new ProveedorRequest();
        request.setNombre(datos[0]);
        int tipo;
        try {
            tipo = Integer.parseInt(datos[1]);
        } catch (NumberFormatException e) {
            return;
        }
        if (tipo <= 0 || tipo > 5) {
            registroErrores.agregarErrorProveedor("El tipo de proveedor es invalido");
            return;
        }
        request.setTipoServicio(tipo);
        request.setPais(datos[2]);
        try {
            proveedordao.agregarProveedor(request);
            registroErrores.agregarCorrecto();
        } catch (DatosInvalidosException e) {
            registroErrores.agregarErrorProveedor("Error de Proveedor: No pueden existir dos proveedores con el mismo nombre");
        }
    }
    
    private void crearPaquete(String[] datos) {
        DestinoDB destino = destinodao.existeNombreDestino(datos[1]);
        if (destino != null) {
            PaqueteRequest request = new PaqueteRequest();
            request.setNombre(datos[0]);
            request.setIdDestino(destino.getIdDestino());
            int duracion;
            try {
                duracion = Integer.parseInt(datos[2]);
            } catch (NumberFormatException e) {
                registroErrores.agregarErrorPaquete("La duracion del paquete solo puede ser entero");
                return;
            }
            if (duracion <= 0) {
                registroErrores.agregarErrorPaquete("La duracion del paquete es invalida");
                return;
            }
            request.setDuracionDias(duracion);
            double precio;
            try {
                precio = Double.parseDouble(datos[3]);
            } catch (NumberFormatException e) {
                return;
            }
            if (precio <= 0) {
                registroErrores.agregarErrorPaquete("El precio no puede ser negativos ni cero");
                return;
            }
            request.setPrecio(precio);
            int capacidad;
            try {
                capacidad = Integer.parseInt(datos[4]);
            } catch (NumberFormatException e) {
                registroErrores.agregarErrorPaquete("La capacidad del paquete solo puede ser entero");
                return;
            }
            if (capacidad <= 0) {
                registroErrores.agregarErrorPaquete("La capadidad del paquete es invalida");
                return;
            }
            request.setCapacidad(capacidad);
            try {
                paquetedao.crearPaquete(request);
                registroErrores.agregarCorrecto();
            } catch (DatosInvalidosException e) {
                registroErrores.agregarErrorPaquete("Los nombre del Paquete no se pueden repetir '" + datos[0] + "'");
            }
        } else {
            registroErrores.agregarErrorPaquete("El nombre del Destino "+ datos[1] +" no existe");
        }
    }
    
    private void crearServicioPaquete(String[] datos) {
        PaqueteTuristicoDB paquete = paquetedao.paquetePorNombre(datos[0]);
        ProveedorDB proveedor = proveedordao.proveedorPorNombre(datos[1]);
        if (paquete != null && proveedor != null) {
            ServicioPaqueteRequest request = new ServicioPaqueteRequest();
            request.setPaquete(paquete.getIdPaquete());
            request.setProveedor(proveedor.getIdProveedor());
            request.setDescripcion(datos[2]);
            double costo;
            try {
                costo = Double.parseDouble(datos[3]);
            } catch (NumberFormatException e) {
                return;
            }
            if (costo <= 0) {
                registroErrores.agregarErrorServicioPaquete("El costo no puede ser negativo");
                return;
            }
            request.setCosto(costo);
            try {
                serviciodao.guardarServicio(request);
                registroErrores.agregarCorrecto();
            } catch (DatosInvalidosException e) {
                registroErrores.agregarErrorServicioPaquete(e.getMessage());
            }
        } else if (paquete == null && proveedor != null) {
            registroErrores.agregarErrorServicioPaquete("El paquete " + datos[0] + " no existe");
        } else if (paquete != null && proveedor == null) {
            registroErrores.agregarErrorServicioPaquete("El proveedor " + datos[1] + " no existe");
        } else {
            registroErrores.agregarErrorServicioPaquete("Ni el paquete " + datos[0] + " ni el proveedor " + datos[1] + " existen");
        }
    }
    
    private void crearCliente(String[] datos) {
        ClienteRequest request = new ClienteRequest();
        request.setDpi(datos[0]);
        request.setNombre(datos[1]);
        String fechaNac = transformarAJson(datos[2]);
        request.setFechaNac(fechaNac);
        request.setTelefono(datos[3]);
        request.setEmail(datos[4]);
        request.setNacionalidad(datos[5]);
        try {
            clientedao.crearClienteDB(request);
            registroErrores.agregarCorrecto();
        } catch (DatosInvalidosException e) {
            registroErrores.agregarErrorCliente("El DPI " + datos[0] + " ya se encuentra registrado");
        }
    }
    
    private void crearReservacion(String[] datos) {
        PaqueteTuristicoDB paquete = paquetedao.paquetePorNombre(datos[0]);
        UsuarioDB usuario = usuariodao.usuarioPorNombre(datos[1]);
        String[] dpis = datos[3].split("\\|");
        List<String> pasajeros;
        try {
            pasajeros = pasajerodao.validarYObtenerPasajeros(dpis);
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error interno: "+e.getMessage());
            registroErrores.agregarErrorReservacion("En una reservacion se encontro un DPI no registrado");
            return;
        }
        
        if (paquete != null && usuario != null) {
            ReservacionRequest request = new ReservacionRequest();
            request.setPaquete(paquete.getIdPaquete());
            request.setAgenteDeRegistro(usuario.getIdUsuario());
            String fechaViaje = transformarAJson(datos[2]);
            request.setFechaViaje(fechaViaje);
            request.setDpiPasajeros(pasajeros);
            
            int cantPasajeros = pasajeros.size();
            request.setCantPasajeros(cantPasajeros);
            request.setCosto(cantPasajeros * paquete.getPrecio());
            try {
                reservaciondao.crearReservacion(request);
                registroErrores.agregarCorrecto();
            } catch (DatosInvalidosException e) {
                registroErrores.agregarErrorReservacion("El formato de fecha, no es un formato valido 'dd/mm/yyyy'");
            }
        } else if (paquete == null && usuario != null) {
            registroErrores.agregarErrorReservacion("El paquete " + datos[0] + " no existe");
        } else if (paquete != null && usuario == null) {
            registroErrores.agregarErrorReservacion("El usuario " + datos[1] + " no existe");
        } else {
            registroErrores.agregarErrorReservacion("Ni el paquete " + datos[0] + " ni el usuario " + datos[1] + " existen");
        }
    }
    
    private void crearPago(String[] datos) {
        int numeroReservacion;
        try {
            numeroReservacion = Integer.parseInt(datos[0].trim());
        } catch (NumberFormatException e) {
            registroErrores.agregarErrorPago("El numero de reservacion del pago no es un numero "+datos[0]);
            return;
        }
        ReservacionDB reservacion = reservaciondao.reservacionPorId(numeroReservacion);
        
        if (reservacion != null) {
            PagoRequest request = new PagoRequest();
            request.setNumReservacion(numeroReservacion);
            double montoPagado;
            int metodoPago;
            try {
                montoPagado = Double.parseDouble(datos[1]);
                metodoPago = Integer.parseInt(datos[2]);
            } catch (NumberFormatException e) {
                return;
            }
            if (montoPagado <= 0) {
                registroErrores.agregarErrorPago("El pago no puede ser negativo");
            }
            if (metodoPago <= 0 || metodoPago > 3) {
                registroErrores.agregarErrorPago("El metodo de pago no es valido");
            }
            request.setMontoPagado(montoPagado);
            request.setMetodo(metodoPago);
            
            String fechaPago = transformarAJson(datos[3]);
            request.setFecha(fechaPago);
            try {
                pagodao.crearRegistroPago(request);
                if (request.getMontoPagado() >= (reservacion.getCosto() - reservacion.getDineroCancelado())) {
                    ReservacionRequest reservacionRequest = new ReservacionRequest();
                    reservacionRequest.setNumReservacion(reservacion.getNumReservacion());
                    reservacionRequest.setEstado(2);
                    reservaciondao.actualizarReservacion(reservacionRequest);
                }
                registroErrores.agregarCorrecto();
            } catch (DatosInvalidosException e) {
                registroErrores.agregarErrorPago("El numero de reservacion "+request.getNumReservacion()+" no existe");
            }
        } else {
            registroErrores.agregarErrorPago("La Reservacion " + datos[0] + " no exsite");
        }
    }
    
    private String[] limpiarDatos(String datos) {
        datos = datos.replace("\"", "");
        
        String[] valores = datos.split("\\)");
        String[] limpio = valores[0].split(",");
        
        for (int i = 0; i < limpio.length; i++) {
            limpio[i] = limpio[i].trim();
        }
        
        return limpio;
    }
    
        
    private String transformarAJson(String fechaDMA) {
        String fecha[] = fechaDMA.split("/");
        String fechajson = fecha[2]+"-"+fecha[1]+"-"+fecha[0];
        return fechajson;
    }
    
    
}
