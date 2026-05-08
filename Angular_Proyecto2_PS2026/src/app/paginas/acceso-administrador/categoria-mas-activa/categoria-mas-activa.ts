import { Component, signal } from '@angular/core';
import { CategoriaActiva } from '../../../models/reporte/categoria-activa';
import { HistorialComisionService } from '../../../restApi/comision-service/historial-comision-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-categoria-mas-activa',
  imports: [Header, MenuSecundario, FormsModule],
  templateUrl: './categoria-mas-activa.html',
  styles: ``,
})
export class CategoriaMasActiva {

  titulo: string = 'Top 5 de Categorias mas Activas';
  linkAnterior: string = '/acceso-administrador';
  topCategorias = signal<CategoriaActiva[]>([]);
  fechaInicial!: string;
  fechaFinal!: string;

  constructor(private historialComisionService: HistorialComisionService) {}

  llamarCategorias(): void {
    const fechaI = new Date(this.fechaInicial);
    const fechaF = new Date(this.fechaFinal);

    if (fechaI > fechaF) {
      alert('No se puede colocar una fecha inicial que sea posterior a la final');
      return;
    }
    this.historialComisionService.getCategoriaActiva(this.fechaInicial, this.fechaFinal).subscribe({
      next: (categorias: CategoriaActiva[]) => this.topCategorias.set(categorias)
    })
  }

}
