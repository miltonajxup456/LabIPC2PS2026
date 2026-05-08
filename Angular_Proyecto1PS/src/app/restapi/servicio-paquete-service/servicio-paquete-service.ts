import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { ServicioPaquete } from "../../modelos/servicio-paquete/servicio-paquete";

@Injectable({
  providedIn: 'root'
})
export class ServicioPaqueteService {

  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public geServiciosPorIdPaquete(idPaquete: number): Observable<ServicioPaquete[]> {
    return this.httpClient.get<ServicioPaquete[]> (`${this.restApi.getApiURL()}servicio/${idPaquete}`);
  }

  public agregarServicioAPaquete(nuevo: ServicioPaquete): Observable<void> {
    return this.httpClient.post<void> (`${this.restApi.getApiURL()}servicio`, nuevo);
  }

  public actualizarServicioPaquete(idServicio: number, servicio: ServicioPaquete): Observable<void> {
    return this.httpClient.put<void> (`${this.restApi.getApiURL()}servicio/${idServicio}`, servicio)
  }

  public eliminarServicioDePaquete(idServicio: number): Observable<void> {
    return this.httpClient.delete<void> (`${this.restApi.getApiURL()}servicio/${idServicio}`);
  }
}
