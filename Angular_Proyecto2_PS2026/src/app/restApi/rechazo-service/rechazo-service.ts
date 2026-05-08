import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Rechazo } from "../../models/rechazo/rechazo";

@Injectable({
    providedIn: 'root'
})

export class RechazoService {

    constructor (private httpClient: HttpClient, private apiUrl: Apiurl) {}

    public getRechazos(idProyecto: number): Observable<Rechazo[]> {
        return this.httpClient.get<Rechazo[]>(`${this.apiUrl.getUrl()}rechazo/${idProyecto}`);
    }

    public agregarRechazo(rechazo: Rechazo): Observable<void> {
        return this.httpClient.post<void>(`${this.apiUrl.getUrl()}rechazo`, rechazo);
    }
}