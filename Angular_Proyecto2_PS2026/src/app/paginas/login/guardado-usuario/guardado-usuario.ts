import { Injectable } from "@angular/core";
import { Usuario } from "../../../models/usuario/usuario";
import { UsuarioService } from "../../../restApi/usuario-service/usuario-service";
import { LoginService } from "../../../restApi/login-service/login-service";

@Injectable({
  providedIn: 'root'
})

export class GuardadoUsuario {

  usuarioLogeado: Usuario | null = null;

  constructor(private usuarioService: UsuarioService, private loginService: LoginService) {}

  public setUsuarioLogeado(usuario: Usuario): void {
    this.usuarioLogeado = usuario;
  }

  public getUsuarioLogeado(): Usuario {
    if (!this.usuarioLogeado) {
      this.loginService.reIngresar().subscribe({
        next: (usuario: Usuario) => this.usuarioLogeado = usuario
      })
    }
    return this.usuarioLogeado!;
  }

  public cerrarSesion(): void {
    this.loginService.cerrarSesion().subscribe();
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
