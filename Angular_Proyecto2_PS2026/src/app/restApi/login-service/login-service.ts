import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Usuario } from "../../models/usuario/usuario";
import { Generico } from "../../models/extras/generico/generico";

@Injectable({
  providedIn: 'root'
})

export class LoginService {

  constructor(private httpClient: HttpClient, private apiurl: Apiurl){}

  public reIngresar(): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${this.apiurl.getUrl()}login/re-ingresar`);
  }

  public cerrarSesion(): Observable<void> {
    return this.httpClient.get<void>(`${this.apiurl.getUrl()}login/cerrar-sesion`);
  }

  public ingresar(solicitante: Usuario): Observable<Generico<Usuario>> {
    return this.httpClient.post<Generico<Usuario>> (`${this.apiurl.getUrl()}login`, solicitante);
  }

}
