import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { Proveedor } from "../../modelos/proveedor/proveedor";

@Injectable({
    providedIn: 'root'
})

export class ProveedorService {

    constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

    public getProveedores(): Observable<Proveedor[]> {
        return this.httpClient.get<Proveedor[]> (`${this.restApi.getApiURL()}proveedor`);
    }

    public getProveedoresPais(idDestino: number): Observable<Proveedor[]> {
        return this.httpClient.get<Proveedor[]> (`${this.restApi.getApiURL()}proveedor/${idDestino}`);
    }

    public agregarProveedor(nuevo: Proveedor): Observable<void> {
        return this.httpClient.post<void> (`${this.restApi.getApiURL()}proveedor`, nuevo);
    }

    public modificarProveedor(idProveedor: number, actual: Proveedor): Observable<void> {
        return this.httpClient.put<void> (`${this.restApi.getApiURL()}proveedor/${idProveedor}`, actual);
    }

    public eliminarProveedor(idProveedor: number): Observable<void> {
        return this.httpClient.delete<void> (`${this.restApi.getApiURL()}proveedor/${idProveedor}`);
    }

}