import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Propuesta } from "../../models/propuesta/propuesta";

@Injectable({
  providedIn: 'root'
})

export class PropuestaHabilidadService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

  public getPropuestas(): Observable<Propuesta[]> {
    return this.httpClient.get<Propuesta[]>(`${this.apiUrl.getUrl()}propuesta-habilidad`);
  }

  public agregarPropuesta(propuesta: Propuesta): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}propuesta-habilidad`, propuesta);
  }

  public modificarPropuesta(propuesta: Propuesta, idPropuesta: number): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}propuesta-habilidad/${idPropuesta}`, propuesta);
  }

  public eliminarPropuesta(idPropuesta: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}propuesta-habilidad/${idPropuesta}`);
  }

}