import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { CalificacionFreelancer } from "../../models/calificacion-freelancer/calificacion-freelancer";

@Injectable({
    providedIn: 'root'
})

export class CalificacionService {

    constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

    public getCalificacionesFreelancer(idFreelancer: string): Observable<CalificacionFreelancer[]> {
        return this.httpClient.get<CalificacionFreelancer[]>(`${this.apiUrl.getUrl()}calificacion/freelancer/${idFreelancer}`);
    }

    public getCalificacionProyecto(idProyecto: number): Observable<CalificacionFreelancer> {
        return this.httpClient.get<CalificacionFreelancer>(`${this.apiUrl.getUrl()}calificacion/proyecto/${idProyecto}`);
    }

    public agregarCalificacion(calificacion: CalificacionFreelancer): Observable<void> {
        return this.httpClient.post<void>(`${this.apiUrl.getUrl()}calificacion`, calificacion);
    }

    public modificarCalificacion(calificacion: CalificacionFreelancer, idCalificacion: number): Observable<void> {
        return this.httpClient.put<void>(`${this.apiUrl.getUrl()}calificacion/${idCalificacion}`, calificacion);
    }
}