import { Component, OnInit, signal } from '@angular/core';
import { UsuarioService } from '../../../restApi/usuario-service/usuario-service';
import { Usuario } from '../../../models/usuario/usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { Habilidad } from '../../../models/habilidad/habilidad';

@Component({
  selector: 'app-ver-usuarios',
  imports: [Header, MenuSecundario],
  templateUrl: './ver-usuarios.html',
  styles: `.hab {background: rgb(45, 231, 61);} .des{background: red;}`,
})
export class VerUsuarios implements OnInit {

  titulo: string = 'Ver a los Usarios Registrados';
  linkAnterior: string = '/acceso-administrador';
  usuarios = signal<Usuario[]>([]);
  usuarioSeleccionado = signal<Usuario | null>(null);
  habilidadesFreelancer = signal<Habilidad[]>([]);

  constructor(private usuarioService: UsuarioService) {}
  
  ngOnInit(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (usuarios: Usuario[]) => this.usuarios.set(usuarios)
    });
  }

  elegirUsuario(usuario: Usuario): void {
    this.usuarioSeleccionado.set(usuario);
    if (usuario.rol === 3) {
      this.usuarioService.getHabsCompletasFree(usuario.nombreUsuario).subscribe({
        next: (habilidades: Habilidad[]) => this.habilidadesFreelancer.set(habilidades)
      })
    } else {
      this.habilidadesFreelancer.set([]);
    }
  }

  cambiar(): void {
    if (this.usuarioSeleccionado()?.nombreUsuario === 'Admin1') return
    const temporal: Usuario = this.usuarioSeleccionado()!;
    this.usuarioSeleccionado.update(usu => usu ? {...usu, baneo: !temporal.baneo}:usu)
    this.usuarios.update(lista => lista.map(usuario => usuario.nombreUsuario === temporal.nombreUsuario ? {...usuario, baneo: !temporal.baneo}: usuario));
    this.usuarioService.baneoCuenta(this.usuarioSeleccionado()!, this.usuarioSeleccionado()?.nombreUsuario!).subscribe();
  }

}
