import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GastoCategoria } from '../../../models/reporte/gasto-categoria';
import { ReporteService } from '../../../restApi/reporte-service/reporte-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ver-gastos-categoria',
  imports: [FormsModule, Header, MenuSecundario],
  templateUrl: './ver-gastos-categoria.html',
  styles: ``,
})
export class VerGastosCategoria {

  titulo: string = 'Historial de Gastos Por Categoria';
  linkAnterior: string = '/acceso-cliente';
  gastos = signal<GastoCategoria[]>([]);
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
    this.reporteService.getGastoCategoria(this.fechaInicial, this.fechaFinal, this.guardado.getUsuarioLogeado().nombreUsuario).subscribe({
      next: (gastos: GastoCategoria[]) => this.gastos.set(gastos)
    })
  }

}
