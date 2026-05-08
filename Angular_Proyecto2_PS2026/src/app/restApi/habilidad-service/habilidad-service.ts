import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { HttpClient } from "@angular/common/http";
import { Habilidad } from "../../models/habilidad/habilidad";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class HabilidadService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl){}

  public getHabilidades(): Observable<Habilidad[]> {
    return this.httpClient.get<Habilidad[]>(`${this.apiUrl.getUrl()}habilidad/habilidades`);
  }

  public agregarHabilidad(habilidad: Habilidad, idPropuesta: number): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}habilidad/${idPropuesta}`, habilidad);
  }

  public editarHabilidad(idHabilidad: number, habilidad: Habilidad): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}habilidad/${idHabilidad}`, habilidad);
  }

  public eliminarHabilidad(idHabilidad: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}habilidad/${idHabilidad}`);
  }
  
}
