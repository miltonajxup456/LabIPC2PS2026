import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '../../../restApi/usuario-service/usuario-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-crear-administrador',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './crear-administrador.html',
  styles: ``,
})
export class CrearAdministrador {

  titulo: string = 'Crea un nuevo usuario Administrador';
  linkAnterior: string = '/acceso-administrador';
  formRegistro!: FormGroup;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.formRegistro = new FormGroup({
      nombreUsuario: new FormControl<string>('Admin2', Validators.required),
      nombre: new FormControl<string>('MiltonA2', Validators.required),
      passwordUser: new FormControl<string>('1234', Validators.required),
      correoElectronico: new FormControl<string>('admin2@gmail.com', Validators.required),
      telefono: new FormControl<string>('12345678', Validators.required),
      direccion: new FormControl<string>('Guatemala', Validators.required),
      cui: new FormControl<string>('87654321', Validators.required),
      fechaNac: new FormControl<Date | null>(null, Validators.required), 
      rol: new FormControl<number>(1, Validators.required)
    })
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
      next: () => {
        alert('El usuario ha sido creado con exito')
        this.formRegistro.reset();
      },
        error: () => {
        alert('Ocurrio un error al intentar crear el usuario');
      }
    })
  }

  vaciar(): void {
    this.formRegistro.reset();
  }

}
