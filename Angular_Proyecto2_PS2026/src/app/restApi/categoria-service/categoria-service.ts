import { Injectable } from "@angular/core";
import { Apiurl } from "../apiurl/apiurl";
import { HttpClient } from "@angular/common/http";
import { Categoria } from "../../models/categoria/categoria";
import { Observable } from "rxjs";
import { Habilidad } from "../../models/habilidad/habilidad";

@Injectable({
  providedIn: 'root'
})

export class CategoriaService {

  constructor(private httpClient: HttpClient, private apiUrl: Apiurl){}

  public getTodasLasCategorias(): Observable<Categoria[]> {
    return this.httpClient.get<Categoria[]>(`${this.apiUrl.getUrl()}categoria/categorias`);
  }

  public getCategoriaPorId(idCategoria: number): Observable<Categoria> {
    return this.httpClient.get<Categoria>(`${this.apiUrl.getUrl()}categoria/por-id/${idCategoria}`);
  }

  public getHabilidadesProyecto(idCategoria: number): Observable<Habilidad[]> {
    return this.httpClient.get<Habilidad[]>(`${this.apiUrl.getUrl()}categoria/habilidades-proyecto/${idCategoria}`);
  }

  public agregarCategoria(categoria: Categoria, idPropuesta: number): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl.getUrl()}categoria/${idPropuesta}`, categoria);
  }

  public editarCategoria(idCategoria: number, categoria: Categoria): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl.getUrl()}categoria/${idCategoria}`, categoria);
  }

  public eliminarCategoria(idCategoria: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl.getUrl()}categoria/${idCategoria}`);
  }
}
