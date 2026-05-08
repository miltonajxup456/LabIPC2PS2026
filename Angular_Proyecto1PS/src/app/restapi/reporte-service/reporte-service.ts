import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Reservacion } from "../../modelos/reservacion/reservacion";
import { Observable } from "rxjs";
import { OcupacionDestino } from "../../modelos/ocupacion-destino/ocupacion-destino";

@Injectable({
    providedIn: 'root'
})

export class ReporteService {

  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public getReservacionesEntreFechas(fechaInicial: string, fechaFinal: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reporte/entre-fechas/${fechaInicial}/${fechaFinal}`);
  }

  public getReservacionesAgenteVentas(fechaInicial: string, fechaFinal: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reporte/agente-ventas/${fechaInicial}/${fechaFinal}`);
  }

  public getGananciasAgenteVentas(fechaInicial: string, fechaFinal: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reporte/ganancias/${fechaInicial}/${fechaFinal}`);
  }

  public getCostoPaquete(idPaquete: number): Observable<number> {
    return this.httpClient.get<number> (`${this.restApi.getApiURL()}reporte/costo-paquete/${idPaquete}`);
  }

  public getPaqueteMasVendido(fechaInicial: string, fechaFinal: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reporte/paquete-mas-vendido/${fechaInicial}/${fechaFinal}`);
  }

  public getPaqueteMenosVendido(fechaInicial: string, fechaFinal: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reporte/paquete-menos-vendido/${fechaInicial}/${fechaFinal}`);
  }

  public getOcupacionDestino(fechaInicial: string, fechaFinal: string): Observable<OcupacionDestino[]> {
    return this.httpClient.get<OcupacionDestino[]> (`${this.restApi.getApiURL()}reporte/ocupacion-destino/${fechaInicial}/${fechaFinal}`);
  }

}