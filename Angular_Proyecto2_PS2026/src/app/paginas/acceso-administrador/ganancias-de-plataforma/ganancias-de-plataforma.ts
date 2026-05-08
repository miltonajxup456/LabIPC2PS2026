import { Component, signal } from '@angular/core';
import { GananciasPlataforma } from '../../../models/reporte/ganancias-plataforma';
import { HistorialComisionService } from '../../../restApi/comision-service/historial-comision-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ganancias-de-plataforma',
  imports: [Header, MenuSecundario, FormsModule],
  templateUrl: './ganancias-de-plataforma.html',
  styles: ``,
})
export class GananciasDePlataforma {

  titulo: string = 'Top 5 de Categorias mas Activas';
  linkAnterior: string = '/acceso-administrador';
  gananacias = signal<GananciasPlataforma | null>(null);
  fechaInicial!: string;
  fechaFinal!: string;

  constructor(private historialComisionService: HistorialComisionService) {}

  llamarGanancias(): void {
    const fechaI = new Date(this.fechaInicial);
    const fechaF = new Date(this.fechaFinal);

    if (fechaI > fechaF) {
      alert('No se puede colocar una fecha inicial que sea posterior a la final');
      return;
    }
    this.historialComisionService.getGananciasPlataforma(this.fechaInicial, this.fechaFinal).subscribe({
      next: (ganancia: GananciasPlataforma) => this.gananacias.set(ganancia)
    })
  }

}
