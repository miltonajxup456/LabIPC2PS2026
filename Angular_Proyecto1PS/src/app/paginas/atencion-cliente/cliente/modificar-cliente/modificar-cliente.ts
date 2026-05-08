import { Component, signal } from '@angular/core';
import { Cliente } from '../../../../modelos/cliente/cliente';
import { ClienteService } from '../../../../restapi/cliente-service/cliente-service';
import { Head } from "../../../../head/head";
import { RegresoMenu } from "../../../regreso-menu/regreso-menu";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-modificar-cliente',
  imports: [Head, RegresoMenu, ReactiveFormsModule],
  templateUrl: './modificar-cliente.html',
  styleUrl: './modificar-cliente.css',
})
export class ModificarCliente {

  titulo: string = 'Modificar los datos de los Clientes';
  linkAnterior: string = '/atencion-cliente';

  allClientes = signal<Cliente[]>([]);

  clienteSeleccionado: Cliente | null = null;

  formularioCliente!: FormGroup;

  constructor(private clienteService: ClienteService) {}

  ngOnInit() {
    this.clienteService.getClientes().subscribe({
      next: (clientes: Cliente[]) => {
        this.allClientes.set(clientes);
      }
    })

    this.formularioCliente = new FormGroup({
      dpi: new FormControl('', Validators.required),
      nombre: new FormControl('', Validators.required),
      fechaNac: new FormControl('', Validators.required),
      telefono: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      nacionalidad: new FormControl('', Validators.required)
    })
    
  }

  setCliente(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
    this.formularioCliente.patchValue({
      dpi: this.clienteSeleccionado.dpi,
      nombre: this.clienteSeleccionado.nombre,
      fechaNac: this.clienteSeleccionado.fechaNac,
      telefono: this.clienteSeleccionado.telefono,
      email: this.clienteSeleccionado.email,
      nacionalidad: this.clienteSeleccionado.nacionalidad
    })
  }

  guardarCambios() {
    if (!this.clienteSeleccionado) return;
    this.clienteService.actualizarCliente(this.clienteSeleccionado.dpi, this.formularioCliente.value).subscribe({
      next: () => {
        alert('El cliente se ha actualizado')
      },
      error: () => {
        alert('No es fué posible actualizar al Cliente')
      }
    })
  }

  eliminarCliente() {

    if(!this.clienteSeleccionado) return;

    this.clienteService.eliminarCliente(this.clienteSeleccionado.dpi).subscribe({
      next: () => {
        this.allClientes.update(lista => lista.filter(cont => cont.dpi != this.clienteSeleccionado!.dpi))
        this.clienteSeleccionado = null;
        alert('El cliente se elimino con exito: ')
      },
      error: () => {
        alert('No fué posible eliminar el Cliente')
      }
    })
  }


}
