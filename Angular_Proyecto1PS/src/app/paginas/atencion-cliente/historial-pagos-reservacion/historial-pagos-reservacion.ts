import { Component, OnInit, signal } from '@angular/core';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { PagoService } from '../../../restapi/pago-service/pago-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Reservacion } from '../../../modelos/reservacion/reservacion';
import { Pago } from '../../../modelos/pago/pago';

@Component({
  selector: 'app-historial-pagos-reservacion',
  imports: [Head, RegresoMenu],
  templateUrl: './historial-pagos-reservacion.html',
  styleUrl: './historial-pagos-reservacion.css',
})
export class HistorialPagosReservacion implements OnInit {

  titulo: string = 'Historial de Pagos por Reservacion';
  linkAnterior: string = '/atencion-cliente';

  todasLasReservaciones = signal<Reservacion[]>([]);
  pagosReservacion = signal<Pago[]>([]);
  idReservacion!: number;

  constructor(private reservacionService: ReservacionService, private pagoService: PagoService) {}

  ngOnInit(): void {
    this.reservacionService.getReservaciones().subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.todasLasReservaciones.set(reservaciones);
      }
    })
  }

  setReservacion(reservacion: Reservacion) {
    this.idReservacion = reservacion.numReservacion!;
    this.pagoService.getPagosPorReservacion(reservacion.numReservacion!).subscribe({
      next: (pagos: Pago[]) => {
        this.pagosReservacion.set(pagos);
      }
    })
  }

}
