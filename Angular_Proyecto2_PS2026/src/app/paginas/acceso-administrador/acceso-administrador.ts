import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../componentes/header/header";
import { Usuario } from '../../models/usuario/usuario';
import { GuardadoUsuario } from '../login/guardado-usuario/guardado-usuario';
import { UsuarioService } from '../../restApi/usuario-service/usuario-service';
import { RouterLink } from "@angular/router";
import { HistorialComisionService } from '../../restApi/comision-service/historial-comision-service';

@Component({
  selector: 'app-acceso-administrador',
  imports: [Header, RouterLink],
  templateUrl: './acceso-administrador.html',
  styles: ``,
})
export class AccesoAdministrador implements OnInit {

  titulo: string = 'Menú de Adminsitrador';
  usuarioLogeado = signal<Usuario | null>(null);
  comisionGanada = signal<number>(0);

  constructor(
    private guardado: GuardadoUsuario, 
    private usuarioService: UsuarioService, 
    private comisionService: HistorialComisionService) {}

  ngOnInit(): void {
    // this.usuarioService.getUsuario('Admin1').subscribe({
    //   next: (log: Usuario) => this.guardado.setUsuarioLogeado(log)
    // });
    this.usuarioLogeado.set(this.guardado.getUsuarioLogeado());
    this.comisionService.getComisionGanada().subscribe({
      next: (comision: number) => this.comisionGanada.set(comision)
    })
  }
  
  cerrarSesion(): void {
    this.guardado.cerrarSesion();
  }

}
