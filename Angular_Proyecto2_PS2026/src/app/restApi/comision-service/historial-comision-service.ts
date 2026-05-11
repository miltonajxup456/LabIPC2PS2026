import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { GananciaFreelancer } from "../../models/reporte/ganancia-freelancer";
import { CategoriaActiva } from "../../models/reporte/categoria-activa";
import { GananciasPlataforma } from "../../models/reporte/ganancias-plataforma";

@Injectable({
    providedIn: 'root'
})

export class HistorialComisionService {

    constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

    public getComisionGanada(): Observable<number> {
        return this.httpClient.get<number>(`${this.apiUrl.getUrl()}historial-comision/comision-total/nada/nada2`);
    }

    public getGananciasFreelancer(fechaInicial: string, fechaFinal: string): Observable<GananciaFreelancer[]> {
        return this.httpClient.get<GananciaFreelancer[]>(`${this.apiUrl.getUrl()}historial-comision/ganancias-freelancer/${fechaInicial}/${fechaFinal}`);
    }

    public getCategoriaActiva(fechaInicial: string, fechaFinal: string): Observable<CategoriaActiva[]> {
        return this.httpClient.get<CategoriaActiva[]>(`${this.apiUrl.getUrl()}historial-comision/categoria-activa/${fechaInicial}/${fechaFinal}`);
    }

    public getGananciasPlataforma(fechaInicial: string, fechaFinal: string): Observable<GananciasPlataforma> {
        return this.httpClient.get<GananciasPlataforma>(`${this.apiUrl.getUrl()}historial-comision/ganancias-plataforma/${fechaInicial}/${fechaFinal}`);
    }
    
}