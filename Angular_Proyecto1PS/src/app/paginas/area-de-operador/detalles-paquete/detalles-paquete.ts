import { Component, OnInit, signal } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Paquete } from '../../../modelos/paquete/paquete';
import { PaqueteService } from '../../../restapi/paquete-service/paquete-service';
import { ServicioPaqueteService } from '../../../restapi/servicio-paquete-service/servicio-paquete-service';
import { ServicioPaquete } from '../../../modelos/servicio-paquete/servicio-paquete';

@Component({
  selector: 'app-detalles-paquete',
  imports: [Head, RegresoMenu],
  templateUrl: './detalles-paquete.html',
  styleUrl: './detalles-paquete.css',
})
export class DetallesPaquete implements OnInit {

  titulo: string = 'Detalles de Paquetes';
  linkAnterior: string  = '/area-operador';

  paquetesGuardados = signal<Paquete[]>([]);
  paqueteElegido = signal<Paquete | null>(null);
  serviciosPaquete = signal<ServicioPaquete[]>([]);
  mayorA80: boolean = false;

  constructor(private paqueteService: PaqueteService, private servicioService: ServicioPaqueteService) {}

  ngOnInit(): void {
    this.paqueteService.getAllPaquetes().subscribe({
      next: (paquetes: Paquete[]) => {
        this.paquetesGuardados.set(paquetes);
      }
    })
  }

  setPaquete(paquete: Paquete): void {
    this.paqueteElegido.set(paquete);
    this.esMayorA80();
    this.servicioService.geServiciosPorIdPaquete(paquete.idPaquete!).subscribe({
      next: (servicios: ServicioPaquete[]) => {
        this.serviciosPaquete.set(servicios);
      }
    })
  }

  esMayorA80(): void {
    this.mayorA80 = this.paqueteElegido()?.reservacionesHechas! > this.paqueteElegido()?.capacidad! * .80
  }

}
