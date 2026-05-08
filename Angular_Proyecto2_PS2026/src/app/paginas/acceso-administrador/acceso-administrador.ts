import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../componentes/header/header";
import { Usuario } from '../../models/usuario/usuario';
import { GuardadoUsuario } from '../login/guardado-usuario/guardado-usuario';
import { UsuarioService } from '../../restApi/usuario-service/usuario-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-acceso-administrador',
  imports: [Header, RouterLink],
  templateUrl: './acceso-administrador.html',
  styles: ``,
})
export class AccesoAdministrador implements OnInit {

  titulo: string = 'Menú de Adminsitrador';
  usuarioLogeado = signal<Usuario | null>(null);

  constructor(private guardado: GuardadoUsuario, private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    // this.usuarioService.getUsuario('Admin1').subscribe({
    //   next: (log: Usuario) => this.guardado.setUsuarioLogeado(log)
    // })
    this.usuarioLogeado.set(this.guardado.getUsuarioLogeado());
  }

  cerrarSesion(): void {
    this.guardado.cerrarSesion();
  }

}
