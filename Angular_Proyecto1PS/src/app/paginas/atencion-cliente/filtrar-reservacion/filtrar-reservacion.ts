import { Component, OnInit, signal } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { Destino } from '../../../modelos/destino/destino';
import { FormsModule } from '@angular/forms';
import { Reservacion } from '../../../modelos/reservacion/reservacion';

@Component({
  selector: 'app-filtrar-reservacion',
  imports: [Head, RegresoMenu, FormsModule],
  templateUrl: './filtrar-reservacion.html',
  styleUrl: './filtrar-reservacion.css',
})
export class FiltrarReservacion implements OnInit {

  titulo: string = 'Consulta las Reservaciones por su Fecha de Viaje';
  linkAnterior: string = '/atencion-cliente';

  destinosGuardados = signal<Destino[]>([]);
  destinoElegido = signal<Destino | null>(null);
  fechaElegida: string = '2026-03-26';
  reservacionesFechaDestino = signal<Reservacion[]>([]);

  constructor(private destinoService: DestinoService, private reservacionService: ReservacionService) {}
  
  ngOnInit(): void {
    this.destinoService.getAllDestinos().subscribe({
      next: (destinos: Destino[]) => {
        this.destinosGuardados.set(destinos);
      }
    });
  }

  setDestino(destino: Destino) {
    if (!this.fechaElegida) return;
    this.destinoElegido.set(destino);

    this.reservacionService.getReservacionesFechaDestino(this.fechaElegida, destino.idDestino!).subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservacionesFechaDestino.set(reservaciones);
      }
    })
  }

}
