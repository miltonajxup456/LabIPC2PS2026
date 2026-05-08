import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Propuesta } from "../../models/propuesta/propuesta";

@Injectable({
  providedIn: 'root'
})

export class PropuestaCategoriaService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

  public getPropuestas(): Observable<Propuesta[]> {
    return this.httpClient.get<Propuesta[]>(`${this.apiUrl.getUrl()}propuesta-categoria`);
  }

  public agregarPropuesta(propuesta: Propuesta): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}propuesta-categoria`, propuesta);
  }

  public modificarPropuesta(propuesta: Propuesta, idPropuesta: number): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}propuesta-categoria/${idPropuesta}`, propuesta);
  }

  public eliminarPropuesta(idPropuesta: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}propuesta-categoria/${idPropuesta}`);
  }

}