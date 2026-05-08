import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Observable } from "rxjs";
import { RegistroErrores } from "../../modelos/registro-errores/registro-errores";

@Injectable({
  providedIn: 'root'
})

export class CargarArchivosService {

  constructor(private httpClient: HttpClient, private restApi: RestUrl) {}

  public cargarArchivo(formData: FormData): Observable<RegistroErrores> {
    return this.httpClient.post<RegistroErrores> (`${this.restApi.getApiURL()}archivo`, formData);
  }
}
