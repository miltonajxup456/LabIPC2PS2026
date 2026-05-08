import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { Observable } from "rxjs";
import { Usuario } from "../../models/usuario/usuario";
import { ListaHabilidades } from "../../models/extras/habilidades/habilidades";

@Injectable({
  providedIn: 'root'
})

export class UsuarioService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl) {}

  public crearAdministrador(): Observable<void> {
    return this.httpClient.get<void>(`${this.apiUrl.getUrl()}usuario/crear-administrador`);
  }

  public getUsuarios(): Observable<Usuario[]> {
    return this.httpClient.get<Usuario[]>(`${this.apiUrl.getUrl()}usuario/todos-los-usuarios`);
  }

  public getUsuario(idUsuario: string): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${this.apiUrl.getUrl()}usuario/usuario-unico/${idUsuario}`);
  }

  public getHabilidadesFreelancer(idUsuario: string): Observable<number[]> {
    return this.httpClient.get<number[]>(`${this.apiUrl.getUrl()}usuario/habilidades-usuario/${idUsuario}`);
  }

  public crearUsuario(usuario: Usuario): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}usuario`, usuario);
  }

  public actualizarCliente(idUsuario: string, usuario: Usuario): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}usuario/actualizar-cliente/${idUsuario}`, usuario);
  }

  public actualizarFreelancer(idUsuario: string, usuario: Usuario): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}usuario/actualizar-freelancer/${idUsuario}`, usuario);
  }

  public agregarHabilidadesFreelancer(idusuario: string, lista: ListaHabilidades): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}usuario/habilidades/${idusuario}`, lista);
  }

  public baneoCuenta(usuario: Usuario, idUsuario: string): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}usuario/baneo/${idUsuario}`, usuario);
  }

  public eliminarUsuario(idUsuario: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}usuario/${idUsuario}`);
  }
  
}
