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

  public ingresar(solicitante: Usuario): Observable<Generico<Usuario>> {
    return this.httpClient.post<Generico<Usuario>> (`${this.apiurl.getUrl()}login`, solicitante);
  }

}
