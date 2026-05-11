import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Header } from "../../componentes/header/header";
import { UsuarioService } from '../../restApi/usuario-service/usuario-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro-usuario',
  imports: [Header, ReactiveFormsModule],
  templateUrl: './registro-usuario.html',
  styles: `.espacio {height: 50px} .espacioFinal {height: 200px} .btnRedondo {margin-bottom: 20px}
  .eleccionUsuario {display: flex; gap: 50px; margin-bottom: 15px;}
  `
})
export class RegistroUsuario implements OnInit {

  titulo: string = 'Registro para nuevos usuarios';

  formRegistro!: FormGroup;

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  ngOnInit(): void {
    this.formRegistro = new FormGroup({
      nombreUsuario: new FormControl<string>('Milton2', Validators.required),
      nombre: new FormControl<string>('Milton', Validators.required),
      passwordUser: new FormControl<string>('1234', Validators.required),
      correoElectronico: new FormControl<string>('mil@gmail.com', Validators.required),
      telefono: new FormControl<string>('12345678', Validators.required),
      direccion: new FormControl<string>('Guatemala', Validators.required),
      cui: new FormControl<string>('87654321', Validators.required),
      fechaNac: new FormControl<Date | null>(null, Validators.required), 
      rol: new FormControl<number>(2, Validators.required)
    })
  }

  setRol(idRol: number): void {
    this.formRegistro.patchValue({
      rol: idRol
    });
  }

  guardarUsuario(): void {
    const miForm = this.formRegistro.value;
    if (miForm.passwordUser.length < 4) {
      alert('La contraseña debe ser de por lo menos 4 elementos');
      return;
    }
    if (!miForm.rol) {
      alert('Aun no se ha elegido el tipo de Usuario');
      return;
    }

    this.usuarioService.crearUsuario(miForm).subscribe({
      next: ()  => {
        alert('El usuario ha sido creado con exito')
        this.formRegistro.reset();
      },
      error: () => {
        alert('Ocurrio un error al intentar crear el usuario');
      }
    })
  }

  volverLogin(): void {
    this.router.navigate(['/login'])
  }

  vaciar(): void {
    this.formRegistro.reset();
  }

}
