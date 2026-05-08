import { Component, OnInit, signal } from '@angular/core';
import { Usuario } from '../../../models/usuario/usuario';
import { UsuarioService } from '../../../restApi/usuario-service/usuario-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-inhabilitar-cuentas',
  imports: [Header, MenuSecundario],
  templateUrl: './inhabilitar-cuentas.html',
  styles: `.hab {background: greenyellow;} .des{background: red;}`,
})
export class InhabilitarCuentas implements OnInit {

  titulo: string = 'Area para habilitar/desabilitar cuentas';
  linkAnterior: string = '/acceso-administrador';
  usuarios = signal<Usuario[]>([]);

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (usuarios: Usuario[]) => this.usuarios.set(usuarios)
    })
  }

  cambiar(usuario: Usuario): void {
    usuario.baneo = !usuario.baneo;
    this.usuarioService.baneoCuenta(usuario, usuario.nombreUsuario).subscribe();
  }

}
