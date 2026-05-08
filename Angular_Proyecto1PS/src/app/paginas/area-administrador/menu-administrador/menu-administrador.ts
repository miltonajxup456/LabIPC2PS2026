import { Component } from '@angular/core';
import { GuardadoUsuario } from '../../../login/guardado-usuario/guardado-usuario';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-menu-administrador',
  imports: [RouterLink],
  templateUrl: './menu-administrador.html',
  styles: ``,
})
export class MenuAdministrador {

  constructor(private guardadoUsuario: GuardadoUsuario) {}

  cerrarSesion() {
    this.guardadoUsuario.cerrarSesion();
  }

}
