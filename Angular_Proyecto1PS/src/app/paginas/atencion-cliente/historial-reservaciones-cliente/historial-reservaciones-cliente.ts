import { Component, OnInit, signal } from '@angular/core';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { ClienteService } from '../../../restapi/cliente-service/cliente-service';
import { Cliente } from '../../../modelos/cliente/cliente';
import { Reservacion } from '../../../modelos/reservacion/reservacion';

@Component({
  selector: 'app-historial-reservaciones-cliente',
  imports: [Head, RegresoMenu],
  templateUrl: './historial-reservaciones-cliente.html',
  styleUrl: './historial-reservaciones-cliente.css',
})
export class HistorialReservacionesCliente implements OnInit {

  titulo: string = 'Historial de Reservaciones por Cliente';
  linkAnterior: string = '/atencion-cliente';

  todosLosClientes = signal<Cliente[]>([]);
  clienteElegido = signal<Cliente | null>(null);
  historialCliente = signal<Reservacion[]>([]);

  constructor(private clienteService: ClienteService, private reservacionService: ReservacionService) {}

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe({
      next: (clientes: Cliente[]) => {
        this.todosLosClientes.set(clientes);
      }
    });
  }

  setCliente(cliente: Cliente) {
    this.clienteElegido.set(cliente);
    this.reservacionService.getHistorialReservacionCliente(cliente.dpi).subscribe({
      next: (historial: Reservacion[]) => {
        this.historialCliente.set(historial);
      }
    })
  }

}
