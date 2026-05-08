import { Component, OnInit, signal } from '@angular/core';
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Destino } from '../../../modelos/destino/destino';
import { PaqueteService } from '../../../restapi/paquete-service/paquete-service';
import { Paquete } from '../../../modelos/paquete/paquete';

@Component({
  selector: 'app-paquetes-por-destino',
  imports: [Head, RegresoMenu],
  templateUrl: './paquetes-por-destino.html',
  styleUrl: './paquetes-por-destino.css',
})
export class PaquetesPorDestino implements OnInit {

  titulo: string = 'Busca los paquetes por el Destino';
  linkAnterior: string = '/area-operador';

  destinosGuardados = signal<Destino[]>([]);
  destinoElegido = signal<Destino | null>(null);
  paquetesDestino = signal<Paquete[]>([]);

  constructor(private destinoService: DestinoService, private paqueteService: PaqueteService) {}

  ngOnInit(): void {
    this.destinoService.getAllDestinos().subscribe({
      next: (destinos: Destino[]) => {
        this.destinosGuardados.set(destinos);
      }
    })
  }

  setDestino(destino: Destino) {
    this.destinoElegido.set(destino);
    this.paqueteService.getPaqueteDestino(destino.idDestino!).subscribe({
      next: (paquetes: Paquete[]) => {
        this.paquetesDestino.set(paquetes);
      }
    })
  }

}
