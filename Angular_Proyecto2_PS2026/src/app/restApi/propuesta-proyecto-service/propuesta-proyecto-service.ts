import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { PropuestaProyecto } from "../../models/propuesta-proyecto/propuesta-proyecto";

@Injectable({
  providedIn: 'root'
})

export class PropuestaProyectoService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl){}

  public getPropuestasEnviadas(fechaInicial: string, fechaFinal: string, idFreelancer: string): Observable<PropuestaProyecto[]> {
    return this.httpClient.get<PropuestaProyecto[]>(`${this.apiUrl.getUrl()}propuesta/propuestas-enviadas/${fechaInicial}/${fechaFinal}/${idFreelancer}`);
  }

  public getPropuestasProyecto(idProyecto: number): Observable<PropuestaProyecto[]> {
    return this.httpClient.get<PropuestaProyecto[]>(`${this.apiUrl.getUrl()}propuesta/propuestas-proyecto/${idProyecto}`);
  }

  public getPropuestaFreelancer(idProyecto: number, idFreelancer: string): Observable<PropuestaProyecto> {
    return this.httpClient.get<PropuestaProyecto>(`${this.apiUrl.getUrl()}propuesta/propuesta-freelancer/${idProyecto}/${idFreelancer}`);
  }

  public agregarPropuesta(propuesta: PropuestaProyecto): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}propuesta`, propuesta);
  }

  public editarPropuesta(idPropuesta: number, idProyecto: number, propuesta: PropuestaProyecto): Observable<PropuestaProyecto> {
    return this.httpClient.put<PropuestaProyecto>(`${this.apiUrl.getUrl()}propuesta/actualizar/${idPropuesta}/${idProyecto}`, propuesta);
  }

  public rechazarPropuesta(idPropuesta: number, propuesta: PropuestaProyecto): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}/propuesta/rechazar/${idPropuesta}`, propuesta);
  }

  public eliminarPropuesta(idPropuesta: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}propuesta/${idPropuesta}`);
  }
  
}
