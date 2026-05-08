import { Component, OnInit, signal } from '@angular/core';
import { UsuarioService } from '../../../restapi/usuario-service/usuario-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Usuario } from '../../../modelos/usuario/usuario';

@Component({
  selector: 'app-modificar-usuario',
  imports: [Head, RegresoMenu],
  templateUrl: './modificar-usuario.html',
  styleUrl: './modificar-usuario.css',
})
export class ModificarUsuario implements OnInit {

  titulo: string = 'Modificar Usuarios';
  linkAnterior: string = '/area-administrador';

  usuariosGuardados = signal<Usuario[]>([]);
  usuarioElegido = signal<Usuario | null>(null);

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.buscarUsuarios();
  }

  setUsuario(usuario: Usuario): void {
    this.usuarioElegido.set(usuario);
  }

  eliminarUsuario(): void {
    this.usuarioService.eliminarUsuario(this.usuarioElegido()?.idUsuario!).subscribe({
      next: () => {
        alert('El usuario se elimino con exito'),
        this.buscarUsuarios()
        this.usuarioElegido.set(null);
      }
    })
  }

  buscarUsuarios(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (usuarios: Usuario[]) => {
        this.usuariosGuardados.set(usuarios);
      }
    })
  }

}
