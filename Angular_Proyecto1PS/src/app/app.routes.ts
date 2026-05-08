import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Home } from './home/home';
import { AtencionCliente } from './paginas/atencion-cliente/atencion-cliente';
import { AreaDeOperador } from './paginas/area-de-operador/area-de-operador';
import { CrearReservacion } from './paginas/atencion-cliente/crear-reservacion/crear-reservacion';
import { RegistrarUsuario } from './paginas/atencion-cliente/cliente/registrar-usuario/registrar-usuario';
import { DatosCliente } from './paginas/atencion-cliente/cliente/datos-cliente/datos-cliente';
import { ModificarCliente } from './paginas/atencion-cliente/cliente/modificar-cliente/modificar-cliente';
import { ProcesarPago } from './paginas/atencion-cliente/procesar-pago/procesar-pago';
import { CancelarReservacion } from './paginas/atencion-cliente/cancelar-reservacion/cancelar-reservacion';
import { AgregarDestino } from './paginas/area-de-operador/agregar-destino/agregar-destino';
import { ModificarDestino } from './paginas/area-de-operador/modificar-destino/modificar-destino';
import { CrearPaqueteTuristico } from './paginas/area-de-operador/crear-paquete-turistico/crear-paquete-turistico';
import { RegistrarProveedor } from './paginas/area-de-operador/registrar-proveedor/registrar-proveedor';
import { ModificarProveedor } from './paginas/area-de-operador/modificar-proveedor/modificar-proveedor';
import { ModificarPaquete } from './paginas/area-de-operador/modificar-paquete/modificar-paquete';
import { DefinirServicios } from './paginas/area-de-operador/definir-servicios/definir-servicios';
import { HistorialReservacionesCliente } from './paginas/atencion-cliente/historial-reservaciones-cliente/historial-reservaciones-cliente';
import { HistorialPagosReservacion } from './paginas/atencion-cliente/historial-pagos-reservacion/historial-pagos-reservacion';
import { FiltrarReservacion } from './paginas/atencion-cliente/filtrar-reservacion/filtrar-reservacion';
import { TodasLasReservaciones } from './paginas/atencion-cliente/todas-las-reservaciones/todas-las-reservaciones';
import { PaquetesPorDestino } from './paginas/area-de-operador/paquetes-por-destino/paquetes-por-destino';
import { ReservacionesPorDia } from './paginas/atencion-cliente/reservaciones-por-dia/reservaciones-por-dia';
import { DetallesPaquete } from './paginas/area-de-operador/detalles-paquete/detalles-paquete';
import { AreaAdministrador } from './paginas/area-administrador/area-administrador';
import { CargaDeArchivos } from './paginas/area-administrador/carga-de-archivos/carga-de-archivos';
import { CrearUsuario } from './paginas/area-administrador/crear-usuario/crear-usuario';
import { ModificarUsuario } from './paginas/area-administrador/modificar-usuario/modificar-usuario';
import { Reportes } from './paginas/area-administrador/reportes/reportes';
import { Ventas } from './paginas/area-administrador/reportes/ventas/ventas';
import { Cancelaciones } from './paginas/area-administrador/reportes/cancelaciones/cancelaciones';
import { AgenteMasVentas } from './paginas/area-administrador/reportes/agente-mas-ventas/agente-mas-ventas';
import { AgenteMasGanancias } from './paginas/area-administrador/reportes/agente-mas-ganancias/agente-mas-ganancias';
import { PaqueteMasVendido } from './paginas/area-administrador/reportes/paquete-mas-vendido/paquete-mas-vendido';
import { PaqueteMenosVendido } from './paginas/area-administrador/reportes/paquete-menos-vendido/paquete-menos-vendido';
import { OcupacionDeDestino } from './paginas/area-administrador/reportes/ocupacion-de-destino/ocupacion-de-destino';
import { GananciasTotales } from './paginas/area-administrador/reportes/ganancias-totales/ganancias-totales';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: Home },
    { path: 'login', component: Login },
    { path: 'atencion-cliente', component: AtencionCliente },
    { path: 'registrar-usuario', component: RegistrarUsuario },
    { path: 'datos-cliente', component: DatosCliente },
    { path: 'modificar-cliente', component: ModificarCliente},
    { path: 'crear-reservacion', component: CrearReservacion },
    { path: 'procesar-pago', component: ProcesarPago },
    { path: 'cancelar-reservacion', component: CancelarReservacion},
    { path: 'historial-reservacion-cliente', component: HistorialReservacionesCliente},
    { path: 'historial-pagos-reservacion', component: HistorialPagosReservacion},
    { path: 'filtrar-reservacion', component: FiltrarReservacion},
    { path: 'reservaciones-por-dia', component: ReservacionesPorDia},
    { path: 'todas-las-reservaciones', component: TodasLasReservaciones},

    { path: 'area-operador', component: AreaDeOperador },
    { path: 'agregar-destino', component: AgregarDestino},
    { path: 'modificar-destino', component: ModificarDestino},
    { path: 'registrar-proveedor', component: RegistrarProveedor},
    { path: 'modificar-proveedor', component: ModificarProveedor},
    { path: 'crear-paquete', component: CrearPaqueteTuristico},
    { path: 'modificar-paquete', component: ModificarPaquete},
    { path: 'definir-servicio', component: DefinirServicios},
    { path: 'paquete-por-destino', component: PaquetesPorDestino},
    { path: 'detalles-paquete', component: DetallesPaquete},

    { path: 'area-administrador', component: AreaAdministrador},
    { path: 'crear-usuario', component: CrearUsuario},
    { path: 'modificar-usuarios', component: ModificarUsuario},
    { path: 'cargar-archivos', component: CargaDeArchivos},
    { path: 'reportes', component: Reportes},
    { path: 'reporte-ventas', component: Ventas},
    { path: 'reporte-cancelaciones', component: Cancelaciones},
    { path: 'reporte-ganancias-totales', component: GananciasTotales},
    { path: 'reporte-agente-mas-ventas', component: AgenteMasVentas},
    { path: 'reporte-agente-mas-ganancia', component: AgenteMasGanancias},
    { path: 'reporte-paquete-mas-vendido', component: PaqueteMasVendido},
    { path: 'reporte-paquete-menos-vendido', component: PaqueteMenosVendido},
    { path: 'reporte-ocupacion-destino', component: OcupacionDeDestino}
];
