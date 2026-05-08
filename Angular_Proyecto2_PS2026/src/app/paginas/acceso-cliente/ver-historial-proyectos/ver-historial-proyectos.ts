import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReporteService } from '../../../restApi/reporte-service/reporte-service';
import { Proyecto } from '../../../models/proyecto/proyecto';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ver-historial-proyectos',
  imports: [FormsModule, Header, MenuSecundario],
  templateUrl: './ver-historial-proyectos.html',
  styles: ``,
})
export class VerHistorialProyectos {

  titulo: string = 'Historial de Proyectos Publicados';
  linkAnterior: string = '/acceso-cliente';
  proyectos = signal<Proyecto[]>([]);
  fechaInicial!: string;
  fechaFinal!: string;

  constructor(private reporteService: ReporteService, private guardado: GuardadoUsuario) {}

  llamarProyectos(): void {
    const fechaI = new Date(this.fechaInicial);
    const fechaF = new Date(this.fechaFinal);

    if (fechaI > fechaF) {
      alert('No se puede colocar una fecha inicial que sea posterior a la final');
      return;
    }
    this.reporteService.getHistorialProyectos(this.fechaInicial, this.fechaFinal, this.guardado.getUsuarioLogeado().nombreUsuario).subscribe({
      next: (proyectos: Proyecto[]) => this.proyectos.set(proyectos)
    })
  }

}
