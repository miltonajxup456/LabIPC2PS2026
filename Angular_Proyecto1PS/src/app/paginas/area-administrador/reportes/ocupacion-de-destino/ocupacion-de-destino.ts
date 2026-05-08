import { Component, signal } from '@angular/core';
import { Reservacion } from '../../../../modelos/reservacion/reservacion';
import { ReporteService } from '../../../../restapi/reporte-service/reporte-service';
import { Reportes } from '../reportes';
import { FormsModule } from '@angular/forms';
import { OcupacionDestino } from '../../../../modelos/ocupacion-destino/ocupacion-destino';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-ocupacion-de-destino',
  imports: [Reportes, FormsModule],
  templateUrl: './ocupacion-de-destino.html',
  styleUrl: './ocupacion-de-destino.css',
})
export class OcupacionDeDestino {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  reservacionesFecha = signal<OcupacionDestino[]>([]);
  mostrar = signal(false);
  reportePdf = signal<Blob | null>(null);

  constructor(private reporteService: ReporteService, private reportePDFService: ReportePDFService) {}

  buscarPorFechas(): void {
    const dateInicial: Date = new Date(this.fechaInicial);
    const dateFinal: Date = new Date(this.fehcaFinal);

    if (dateInicial > dateFinal) {
      alert('La fecha inicial no puede ser posterior a la fecha Final')
      return;
    }

    this.reporteService.getOcupacionDestino(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (reservaciones: OcupacionDestino[]) => {
        this.reservacionesFecha.set(reservaciones)
        this.mostrar.set(true);
        this.buscarPDF();
      }
    })
  }
  
  private buscarPDF(): void {
    this.reportePDFService.getOcupacionDestinoPDF(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'reporte de ocupacion de destino.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
