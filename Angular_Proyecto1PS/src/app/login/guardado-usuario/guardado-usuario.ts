import { Injectable, signal } from "@angular/core";
import { Usuario } from "../../modelos/usuario/usuario";

@Injectable({
  providedIn: 'root'
})
export class GuardadoUsuario {
  usuarioPrueba: Usuario = {
    idUsuario: 3,
    nombre: 'Ajxup',
    rol: 1
  }
  usuario = signal<Usuario | null> (this.usuarioPrueba);

  setUsuario(usuarioActual: Usuario) {
    this.usuario.set(usuarioActual)
  }

  public getUsuario(): Usuario | null {
    return this.usuario();
  }

  cerrarSesion() {
    this.usuario.set(null);
  }
}
