import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Cancelacion } from "../../models/cancelacion/cancelacion";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class CancelacionService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

  public getCancelacion(idProyecto: number, idFreelancer: string): Observable<Cancelacion> {
    return this.httpClient.get<Cancelacion>(`${this.apiUrl.getUrl()}cancelacion/${idProyecto}/${idFreelancer}`);
  }

  public agregarCancelacion(cancelacion: Cancelacion): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}cancelacion`, cancelacion);
  }
}