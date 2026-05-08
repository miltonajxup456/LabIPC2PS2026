import { Component, OnInit, signal } from '@angular/core';
import { Head } from "../../../../head/head";
import { RegresoMenu } from "../../../regreso-menu/regreso-menu";
import { Cliente } from '../../../../modelos/cliente/cliente';
import { ClienteService } from '../../../../restapi/cliente-service/cliente-service';

@Component({
  selector: 'app-datos-cliente',
  imports: [Head, RegresoMenu],
  templateUrl: './datos-cliente.html',
  styleUrl: './datos-cliente.css',
})
export class DatosCliente implements OnInit {

  titulo: string = 'Datos de los Clientes';
  linkAnterior: string = '/atencion-cliente';

  allClientes = signal<Cliente[]>([]);

  clienteSeleccionado!: Cliente;

  constructor(private clienteService: ClienteService) {}

  ngOnInit() {
    this.clienteService.getClientes().subscribe({
      next: (clientes: Cliente[]) => {
        this.allClientes.set(clientes);
      }
    })
  }

  setCliente(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
  }

}
