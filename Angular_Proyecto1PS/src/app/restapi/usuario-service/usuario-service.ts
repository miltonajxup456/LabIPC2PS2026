import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { RestUrl } from "../rest-url/rest-url";
import { Usuario } from "../../modelos/usuario/usuario";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class UsuarioService {

  constructor(private httpCliente: HttpClient, private restApi: RestUrl) {}

  public getUsuarios(): Observable<Usuario[]> {
    return this.httpCliente.get<Usuario[]> (`${this.restApi.getApiURL()}usuario`);
  }
  
  public crearUsuario(nuevoUsuario: Usuario): Observable<void> {
    return this.httpCliente.post<void> (`${this.restApi.getApiURL()}usuario`, nuevoUsuario);
  }

  public eliminarUsuario(idUsuario: number): Observable<void> {
    return this.httpCliente.delete<void> (`${this.restApi.getApiURL()}usuario/${idUsuario}`);
  }
}
