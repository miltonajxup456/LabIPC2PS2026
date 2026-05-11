import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Proyecto } from "../../models/proyecto/proyecto";
import { GastoCategoria } from "../../models/reporte/gasto-categoria";
import { ContratoCompletado } from "../../models/reporte/contrato-completado";
import { CategoriaTrabajada } from "../../models/reporte/categoria-trabajada";

@Injectable({
    providedIn: 'root'
})

export class ReporteService {
    constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

    public getHistorialProyectos(fechaInicial: string, fechaFinal: string, idCliente: string): Observable<Proyecto[]> {
        return this.httpClient.get<Proyecto[]>(`${this.apiUrl.getUrl()}reporte/proyectos-publicados/${fechaInicial}/${fechaFinal}/${idCliente}`);
    }

    public getReporteProyectos(fechaInicial: string, fechaFinal: string, idCliente: string): Observable<Blob> {
        return this.httpClient.get(`${this.apiUrl.getUrl()}reporte/proyectos-publicados-pdf/${fechaInicial}/${fechaFinal}/${idCliente}`, {responseType: 'blob'});
    }

    public getGastoCategoria(fechaInicial: string, fechaFinal: string, idCliente: string): Observable<GastoCategoria[]> {
        return this.httpClient.get<GastoCategoria[]>(`${this.apiUrl.getUrl()}reporte/gasto-categoria/${fechaInicial}/${fechaFinal}/${idCliente}`);
    }

    public getContratosCompletados(fechaInicial: string, fechaFinal: string, idFreelancer: string): Observable<ContratoCompletado[]> {
        return this.httpClient.get<ContratoCompletado[]>(`${this.apiUrl.getUrl()}reporte/contratos-completados/${fechaInicial}/${fechaFinal}/${idFreelancer}`);
    }

    public getCategoriasTrabajadas(idFreelancer: string): Observable<CategoriaTrabajada[]> {
        return this.httpClient.get<CategoriaTrabajada[]>(`${this.apiUrl.getUrl()}reporte/categorias-trabajadas/${idFreelancer}`);;
    }
}