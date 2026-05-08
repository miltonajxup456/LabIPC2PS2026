import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Cliente } from "../../modelos/cliente/cliente";

@Injectable({
  providedIn: 'root'
})

export class PasajerosService {
  constructor(private restApi: RestUrl, private httpClient: HttpClient){}

  public getPasajeros(): Observable<Cliente[]> {
    return this.httpClient.get<Cliente[]> (`${this.restApi.getApiURL()}cliente`);
  }

  public crearCliente(nuevo: Cliente): Observable<void> {
    return this.httpClient.post<void> (`${this.restApi.getApiURL()}cliente`, nuevo);
  }

  public eliminarCliente(idCliente: number): Observable<void> {
    return this.httpClient.delete<void> (`${this.restApi.getApiURL()}/${idCliente}`);
  }
}
