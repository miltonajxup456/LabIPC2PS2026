import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { Cliente } from "../../modelos/cliente/cliente";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class ClienteService {
  constructor(private restApi: RestUrl, private httpClient: HttpClient){}

  public getClientes(): Observable<Cliente[]> {
    return this.httpClient.get<Cliente[]> (`${this.restApi.getApiURL()}cliente`);
  }

  public crearCliente(cliente: Cliente): Observable<void> {
    return this.httpClient.post<void> (`${this.restApi.getApiURL()}cliente`, cliente);
  }

  public actualizarCliente(idCliente: string, cliente: Cliente): Observable<void> {
    return this.httpClient.put<void> (`${this.restApi.getApiURL()}cliente/${idCliente}`, cliente);
  }

  public eliminarCliente(idCliente: string): Observable<void> {
    return this.httpClient.delete<void> (`${this.restApi.getApiURL()}cliente/${idCliente}`);
  }


}
