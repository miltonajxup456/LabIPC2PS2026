import { Component, OnInit, signal } from '@angular/core';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { Reservacion } from '../../../modelos/reservacion/reservacion';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";

@Component({
  selector: 'app-todas-las-reservaciones',
  imports: [Head, RegresoMenu],
  templateUrl: './todas-las-reservaciones.html',
  styleUrl: './todas-las-reservaciones.css',
})
export class TodasLasReservaciones implements OnInit {

  titulo: string = 'Todas las Reservaciones';
  linkAnterior: string = '/atencion-cliente';

  todasLasReservaciones = signal<Reservacion[]>([]);

  constructor(private reservacionService: ReservacionService) {}

  ngOnInit(): void {
    this.reservacionService.getReservaciones().subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.todasLasReservaciones.set(reservaciones);
      }
    })
  }

}
