import { Component, OnInit, signal } from '@angular/core';
import { ProveedorService } from '../../../restapi/proveedor-service/proveedor-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { Paquete } from '../../../modelos/paquete/paquete';
import { Proveedor } from '../../../modelos/proveedor/proveedor';
import { PaqueteService } from '../../../restapi/paquete-service/paquete-service';
import { Destino } from '../../../modelos/destino/destino';
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { ServicioPaquete } from '../../../modelos/servicio-paquete/servicio-paquete';
import { ServicioPaqueteService } from '../../../restapi/servicio-paquete-service/servicio-paquete-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-definir-servicios',
  imports: [Head, RegresoMenu, FormsModule],
  templateUrl: './definir-servicios.html',
  styleUrl: './definir-servicios.css',
})
export class DefinirServicios implements OnInit {

  titulo: string = 'Define los servicios disponibles para los paquetes';
  linkAnterior: string = '/area-operador';

  paquetesDisponibles = signal<Paquete[]>([]);
  paqueteElegido = signal<Paquete | null>(null);
  proveedoresPais = signal<Proveedor[]>([]);
  proveedorElegido = signal<Proveedor | null>(null);
  serviciosPaquete = signal<ServicioPaquete[]>([]);
  servicioElegido = signal<ServicioPaquete | null>(null);
  definirServicio: boolean = false;
  costoServicio = signal<number>(0);

  constructor(
    private paqueteService: PaqueteService,
    private proveedorService: ProveedorService, 
    private servicioService: ServicioPaqueteService) {}

  ngOnInit(): void {
    this.paqueteService.getAllPaquetes().subscribe({
      next: (paquetes: Paquete[]) => {
        this.paquetesDisponibles.set(paquetes);
      }
    });
  }

  setPaquete(paquete: Paquete) {
    this.completarProcesoServicio();
    this.paqueteElegido.set(paquete);
    this.proveedorService.getProveedoresPais(paquete.idDestino!).subscribe({
      next: (proveedores: Proveedor[]) => {
        this.proveedoresPais.set(proveedores);
        this.actualizarServicios();
      }
    })
  }

  actualizarServicios() {
    this.servicioService.geServiciosPorIdPaquete(this.paqueteElegido()?.idPaquete!).subscribe({
      next: (servicios: ServicioPaquete[]) => {
        this.serviciosPaquete.set(servicios);
      }
    })
  }

  estaActivo(proveedor: Proveedor): boolean {
    for (let i = 0; i < this.serviciosPaquete().length; i++) {
      if (proveedor.idProveedor === this.serviciosPaquete()[i].proveedor) {
        return true;
      }
    }
    return false;
  }

  setServicioActivo(proveedor: Proveedor) {
    for (let i = 0; i < this.serviciosPaquete().length; i++) {
      if (proveedor.idProveedor === this.serviciosPaquete()[i].proveedor) {
        this.costoServicio.set(this.serviciosPaquete()[i].costo);
        this.servicioElegido.set(this.serviciosPaquete()[i]);
        this.definirServicio = false;
        this.setProveedor(proveedor);
        return;
      }
    }
  }

  activarCreacionServicio(proveedor: Proveedor) {
    this.servicioElegido.set(null);
    this.costoServicio.set(0);
    this.definirServicio = true;
    this.setProveedor(proveedor);
  }

  private setProveedor(proveedor: Proveedor) {
    this.proveedorElegido.set(proveedor);
  }

  actualizarServicioPaquete() {
    this.servicioElegido.update(cont => cont ? {...cont, costo: this.costoServicio()}: cont);
    this.servicioService.actualizarServicioPaquete(this.servicioElegido()?.idServicioPaquete!, this.servicioElegido()!).subscribe({
      next: () => {
        this.completarProcesoServicio();
      }
    })
  }

  agregarServicio() {
    const nuevoServicio: ServicioPaquete = {
      costo: this.costoServicio(),
      paquete: this.paqueteElegido()?.idPaquete!,
      proveedor: this.proveedorElegido()?.idProveedor!
    }
    this.servicioService.agregarServicioAPaquete(nuevoServicio).subscribe({
      next: () => {
        this.completarProcesoServicio();
      }
    })
  }

  private completarProcesoServicio() {
    this.servicioElegido.set(null);
    this.paqueteElegido.set(null);
    this.proveedorElegido.set(null);
    this.definirServicio = false;
    this.costoServicio.set(0);
  }

}
