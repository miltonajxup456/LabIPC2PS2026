import { Component, signal } from '@angular/core';
import { Reservacion } from '../../../../modelos/reservacion/reservacion';
import { ReporteService } from '../../../../restapi/reporte-service/reporte-service';
import { Reportes } from "../reportes";
import { FormsModule } from '@angular/forms';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-paquete-menos-vendido',
  imports: [Reportes, FormsModule],
  templateUrl: './paquete-menos-vendido.html',
  styleUrl: './paquete-menos-vendido.css',
})
export class PaqueteMenosVendido {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  reservacionesFecha = signal<Reservacion[]>([]);
  mostrar = signal(false);
  nombrePaquete = signal<string | null>(null);
  reportePdf = signal<Blob | null>(null);

  constructor(private reporteService: ReporteService, private reportePDFService: ReportePDFService) {}

  buscarPorFechas(): void {
    const dateInicial: Date = new Date(this.fechaInicial);
    const dateFinal: Date = new Date(this.fehcaFinal);

    if (dateInicial > dateFinal) {
      alert('La fecha inicial no puede ser posterior a la fecha Final')
      return;
    }

    this.reporteService.getPaqueteMenosVendido(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservacionesFecha.set(reservaciones)
        this.mostrar.set(true);
        this.buscarPDF();
        if (reservaciones.length > 0) {
          this.nombrePaquete.set(reservaciones[0].nombrePaquete!);
        }
      }
    })
  }
  
  private buscarPDF(): void {
    this.reportePDFService.getPaqueteMenosVendido(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'reporte de paquete menos vendido.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
