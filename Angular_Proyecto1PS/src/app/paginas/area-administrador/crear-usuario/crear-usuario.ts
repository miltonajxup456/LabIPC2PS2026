import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../../restapi/usuario-service/usuario-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-crear-usuario',
  imports: [Head, RegresoMenu, ReactiveFormsModule],
  templateUrl: './crear-usuario.html',
  styleUrl: './crear-usuario.css',
})
export class CrearUsuario implements OnInit {

  titulo: string = 'Agrega nuevos Usuarios';
  linkAnterior: string = '/area-administrador';

  formUsuario!: FormGroup;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.formUsuario = new FormGroup({
      nombre: new FormControl<string>('', Validators.required),
      password_user: new FormControl<string>('', Validators.required),
      rol: new FormControl<number | null>(null, Validators.required)
    })
  }

  setRol(rolElegido: number): void {
    this.formUsuario.patchValue({
      rol: rolElegido
    })
  }

  agregarUsuario(): void {
    if (this.formUsuario.value.password_user.length < 6 ) {
      alert('La contraseña debe ser de por lo menos 6 caracteres');
      return;
    }

    this.usuarioService.crearUsuario(this.formUsuario.value).subscribe({
      next: () => {
        alert('Se agrego con exito el usuario'),
        this.formUsuario.reset()
      }, 
      error: () => {alert('No se pueden tener dos usuarios con el mismo nombre')}
    })
  }

}
