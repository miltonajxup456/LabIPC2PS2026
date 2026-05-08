import { Component, signal } from '@angular/core';
import { Reportes } from "../reportes";
import { FormsModule } from '@angular/forms';
import { ReservacionService } from '../../../../restapi/reservacion-service/reservacion-service';
import { Reservacion } from '../../../../modelos/reservacion/reservacion';
import { ReporteService } from '../../../../restapi/reporte-service/reporte-service';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-ventas',
  imports: [Reportes, FormsModule],
  templateUrl: './ventas.html',
  styleUrl: './ventas.css',
})
export class Ventas {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  reservacionesFecha = signal<Reservacion[]>([]);
  ganancias = signal<number>(0);
  reportePdf = signal<Blob | null>(null);

  constructor(private reporteService: ReporteService, private reportePDFService: ReportePDFService) {}

  buscarPorFechas(): void{
    const dateInicial: Date = new Date(this.fechaInicial);
    const dateFinal: Date = new Date(this.fehcaFinal);

    if (dateInicial > dateFinal) {
      alert('La fecha inicial no puede ser posterior a la fecha Final')
      return;
    }

    this.reporteService.getReservacionesEntreFechas(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservacionesFecha.set(reservaciones);
        this.calcularGanancias();
        this.buscarPDF();
      }
    })
  }

  private calcularGanancias(): void {
    let suma: number = 0;
    for (let i = 0; i < this.reservacionesFecha().length; i++) {
      suma += this.reservacionesFecha()[i].costo;
    }
    this.ganancias.set(suma);
  }

  private buscarPDF(): void {
    this.reportePDFService.getRervacionesVentasPDF(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = "reporte de ventas.pdf";
    a.click();

    window.URL.revokeObjectURL(url);
  }

}
