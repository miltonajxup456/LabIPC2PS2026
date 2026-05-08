import { Component, signal } from '@angular/core';
import { ContratoCompletado } from '../../../models/reporte/contrato-completado';
import { ReporteService } from '../../../restApi/reporte-service/reporte-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ver-contratos-completados',
  imports: [Header, MenuSecundario, FormsModule],
  templateUrl: './ver-contratos-completados.html',
  styles: ``,
})
export class VerContratosCompletados {

  titulo: string = 'Historial de Contratos Realizados';
  linkAnterior: string = '/acceso-freelancer';
  contratos = signal<ContratoCompletado[]>([]);
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
    this.reporteService.getContratosCompletados(this.fechaInicial, this.fechaFinal, this.guardado.getUsuarioLogeado().nombreUsuario).subscribe({
      next: (contratos: ContratoCompletado[]) => this.contratos.set(contratos)
    })
  }

}
