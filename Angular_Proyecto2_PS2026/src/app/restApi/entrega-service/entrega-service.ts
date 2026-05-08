import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Entrega } from "../../models/entrega/entrega";

@Injectable({
  providedIn: 'root'
})
export class EntregaService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl){}

  public getEntregasProyecto(idProyecto: number): Observable<Entrega[]> {
    return this.httpClient.get<Entrega[]>(`${this.apiUrl.getUrl()}/entrega/entregas-proyecto/${idProyecto}`);
  }

  public getEntrega(idEntrega: number): Observable<Blob> {
    return this.httpClient.get(`${this.apiUrl.getUrl()}entrega/entrega/${idEntrega}`, {responseType: 'blob'});
  }
  
  public subirEntrega(entrega: FormData): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}entrega`, entrega);
  }
  
}
