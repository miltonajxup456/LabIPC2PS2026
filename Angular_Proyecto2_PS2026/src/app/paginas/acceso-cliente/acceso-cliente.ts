import { Component, signal } from '@angular/core';
import { Header } from "../../componentes/header/header";
import { RouterLink } from "@angular/router";
import { UsuarioService } from '../../restApi/usuario-service/usuario-service';
import { GuardadoUsuario } from '../login/guardado-usuario/guardado-usuario';
import { Usuario } from '../../models/usuario/usuario';
import { CompletarDatos } from "../componentes-extra/completar-datos/completar-datos";

@Component({
  selector: 'app-acceso-cliente',
  imports: [Header, RouterLink, CompletarDatos],
  templateUrl: './acceso-cliente.html',
  styles: ``
})
export class AccesoCliente {

  titulo: string = 'Opciones del Cliente';
  usuarioLogeado = signal<Usuario | null>(null);

  constructor(private usuarioService: UsuarioService, private guardadoUsuario: GuardadoUsuario) {}

  ngOnInit(): void {
    // this.usuarioService.getUsuario('Milton2').subscribe({
    //   next: (usuario: Usuario) => {
    //     this.guardadoUsuario.setUsuarioLogeado(usuario);
    //   }
    // })
    this.usuarioLogeado.set(this.guardadoUsuario.getUsuarioLogeado());
  }

  cerrarSesion(): void {
    this.guardadoUsuario.cerrarSesion();
  }

}
