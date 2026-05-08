import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Recarga } from "../../models/recarga/recarga";
import { Usuario } from "../../models/usuario/usuario";

@Injectable({
  providedIn: 'root'
})

export class RecargaService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

  public getRegistros(): Observable<Recarga[]> {
    return this.httpClient.get<Recarga[]>(`${this.apiUrl.getUrl()}recarga/todos-los-registros`);
  }

  public getRegistrosCliente(idCliente: string): Observable<Recarga[]> {
    return this.httpClient.get<Recarga[]>(`${this.apiUrl.getUrl()}recarga/registros-cliente/${idCliente}`);
  }

  public agregarRecarga(recarga: Recarga): Observable<Usuario> {
    return this.httpClient.post<Usuario>(`${this.apiUrl.getUrl()}recarga`, recarga);
  }
  
}
