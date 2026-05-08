import { Component, signal } from '@angular/core';
import { Reportes } from "../reportes";
import { FormsModule } from '@angular/forms';
import { Reservacion } from '../../../../modelos/reservacion/reservacion';
import { ReporteService } from '../../../../restapi/reporte-service/reporte-service';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-agente-mas-ventas',
  imports: [Reportes, FormsModule],
  templateUrl: './agente-mas-ventas.html',
  styleUrl: './agente-mas-ventas.css',
})
export class AgenteMasVentas {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  reservacionesFecha = signal<Reservacion[]>([]);
  mostrar = signal(false);
  agenteDeRegistro = signal<string | null>(null);
  reportePdf = signal<Blob | null>(null);

  constructor(private reporteService: ReporteService, private reportePDFService: ReportePDFService) {}

  buscarPorFechas(): void {
    const dateInicial: Date = new Date(this.fechaInicial);
    const dateFinal: Date = new Date(this.fehcaFinal);

    if (dateInicial > dateFinal) {
      alert('La fecha inicial no puede ser posterior a la fecha Final')
      return;
    }

    this.reporteService.getReservacionesAgenteVentas(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservacionesFecha.set(reservaciones)
        this.mostrar.set(true);
        if (reservaciones.length > 0) {
          this.agenteDeRegistro.set(reservaciones[0].nombreCliente!);
        }
        this.buscarPDF();
      }
    })
  }

  private buscarPDF(): void {
    this.reportePDFService.getAgenteVentasPDF(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'reporte de mas ventas de un agente de ventas.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
