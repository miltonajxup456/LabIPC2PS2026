import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { Reservacion } from "../../modelos/reservacion/reservacion";

@Injectable({
  providedIn: 'root'
})
export class ReservacionService {
  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public getReservaciones(): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reservacion`);
  }

  public getHistorialReservacionCliente(dpi: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reservacion/dpi/${dpi}`);
  }

  public getReservacionesFecha(fecha: string): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reservacion/fecha/${fecha}`)
  }

  public getReservacionesFechaDestino(fecha: string, idDestino: number): Observable<Reservacion[]> {
    return this.httpClient.get<Reservacion[]> (`${this.restApi.getApiURL()}reservacion/fecha-destino/${fecha}/${idDestino}`)
  }

  public crearReservacion(nuevo: Reservacion): Observable<void> {
    return this.httpClient.post<void> (`${this.restApi.getApiURL()}reservacion`, nuevo);
  }

  public actualizarReservacion(actual: Reservacion): Observable<void> {
    return this.httpClient.put<void> (`${this.restApi.getApiURL()}reservacion`, actual);
  }

}
