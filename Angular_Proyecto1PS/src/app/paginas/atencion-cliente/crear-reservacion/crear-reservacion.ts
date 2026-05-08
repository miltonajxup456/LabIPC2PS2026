import { Component, OnInit, signal } from '@angular/core';
import { ClienteService } from '../../../restapi/cliente-service/cliente-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { GuardadoUsuario } from '../../../login/guardado-usuario/guardado-usuario';
import { Destino } from '../../../modelos/destino/destino';
import { Paquete } from '../../../modelos/paquete/paquete';
import { PaqueteService } from '../../../restapi/paquete-service/paquete-service';
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { Cliente } from '../../../modelos/cliente/cliente';
import { FormsModule } from '@angular/forms';
import { Reservacion } from '../../../modelos/reservacion/reservacion';
import { ReservacionService } from '../../../restapi/reservacion-service/reservacion-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-crear-reservacion',
  imports: [Head, RegresoMenu, FormsModule, RouterLink],
  templateUrl: './crear-reservacion.html',
  styleUrl: './crear-reservacion.css',
})
export class CrearReservacion implements OnInit{

  titulo: string = 'Crear Reservacion';
  linkAnterior: string = '/atencion-cliente';

  fechaReservacion: string = '';
  destinoElegido: number = 0;
  pasajeros = signal<string[]>([]);
  dpiBuscado: string = '';
  costoReservacion: number = 0;
  mayorA80: boolean = false;
  
  destinosDisponibles = signal<Destino[]>([]);
  paquetesDisponibles = signal<Paquete[]>([]);
  paqueteElegido = signal<Paquete | null>(null);
  clientesGuardados = signal<Cliente[]>([]);

  constructor(
    private guardadoUsuario: GuardadoUsuario, 
    private destinoService: DestinoService,
    private paqueteService: PaqueteService,
    private clienteService: ClienteService, 
    private reservacionService: ReservacionService) {}

  ngOnInit(): void {
    this.destinoService.getAllDestinos().subscribe({
      next: (destinos: Destino[]) => {
        this.destinosDisponibles.set(destinos);
      }
    });

    this.clienteService.getClientes().subscribe({
      next: (pasajeros: Cliente[]) => {
        this.clientesGuardados.set(pasajeros);
      }
    })
  }

  setDestino(destino: Destino) {
    this.paqueteElegido.set(null);
    this.destinoElegido = destino.idDestino!;
    this.paqueteService.getPaqueteDestino(destino.idDestino!).subscribe({
      next: (paquetes: Paquete[]) => {
        this.paquetesDisponibles.set(paquetes);
      }
    })
  }

  setPaquete(paquete: Paquete) {
    this.paqueteElegido.set(paquete);
    this.costoReservacion = this.paqueteElegido()?.precio! * this.pasajeros().length;
    this.esMayorAOchenta();
  }

  estaAgregado(pasajero: Cliente): boolean {
    for (let i = 0; i < this.pasajeros().length; i++) {
      if (pasajero.dpi === this.pasajeros()[i]) {
        return true;
      }
    }
    return false;
  }

  adjuntarPasajero(pasajero: Cliente) {
    if (this.estaAgregado(pasajero)) {
      this.eliminarPasajero(pasajero);
    } else {
      this.agregarPasajero(pasajero);
    }
    this.costoReservacion = this.paqueteElegido()?.precio! * this.pasajeros().length;
  }

  private agregarPasajero(pasajero: Cliente) {
    this.pasajeros.update(pasajeros => [...pasajeros, pasajero.dpi]);
  }

  private eliminarPasajero(pasajero: Cliente) {
    this.pasajeros.update(clientes => clientes.filter(todo => todo !== pasajero.dpi));
  }

  sePuedeGuardar(): boolean {
    if (this.fechaReservacion && this.paqueteElegido() && this.pasajeros().length > 0) {
      return false;
    }
    return true;
  }

  guardarReservacion() {
    const numeroPasajeros = this.pasajeros().length;
    const nuevaReservacion: Reservacion = {
      fechaViaje: this.fechaReservacion,
      cantPasajeros: numeroPasajeros,
      costo: numeroPasajeros * this.paqueteElegido()?.precio!,
      agenteDeRegistro: this.guardadoUsuario.getUsuario()?.idUsuario!,
      paquete: this.paqueteElegido()?.idPaquete!,
      nombrePaquete: this.paqueteElegido()?.nombre!,
      dpiPasajeros: this.pasajeros()
    }

    this.reservacionService.crearReservacion(nuevaReservacion).subscribe({
      next: () => {
        alert('La reservacion se creó con exito');
        this.fechaReservacion = '';
        this.destinoElegido = 0;
        this.pasajeros.set([]);
        this.paquetesDisponibles.set([]);
        this.paqueteElegido.set(null);
      }, 
      error: () => alert('Ocurrio un error al intentar guardar la reservacion')
    })
  }

  esMayorAOchenta() {
    this.mayorA80 = this.paqueteElegido()?.reservacionesHechas! > this.paqueteElegido()?.capacidad! * .8;
  }

}
