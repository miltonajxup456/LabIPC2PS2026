import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Recarga } from '../../../models/recarga/recarga';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { RecargaService } from '../../../restApi/recarga-service/recarga-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ver-historial-recargas',
  imports: [FormsModule, Header, MenuSecundario],
  templateUrl: './ver-historial-recargas.html',
  styles: ``,
})
export class VerHistorialRecargas implements OnInit {

  titulo: string = 'Historial de Recargas Realizadas';
  linkAnterior: string = '/acceso-cliente';
  recargas = signal<Recarga[]>([]);
  fechaInicial!: string;
  fechaFinal!: string;

  constructor(private recargaService: RecargaService, private guardado: GuardadoUsuario) {}

  ngOnInit(): void {
    this.recargaService.getRegistrosCliente(this.guardado.getUsuarioLogeado().nombreUsuario).subscribe({
      next: (recargas: Recarga[]) => this.recargas.set(recargas)
    })
  }
}
