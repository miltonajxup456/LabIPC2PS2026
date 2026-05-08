import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { RestUrl } from "../rest-url/rest-url";
import { HttpClient } from "@angular/common/http";
import { Cancelacion } from "../../modelos/cancelacion/cancelacion";

@Injectable({
  providedIn: 'root'
})

export class CancelacionService {

  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public getCancelaciones(fechaInicial: string, fechaFinal: string): Observable<Cancelacion[]> {
    return this.httpClient.get<Cancelacion[]> (`${this.restApi.getApiURL()}cancelacion/${fechaInicial}/${fechaFinal}`)
  }

  public cancelarReservacion(idReservacion: number, porcentaje: number): Observable<void> {
    const cancelacion = {idReservacion: idReservacion, porcentaje: porcentaje};
    return this.httpClient.post<void> (`${this.restApi.getApiURL()}cancelacion`, cancelacion);
  }

}
