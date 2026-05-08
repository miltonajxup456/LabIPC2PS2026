import { Component, signal } from '@angular/core';
import { Reservacion } from '../../../../modelos/reservacion/reservacion';
import { ReporteService } from '../../../../restapi/reporte-service/reporte-service';
import { Reportes } from "../reportes";
import { FormsModule } from '@angular/forms';
import { ReportePDFService } from '../../../../restapi/reporte-pdf-service/reporte-pdf-service';

@Component({
  selector: 'app-agente-mas-ganancias',
  imports: [Reportes, FormsModule],
  templateUrl: './agente-mas-ganancias.html',
  styleUrl: './agente-mas-ganancias.css',
})
export class AgenteMasGanancias {

  fechaInicial: string = '';
  fehcaFinal: string = '';
  reservacionesFecha = signal<Reservacion[]>([]);
  mostrar = signal(false);
  agenteDeRegistro = signal<string | null>(null);
  ganancias = signal<number>(0);
  reportePdf = signal<Blob | null>(null);

  constructor(private reporteService: ReporteService, private reportePDFService: ReportePDFService) {}

  buscarPorFechas(): void {
    const dateInicial: Date = new Date(this.fechaInicial);
    const dateFinal: Date = new Date(this.fehcaFinal);

    if (dateInicial > dateFinal) {
      alert('La fecha inicial no puede ser posterior a la fecha Final')
      return;
    }

    this.reporteService.getGananciasAgenteVentas(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (reservaciones: Reservacion[]) => {
        this.reservacionesFecha.set(reservaciones)
        this.mostrar.set(true);
        this.calcularGanancias();
        this.buscarPDF();
        if (reservaciones.length > 0) {
          this.agenteDeRegistro.set(reservaciones[0].nombreCliente!);
        }
      }
    })
  }

  calcularGanancias(): void {
    let sumaGanancia: number = 0;
    for (let i = 0; i < this.reservacionesFecha().length; i++) {
      this.reporteService.getCostoPaquete(this.reservacionesFecha()[i].paquete).subscribe({
        next: (costoServicio: number) => {
          sumaGanancia = sumaGanancia + (this.reservacionesFecha()[i].costo - costoServicio);
          this.ganancias.set(sumaGanancia);
        }
      })
    }
  }

  private buscarPDF(): void {
    this.reportePDFService.getAgenteGananciasPDF(this.fechaInicial, this.fehcaFinal).subscribe({
      next: (pdf: Blob) => {
        this.reportePdf.set(pdf);
      }
    })
  }

  descargarPdf(): void {
    const url = window.URL.createObjectURL(this.reportePdf()!);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'reporte de mas ganancias de agente de un ventas.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
