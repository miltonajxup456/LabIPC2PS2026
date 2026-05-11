import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Proyecto } from "../../models/proyecto/proyecto";
import { Habilidad } from "../../models/habilidad/habilidad";

@Injectable({
  providedIn: 'root'
})

export class ProyectoService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

  public getProyectos(): Observable<Proyecto[]> {
    return this.httpClient.get<Proyecto[]>(`${this.apiUrl.getUrl()}proyecto/todos-los-proyectos`);
  }

  public getProyectosAbiertos(): Observable<Proyecto[]> {
    return this.httpClient.get<Proyecto[]>(`${this.apiUrl.getUrl()}proyecto/proyectos-abiertos`);
  }

  public getHabilidadesProyecto(idProyecto: number): Observable<Habilidad[]> {
    return this.httpClient.get<Habilidad[]>(`${this.apiUrl.getUrl()}proyecto/habilidades-proyecto/${idProyecto}`);
  }

  public getProyectosCliente(idCliente: string): Observable<Proyecto[]> {
    return this.httpClient.get<Proyecto[]>(`${this.apiUrl.getUrl()}proyecto/cliente/${idCliente}`);
  }

  public getProyectosFreelancer(idFreelancer: string): Observable<Proyecto[]> {
    return this.httpClient.get<Proyecto[]>(`${this.apiUrl.getUrl()}proyecto/freelancer/${idFreelancer}`);
  }

  public agregarProyecto(proyecto: Proyecto): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}proyecto`, proyecto);
  }

  public modificarProyecto(idProyecto: number, proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${this.apiUrl.getUrl()}proyecto/actualizacion/${idProyecto}`, proyecto);
  }

  public aceptarPropuesta(idProyecto: number, proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${this.apiUrl.getUrl()}proyecto/proyecto-aceptado/${idProyecto}`, proyecto);
  }

  public rechazarProyecto(idProyecto: number, proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${this.apiUrl.getUrl()}proyecto/proyecto-rechazado/${idProyecto}`, proyecto);
  }

  public aprobarProyecto(idProyecto: number, proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${this.apiUrl.getUrl()}proyecto/proyecto-completado/${idProyecto}`, proyecto);
  }

  public cancelarProyecto(idProyecto: number, proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${this.apiUrl.getUrl()}proyecto/proyecto-cancelado/${idProyecto}`, proyecto);
  }

  public eliminarProyecto(idProyecto: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}proyecto/${idProyecto}`);
  }

}
