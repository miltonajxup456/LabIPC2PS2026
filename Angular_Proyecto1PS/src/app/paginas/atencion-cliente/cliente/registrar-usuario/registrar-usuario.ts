import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '../../../../restapi/cliente-service/cliente-service';
import { Head } from '../../../../head/head';
import { Cliente } from '../../../../modelos/cliente/cliente';
import { GuardadoUsuario } from '../../../../login/guardado-usuario/guardado-usuario';
import { RegresoMenu } from "../../../regreso-menu/regreso-menu";

@Component({
  selector: 'app-registrar-usuario',
  imports: [Head, ReactiveFormsModule, RegresoMenu],
  templateUrl: './registrar-usuario.html',
  styleUrl: './registrar-usuario.css',
})
export class RegistrarUsuario {

  titulo: string = 'Registra a un Usuario'
  paginaAnterior: string = '/atencion-cliente';

  constructor (private clienteService: ClienteService, private guardadoUsurio: GuardadoUsuario) {}

  formUsuario = new FormGroup ({
    dpi: new FormControl('Mil123', Validators.required),
    nombre: new FormControl('MIL', Validators.required),
    fechaNac: new FormControl('2026-01-23', Validators.required),
    telefono: new FormControl('33', Validators.required),
    email: new FormControl('e@d', [Validators.required, Validators.email]),
    nacionalidad: new FormControl('guate', Validators.required)
  });

  guardarUsuario() {

    const nuevoCliente: Cliente = {
      dpi: this.formUsuario.value.dpi!,
      nombre: this.formUsuario.value.nombre!,
      fechaNac: this.formUsuario.value.fechaNac!,
      telefono: this.formUsuario.value.telefono!,
      email: this.formUsuario.value.email!,
      nacionalidad: this.formUsuario.value.nacionalidad!
    }
    
    this.clienteService.crearCliente(nuevoCliente).subscribe({
      next: () => {
        alert('El nuevo cliente se guardo con exito')
        this.formUsuario.reset
        
      },
      error: () => {
        alert('Ocurrio un error al guardar al Cliente')
      }
    });
  }

  cerrarSesion() {
    this.guardadoUsurio.cerrarSesion();
  }

  limpiar(): void {
    this.formUsuario.reset();
  }
  
}
