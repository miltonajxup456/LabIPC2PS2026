import { Component, OnInit, signal } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Reservacion } from '../../../modelos/reservacion/reservacion';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PagoService } from '../../../restapi/pago-service/pago-service';
import { Pago } from '../../../modelos/pago/pago';

@Component({
  selector: 'app-procesar-pago',
  imports: [Head, RegresoMenu, ReactiveFormsModule],
  templateUrl: './procesar-pago.html',
  styleUrl: './procesar-pago.css',
})
export class ProcesarPago implements OnInit {

  titulo: string = 'Procesar Pago';
  linkAnterior: string = '/atencion-cliente';

  formPago!: FormGroup;

  reservaciones = signal<Reservacion[]>([]);

  reservacionElegida = signal<Reservacion | null>(null);
  formaDePago!: number;
  dineroPagado!: number;
  numeroReservacion!: number;
  dineroFormulario!: number;
  pdfDePago = signal<Blob | null>(null);

  constructor(private reservacionesService: ReservacionService, private pagoService: PagoService) {}

  ngOnInit(): void {
    this.formPago = new FormGroup({
      cantidadPagada: new FormControl<number>(0)
    })

    this.buscarReservaciones();
  }

  setReservacion(eleccion: Reservacion) {
    if (eleccion.estado === 3) {
      alert('No es posible seleccionar una reservacion Cancelada');
      return;
    }
    if (eleccion.estado === 4) {
      alert('No es posible seleccionar las reservaciones que ya estan completadas');
      return;
    }
    this.reservacionElegida.set(eleccion);
    this.dineroPagado = eleccion.dineroCancelado ?? 0;
    this.numeroReservacion = eleccion.numReservacion!;
  }

  setMetodo(metodo: number) {
    this.formaDePago = metodo;
  }

  completarPago(): void {
    if(!this.reservacionElegida) {
      alert('Antes de poder pagar se debe escoger una Reservacion');
      return;
    }
    if(!this.formaDePago) {
      alert('Se debe de especificar la forma de pago');
      return;
    }
    this.dineroFormulario = this.formPago.value.cantidadPagada;
    if(this.dineroFormulario < 0) {
      alert('No se pueden ingresar cantidades negativas');
      return;
    }
    if (this.dineroFormulario == 0) {
      alert('Ingresa una cantidad mayor a 0 para que sea un pago valido');
      return;
    }
    if (this.dineroFormulario > (this.reservacionElegida()!.costo - this.dineroPagado)) {
      alert('No se puede pagar mas del costo total de la reservacion');
      return;
    }
    
    const pago: Pago = {
      montoPagado: this.dineroFormulario,
      numReservacion: this.numeroReservacion,
      metodo: this.formaDePago
    }
    this.pagoService.crearRegistroPago(pago).subscribe ({
      next: (pdf: Blob) => {
        alert('El pago se realizó con exito');
        this.pdfDePago.set(pdf);
        this.dineroPagado += this.dineroFormulario;
        this.formPago.reset();
        this.reservacionElegida.update(cont => cont ? {...cont, dineroCancelado: this.dineroPagado}: cont);
        this.buscarReservaciones();
        if (this.dineroPagado >= this.reservacionElegida()!.costo) {
          this.reservacionElegida.update(cont => cont ? {...cont, estado: 2}: cont);
          this.reservacionesService.actualizarReservacion(this.reservacionElegida()!).subscribe({
            next: () => {
              alert('El pago de la reservacion ha sido completado')
            }
          });
        }
      },
      error: () => {
        alert('No se puedo completar el pago');
      }
    })
  }

  descargarPDF(): void {
    const url = window.URL.createObjectURL(this.pdfDePago()!);

    const a = document.createElement('a');
    a.href = url;
    a.download = "Recibo_de_pago.pdf";
    a.click();

    window.URL.revokeObjectURL(url);
  }

  otraFormaPDF(): void {
    //this.urlPDF = window.URL.createObjectURL(this.pdfPago()!);
  }

  /*
ngOnDestroy(): void {
  if (this.pdfUrl) {
    window.URL.revokeObjectURL(this.pdfUrl);
  }
}
  */

  buscarReservaciones(): void {
    this.reservacionesService.getReservaciones().subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservaciones.set(reservaciones)
      }
    });
  }

}
