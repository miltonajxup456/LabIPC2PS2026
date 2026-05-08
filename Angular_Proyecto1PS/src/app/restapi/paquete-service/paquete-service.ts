import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { Paquete } from "../../modelos/paquete/paquete";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class PaqueteService {
  constructor(private restApi: RestUrl, private httpClient: HttpClient) {}

  public getAllPaquetes(): Observable<Paquete[]> {
    return this.httpClient.get<Paquete[]> (`${this.restApi.getApiURL()}paquete`);
  }

  public getPaqueteDestino(idDestino: number): Observable<Paquete[]> {
    return this.httpClient.get<Paquete[]> (`${this.restApi.getApiURL()}paquete/destino/${idDestino}`)
  }
  
  public crearPaquete(paquete: Paquete): Observable<void> {
    return this.httpClient.post<void> (`${this.restApi.getApiURL()}paquete`, paquete);
  }

  public actualizarPaquete(idPaquete: number, actual: Paquete): Observable<void> {
    return this.httpClient.put<void> (`${this.restApi.getApiURL()}paquete/${idPaquete}`, actual);
  }

  public eliminarPaquete(idPaquete: number): Observable<void> {
    return this.httpClient.delete<void> (`${this.restApi.getApiURL()}paquete/${idPaquete}`);
  }

}
