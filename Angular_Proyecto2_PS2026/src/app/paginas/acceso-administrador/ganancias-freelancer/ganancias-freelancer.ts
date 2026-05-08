import { Component, OnInit, signal } from '@angular/core';
import { HistorialComisionService } from '../../../restApi/comision-service/historial-comision-service';
import { FormsModule } from '@angular/forms';
import { GananciaFreelancer } from '../../../models/reporte/ganancia-freelancer';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ganancias-freelancer',
  imports: [FormsModule, Header, MenuSecundario],
  templateUrl: './ganancias-freelancer.html',
  styles: ``,
})
export class GananciasFreelancer {

  titulo: string = 'Ganancias de Top 5 freelancers';
  linkAnterior: string = '/acceso-administrador';
  topGanancias = signal<GananciaFreelancer[]>([]);
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
    this.historialComisionService.getGananciasFreelancer(this.fechaInicial, this.fechaFinal).subscribe({
      next: (ganancias: GananciaFreelancer[]) => this.topGanancias.set(ganancias)
    })
  }

}
