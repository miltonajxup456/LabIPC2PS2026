import { Component, OnInit, signal } from '@angular/core';
import { CategoriaTrabajada } from '../../../models/reporte/categoria-trabajada';
import { ReporteService } from '../../../restApi/reporte-service/reporte-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ver-categorias-preferidas',
  imports: [Header, MenuSecundario],
  templateUrl: './ver-categorias-preferidas.html',
  styles: ``,
})
export class VerCategoriasPreferidas implements OnInit {

  titulo: string = 'Top 5 Categorias en la que mas ha trabajado';
  linkAnterior: string = '/acceso-freelancer';
  categorias = signal<CategoriaTrabajada[]>([]);

  constructor(private reporteService: ReporteService, private guardado: GuardadoUsuario) {}

  ngOnInit(): void {
    this.reporteService.getCategoriasTrabajadas(this.guardado.getUsuarioLogeado().nombreUsuario).subscribe({
      next: (categorias: CategoriaTrabajada[]) => this.categorias.set(categorias)
    })
  }
}
