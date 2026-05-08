import { Component } from '@angular/core';
import { Head } from "../../../head/head";
import { RouterLink } from "@angular/router";
import { GuardadoUsuario } from '../../../login/guardado-usuario/guardado-usuario';

@Component({
  selector: 'app-menu-operador',
  imports: [RouterLink],
  templateUrl: './menu-operador.html',
  styleUrl: './menu-operador.css',
})
export class MenuOperador {

  constructor(private guardadoUsuario: GuardadoUsuario) {}
  
  cerraSesion() {
    this.guardadoUsuario.cerrarSesion();
  }

}
