import { Component, OnInit, signal } from '@angular/core';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { Reservacion } from '../../../modelos/reservacion/reservacion';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reservaciones-por-dia',
  imports: [Head, RegresoMenu, FormsModule],
  templateUrl: './reservaciones-por-dia.html',
  styleUrl: './reservaciones-por-dia.css',
})
export class ReservacionesPorDia {

  titulo: string = 'Reservaciones por dia';
  linkAnterior: string = '/atencion-cliente';

  reservacionesDia = signal<Reservacion[]>([]);
  fechaElegida = signal<string | null>(null);

  constructor(private reservacionService: ReservacionService) {}

  buscarReservaciones() {
    this.reservacionService.getReservacionesFecha(this.fechaElegida()!).subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservacionesDia.set(reservaciones);
      }
    })
  }

}
