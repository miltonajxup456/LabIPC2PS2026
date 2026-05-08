import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { GuardadoUsuario } from '../../../login/guardado-usuario/guardado-usuario';

@Component({
  selector: 'app-menu-atencion-cliente',
  imports: [RouterLink],
  templateUrl: './menu-atencion-cliente.html',
  styleUrl: './menu-atencion-cliente.css',
})
export class MenuAtencionCliente {
  
  constructor(private guardarUsuario: GuardadoUsuario) {}

  cerarSesion() {
    this.guardarUsuario.cerrarSesion();
  }

}
