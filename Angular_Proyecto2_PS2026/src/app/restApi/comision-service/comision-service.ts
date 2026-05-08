import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Comision } from "../../models/comision/comision";

@Injectable({
  providedIn: 'root'
})

export class ComisionService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl){}

  public getUltimaComision(): Observable<Comision> {
    return this.httpClient.get<Comision>(`${this.apiUrl.getUrl()}comision/ultima-comision`);
  }

  public getComisionPorID(idComision: number): Observable<Comision> {
    return this.httpClient.get<Comision>(`${this.apiUrl.getUrl()}comision/comision-id/${idComision}`);
  }

  public getHistorial(): Observable<Comision[]> {
    return this.httpClient.get<Comision[]>(`${this.apiUrl.getUrl()}comision/historial`);
  }

  public agregarComision(comision: Comision): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}comision`, comision);
  }

}
