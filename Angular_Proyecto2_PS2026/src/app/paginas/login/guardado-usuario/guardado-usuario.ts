import { Injectable } from "@angular/core";
import { Usuario } from "../../../models/usuario/usuario";
import { UsuarioService } from "../../../restApi/usuario-service/usuario-service";

@Injectable({
  providedIn: 'root'
})

export class GuardadoUsuario {

  usuarioLogeado!: Usuario | null;

  constructor(private usuarioService: UsuarioService) {}

  public setUsuarioLogeado(usuario: Usuario): void {
    this.usuarioLogeado = usuario;
  }

  public getUsuarioLogeado(): Usuario {
    return this.usuarioLogeado!;
  }

  public cerrarSesion(): void {
    this.usuarioLogeado = null;
  }

  public actualizarUsuario(): Usuario {
    this.usuarioService.getUsuario(this.usuarioLogeado!.nombreUsuario).subscribe({
      next: (usuario: Usuario) => {
        this.usuarioLogeado = usuario;
      }
    })
    return this.usuarioLogeado!;
  }
}
