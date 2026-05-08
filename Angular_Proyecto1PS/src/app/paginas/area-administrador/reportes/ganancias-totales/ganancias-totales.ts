import { Component, signal } from '@angular/core';
import { Reportes } from '../reportes';
import { FormsModule } from '@angular/forms';
import { Reservacion } from '../../../../modelos/reservacion/reservacion';
import { ReporteService } from '../../../../restapi/reporte-service/reporte-service';
import { CancelacionService } from '../../../../restapi/cancelacion-service/cancelacion-service';
import { Cancelacion } from '../../../../modelos/cancelacion/cancelacion';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-ganancias-totales',
  imports: [Reportes, FormsModule],
  templateUrl: './ganancias-totales.html',
  styleUrl: './ganancias-totales.css',
})
export class GananciasTotales {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  reservacionesFecha = signal<Reservacion[]>([]);
  ganancias = signal<number>(0);
  cancelaciones = signal<Cancelacion[]>([]);
  reportePdf = signal<Blob | null>(null);

  constructor(
    private reporteService: ReporteService, 
    private cancelacionService: CancelacionService, private reportePDFService: ReportePDFService) {}

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
    
    this.cancelacionService.getCancelaciones(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (cancelaciones: Cancelacion[]) => {
        this.cancelaciones.set(cancelaciones);
      }
    })
  }

  calcularGanancias(): void {
    let sumaGanancia: number = 0;
    for (let i = 0; i < this.reservacionesFecha().length; i++) {
      this.reporteService.getCostoPaquete(this.reservacionesFecha()[i].paquete).subscribe({
        next: (costoServicio: number) => {
          sumaGanancia += (this.reservacionesFecha()[i].costo - costoServicio);
          this.ganancias.set(sumaGanancia);
        }
      })
    }
  }

  private buscarPDF(): void {
    this.reportePDFService.getGananciasPDF(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'reporte de Ganancias.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
