import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { Pago } from "../../modelos/pago/pago";

@Injectable({
  providedIn: 'root'
})

export class PagoService {
  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public getPagosPorReservacion(idReservacion: number): Observable<Pago[]> {
    return this.httpClient.get<Pago[]> (`${this.restApi.getApiURL()}pago/${idReservacion}`);
  }
  public crearRegistroPago(pago: Pago): Observable<Blob> {
    return this.httpClient.post (`${this.restApi.getApiURL()}pago`, pago, {responseType: 'blob'});
  }
}
