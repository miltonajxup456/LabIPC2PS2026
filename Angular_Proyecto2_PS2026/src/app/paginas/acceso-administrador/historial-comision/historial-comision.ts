import { Component, OnInit, signal } from '@angular/core';
import { Comision } from '../../../models/comision/comision';
import { ComisionService } from '../../../restApi/comision-service/comision-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-historial-comision',
  imports: [Header, MenuSecundario],
  templateUrl: './historial-comision.html',
  styles: ``,
})
export class HistorialComision implements OnInit {

  titulo: string = 'Historial de Comisiones';
  linkAnterior: string = "/acceso-administrador";
  comisiones = signal<Comision[]>([]);

  constructor(private comisionService: ComisionService) {}

  ngOnInit(): void {
    this.comisionService.getHistorial().subscribe({
      next: (comisiones: Comision[]) => this.comisiones.set(comisiones)
    });
  }

}
