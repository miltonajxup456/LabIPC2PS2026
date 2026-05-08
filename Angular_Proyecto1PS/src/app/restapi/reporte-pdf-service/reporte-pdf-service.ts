import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})

export class ReportePDFService {

  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public getRervacionesVentasPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-ventas/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'});
  }

  public getGananciasPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-ganancias/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'});
  }

  public getCancelacionesPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-cancelaciones/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'})
  }

  public getAgenteVentasPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-agente-ventas/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'})
  }

  public getAgenteGananciasPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-agente-ganancias/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'});
  }

  public getPaqueteMasVendidoPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-paquete-mas-vendido/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'});
  }

  public getPaqueteMenosVendido(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-paquete-menos-vendido/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'});
  }

  public getOcupacionDestinoPDF(fechaInicial: string, fechaFinal: string): Observable<Blob> {
    return this.httpClient.get (`${this.restApi.getApiURL()}pdf-reporte/pdf-ocupacion-destino/${fechaInicial}/${fechaFinal}`, {responseType: 'blob'});
  }

}