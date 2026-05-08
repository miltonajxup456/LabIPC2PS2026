import { Routes } from '@angular/router';
import { Login } from './paginas/login/login';
import { RegistroUsuario } from './paginas/registro-usuario/registro-usuario';
import { AccesoAdministrador } from './paginas/acceso-administrador/acceso-administrador';
import { AccesoFreelancer } from './paginas/acceso-freelancer/acceso-freelancer';
import { CrearProyecto } from './paginas/acceso-cliente/crear-proyecto/crear-proyecto';
import { VerPropuestas } from './paginas/acceso-cliente/ver-propuestas/ver-propuestas';
import { EditarInformacionCliente } from './paginas/acceso-cliente/editar-informacion-cliente/editar-informacion-cliente';
import { AccesoCliente } from './paginas/acceso-cliente/acceso-cliente';
import { AgregarPropuesta } from './paginas/acceso-freelancer/agregar-propuesta/agregar-propuesta';
import { RecargarSaldo } from './paginas/acceso-cliente/recargar-saldo/recargar-saldo';
import { EditarProyecto } from './paginas/acceso-cliente/editar-proyecto/editar-proyecto';
import { EntregarProyecto } from './paginas/acceso-freelancer/entregar-proyecto/entregar-proyecto';
import { VerEntregas } from './paginas/acceso-cliente/ver-entregas/ver-entregas';
import { ProponerCategoria } from './paginas/acceso-freelancer/proponer-categoria/proponer-categoria';
import { ProponerHabilidad } from './paginas/acceso-freelancer/proponer-habilidad/proponer-habilidad';
import { VerPropuestasHabilidad } from './paginas/acceso-administrador/ver-propuestas-habilidad/ver-propuestas-habilidad';
import { VerPropuestasCategoria } from './paginas/acceso-administrador/ver-propuestas-categoria/ver-propuestas-categoria';
import { ModificarComision } from './paginas/acceso-administrador/modificar-comision/modificar-comision';
import { InhabilitarCuentas } from './paginas/acceso-administrador/inhabilitar-cuentas/inhabilitar-cuentas';
import { HistorialComision } from './paginas/acceso-administrador/historial-comision/historial-comision';
import { GananciasFreelancer } from './paginas/acceso-administrador/ganancias-freelancer/ganancias-freelancer';
import { CategoriaMasActiva } from './paginas/acceso-administrador/categoria-mas-activa/categoria-mas-activa';
import { GananciasDePlataforma } from './paginas/acceso-administrador/ganancias-de-plataforma/ganancias-de-plataforma';
import { VerHistorialProyectos } from './paginas/acceso-cliente/ver-historial-proyectos/ver-historial-proyectos';
import { VerHistorialRecargas } from './paginas/acceso-cliente/ver-historial-recargas/ver-historial-recargas';
import { VerGastosCategoria } from './paginas/acceso-cliente/ver-gastos-categoria/ver-gastos-categoria';
import { VerContratosCompletados } from './paginas/acceso-freelancer/ver-contratos-completados/ver-contratos-completados';
import { VerCategoriasPreferidas } from './paginas/acceso-freelancer/ver-categorias-preferidas/ver-categorias-preferidas';
import { VerPropuestasEnviadas } from './paginas/acceso-freelancer/ver-propuestas-enviadas/ver-propuestas-enviadas';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: Login },
    { path: 'registro-usuario', component: RegistroUsuario },
    { path: 'acceso-administrador', component: AccesoAdministrador },
    { path: 'acceso-cliente', component: AccesoCliente },
    { path: 'editar-informacion-cliente', component: EditarInformacionCliente },
    { path: 'recargar-saldo', component: RecargarSaldo },
    { path: 'crear-proyecto', component: CrearProyecto },
    { path: 'editar-proyecto', component: EditarProyecto },
    { path: 'ver-propuestas', component: VerPropuestas },
    { path: 'ver-entregas', component: VerEntregas },
    { path: 'ver-historial-proyectos', component: VerHistorialProyectos },
    { path: 'ver-historial-recargas', component: VerHistorialRecargas},
    { path: 'ver-gastos-categorias', component: VerGastosCategoria},

    { path: 'acceso-freelancer', component: AccesoFreelancer },
    { path: 'agregar-propuesta', component: AgregarPropuesta }, 
    { path: 'entregar-proyecto', component: EntregarProyecto }, 
    { path: 'proponer-categoria', component: ProponerCategoria },
    { path: 'proponer-habilidad', component: ProponerHabilidad },
    { path: 'ver-contratos-completados', component: VerContratosCompletados },
    { path: 'ver-categorias-preferidas', component: VerCategoriasPreferidas,},
    { path: 'ver-propuestas-enviadas', component: VerPropuestasEnviadas },

    { path: 'acceso-administrador', component: AccesoAdministrador },
    { path: 'ver-propuestas-categoria', component: VerPropuestasCategoria },
    { path: 'ver-propuestas-habilidad', component: VerPropuestasHabilidad }, 
    { path: 'modificar-comision', component: ModificarComision }, 
    { path: 'inhabilitar-cuentas', component: InhabilitarCuentas },
    { path: 'historial-comision', component: HistorialComision },
    { path: 'ganancias-freelancer', component: GananciasFreelancer },
    { path: 'categoria-mas-activa', component: CategoriaMasActiva },
    { path: 'ganancias-de-plataforma', component: GananciasDePlataforma }
];
