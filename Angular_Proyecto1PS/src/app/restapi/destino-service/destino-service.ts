import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { Destino } from "../../modelos/destino/destino";

@Injectable({
  providedIn: 'root'
})
export class DestinoService {

  constructor(private httpClient: HttpClient,private restApi: RestUrl) {}

  public getAllDestinos(): Observable<Destino[]> {
    return this.httpClient.get<Destino[]>(`${this.restApi.getApiURL()}destino`);
  }

  public crearDestino(destino: Destino): Observable<void> {
    return this.httpClient.post<void>(`${this.restApi.getApiURL()}destino`, destino);
  }

  public actualizarDestino(idDestino: number, destino: Destino): Observable<void> {
    return this.httpClient.put<void> (`${this.restApi.getApiURL()}destino/${idDestino}`, destino)
  }

  public borrarDestino(destino: Destino): Observable<void> {
    return this.httpClient.delete<void>(`${this.restApi.getApiURL()}destino`, {body: destino});
  }

  public borrarDestinoPorId(idDestino: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.restApi.getApiURL()}destino/${idDestino}`);
  }

}
