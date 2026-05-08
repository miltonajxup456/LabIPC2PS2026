import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../componentes/header/header";
import { RouterLink } from "@angular/router";
import { Usuario } from '../../models/usuario/usuario';
import { UsuarioService } from '../../restApi/usuario-service/usuario-service';
import { GuardadoUsuario } from '../login/guardado-usuario/guardado-usuario';
import { CompletarDatos } from "../componentes-extra/completar-datos/completar-datos";

@Component({
  selector: 'app-acceso-freelancer',
  imports: [Header, RouterLink, CompletarDatos],
  templateUrl: './acceso-freelancer.html',
  styles: ``,
})
export class AccesoFreelancer implements OnInit {

  titulo: string = 'Acceso de Freelancer';
  usuarioLogeado = signal<Usuario | null>(null);

  constructor(private usuarioService: UsuarioService, private guardadoUsuario: GuardadoUsuario) {}

  ngOnInit(): void {
    // this.usuarioService.getUsuario('Milton3').subscribe({
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
