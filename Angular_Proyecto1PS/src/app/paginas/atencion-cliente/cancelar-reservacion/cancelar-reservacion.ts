import { Component, OnInit, signal } from '@angular/core';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Reservacion } from '../../../modelos/reservacion/reservacion';
import { FormsModule } from '@angular/forms';
import { CancelacionService } from '../../../restapi/cancelacion-service/cancelacion-service';

@Component({
  selector: 'app-cancelar-reservacion',
  imports: [Head, RegresoMenu, FormsModule],
  templateUrl: './cancelar-reservacion.html',
  styleUrl: './cancelar-reservacion.css',
})
export class CancelarReservacion implements OnInit {

  titulo: string = 'Cancelaciones de Reservaviones';
  linkAnterior: string = '/atencion-cliente';
  fechaSetteada: string = '2026-04-01';

  allReservaciones = signal<Reservacion[]>([]);
  reservacionElegida = signal<Reservacion | null>(null);

  constructor(private reservacionService: ReservacionService, private cancelacionService: CancelacionService) {}

  ngOnInit(): void {
    this.traerReservaciones();
  }

  traerReservaciones() {
    this.reservacionService.getReservaciones().subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.allReservaciones.set(reservaciones);
      }
    });
  }

  setReservacion(reservacionElegida: Reservacion) {
    if (reservacionElegida.estado === 3) {
      alert('Esta Reservacion ya se encuentra cancelada');
      return;
    }
    if (reservacionElegida.estado === 4) {
      alert('Esta Reservacion no puede ser cancelada porque ya se ha completado');
      return;
    }
    const fechaLimite: Date = new Date(this.fechaSetteada);
    fechaLimite.setDate(fechaLimite.getDate() +7);
    const fechaViaje: Date = new Date(reservacionElegida.fechaViaje)
    if (fechaLimite > fechaViaje) {
      alert('No es posible cancelar esta Reservacion porque quedan menos de 7 dias');
      return;
    }

    this.reservacionElegida.set(reservacionElegida);
  }

  cancelarReservacion() {
    if(!this.reservacionElegida()) {
      alert('Aun no se ha seleccionado una reservacion para cancelar');
      return;
    }
    
    this.calcularReembolso();
    this.reservacionElegida.update(cont => cont ? {...cont, estado: 3}: cont );
    this.reservacionService.actualizarReservacion(this.reservacionElegida()!).subscribe({
      next: () => {
        alert('Se cancelo con exito la reservacion');
        this.reservacionElegida.set(null);
        this.traerReservaciones();
      }
    })
    
  }
  
  calcularReembolso() {
    const fechaElegida: Date = new Date(this.fechaSetteada);
    const fechaViaje: Date = new Date(this.reservacionElegida()!.fechaViaje);
    const fecha30: Date = new Date(fechaElegida);
    fecha30.setDate(fecha30.getDate() + 30);
    const fecha15: Date = new Date(fechaElegida);
    fecha15.setDate(fecha15.getDate() + 15);
    const fecha7: Date = new Date(fechaElegida);
    fecha7.setDate(fecha7.getDate() + 7);

    if (fecha30 < fechaViaje) {
      alert('Se hizo un reembolso del 100 porciento')
      //100;
      this.cancelacionService.cancelarReservacion(this.reservacionElegida()?.numReservacion!, 100).subscribe();
      return;
    }
    
    if (fecha15 <= fechaViaje && fecha30 >= fechaViaje) {
      alert('Se hizo un reembolso del 70 porciento')
      //70;
      this.cancelacionService.cancelarReservacion(this.reservacionElegida()?.numReservacion!, 70).subscribe();
      return;
    }
    
    if (fecha7 <= fechaViaje && fecha15 > fechaViaje) {
      alert('Se hizo un reembolso del 40 porciento')
      //40; 
      this.cancelacionService.cancelarReservacion(this.reservacionElegida()?.numReservacion!, 40).subscribe();
      return;
    }
    alert('No se puede hacer un reembolso de esta reservacion porque quedan menos de 7 dias');
  }

  completarReservacion() {
    const actual: Reservacion = {...this.reservacionElegida()!, estado: 4};
    this.reservacionService.actualizarReservacion(actual).subscribe({
      next: () => {
        this.reservacionElegida.set(null);
        alert('Se ha completado con exito la reservacion');
        this.traerReservaciones();
      }
    })
  }

}
