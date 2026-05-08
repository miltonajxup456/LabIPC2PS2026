import { Component, signal } from '@angular/core';
import { Reportes } from "../reportes";
import { FormsModule } from '@angular/forms';
import { CancelacionService } from '../../../../restapi/cancelacion-service/cancelacion-service';
import { Cancelacion } from '../../../../modelos/cancelacion/cancelacion';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-cancelaciones',
  imports: [Reportes, FormsModule],
  templateUrl: './cancelaciones.html',
  styleUrl: './cancelaciones.css',
})
export class Cancelaciones {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  cancelacionesFecha = signal<Cancelacion[]>([]);
  montoReembolsado = signal<number>(0);
  mostrar = signal(false);
  reportePdf = signal<Blob | null>(null);

  constructor(private cancelacionService: CancelacionService, private reportePDFService: ReportePDFService) {}

  buscarPorFechas(): void{
    const dateInicial: Date = new Date(this.fechaInicial);
    const dateFinal: Date = new Date(this.fehcaFinal);

    if (dateInicial > dateFinal) {
      alert('La fecha inicial no puede ser posterior a la fecha Final')
      return;
    }

    this.cancelacionService.getCancelaciones(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (cancelaciones: Cancelacion[]) => {
        this.cancelacionesFecha.set(cancelaciones);
        this.mostrar.set(true);
        this.buscarPDF();
      }
    })
  }

  cantidadReembolsado(cancelacion: Cancelacion): number {
    let monto: number = cancelacion.dineroPagado * (cancelacion.porcentajeReembolso / 100);
    return monto;
  }

  private buscarPDF(): void {
    this.reportePDFService.getCancelacionesPDF(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = "reporte de cancelaciones.pdf";
    a.click();

    window.URL.revokeObjectURL(url);
  }

}
