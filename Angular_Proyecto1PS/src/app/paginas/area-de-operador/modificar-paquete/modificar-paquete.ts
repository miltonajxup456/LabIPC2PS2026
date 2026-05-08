import { Component, signal } from '@angular/core';
import { Destino } from '../../../modelos/destino/destino';
import { Proveedor } from '../../../modelos/proveedor/proveedor';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { PaqueteService } from '../../../restapi/paquete-service/paquete-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { ProveedorService } from '../../../restapi/proveedor-service/proveedor-service';
import { Paquete } from '../../../modelos/paquete/paquete';

@Component({
  selector: 'app-modificar-paquete',
  imports: [Head, RegresoMenu, ReactiveFormsModule],
  templateUrl: './modificar-paquete.html',
  styleUrl: './modificar-paquete.css',
})
export class ModificarPaquete {

  titulo: string = 'Modifica los Paquetes';
  linkAnterior: string = '/area-operador';

  todosPaquetes = signal<Paquete[]>([]);
  paqueteElegido = signal<Paquete | null>(null);
  todosDestinos = signal<Destino[]>([]);
  todosProveedores = signal<Proveedor[]>([]);
  proveedorElegido = signal<Proveedor | null>(null);

  formPaquete!: FormGroup;

  constructor(
    private destinoService: DestinoService, 
    private paqueteService: PaqueteService, 
    private proveedorService: ProveedorService
  ) {}

  ngOnInit(): void {
    this.paqueteService.getAllPaquetes().subscribe({
      next: (paquetes: Paquete[])  => {
        this.todosPaquetes.set(paquetes);
      }
    })

    this.destinoService.getAllDestinos().subscribe({
      next: (destinos: Destino[]) => {
        this.todosDestinos.set(destinos);
      }
    })

    this.proveedorService.getProveedores().subscribe({
      next: (proveedores: Proveedor[]) => {
        this.todosProveedores.set(proveedores);
      }
    })

    this.formPaquete = new FormGroup({
      nombre: new FormControl<string>('', Validators.required),
      duracionDias: new FormControl<number | null>(null, Validators.required),
      precio: new FormControl<number | null>(null, Validators.required),
      capacidad: new FormControl<number | null>(null, Validators.required),
      descripcion: new FormControl<string | null>(null, Validators.required),
      idDestino: new FormControl<number | null>(null, Validators.required)
    })
  }

  setPaquete(paquete: Paquete) {
    this.paqueteElegido.set(paquete);
    
    this.formPaquete.patchValue({
      nombre: paquete.nombre,
      duracionDias: paquete.duracionDias,
      precio: paquete.precio,
      capacidad: paquete.capacidad,
      descripcion: paquete.descripcion,
      idDestino: paquete.idDestino
    })
  }

  setDestino(destino: Destino) {
    if (!this.paqueteElegido()) {
      alert('Aun no se ha elegido un Paquete para modificar');
      return;
    }
    this.formPaquete.patchValue({
      idDestino: destino.idDestino
    })
  }

  guardarPaquete() {
    const form = this.formPaquete.value;
    if (!this.paqueteElegido()) {
      alert('Aun no se ha elegido un paquete');
      return;
    }
    if (form.nombre.length > 50) {
      alert('El nombre no puede ser mayor a 50 caracteres')
      return;
    }
    if (form.descripcion.length > 150) {
      alert('La descirpcion tiene como maximo permitido 150 caracteres');
      return;
    }
    if (form.duracionDias <= 0 || form.precio <= 0 || form.capacidad <= 0) {
      alert('Las cantidades deben ser mayores a cero');
      return;
    }
    this.paqueteService.actualizarPaquete(this.paqueteElegido()!.idPaquete!,form).subscribe({
      next: () => {
        alert('El paquete se ha actualizado con Exito');
        this.paqueteElegido.update(cont => cont ? {...cont, ...form}: cont);
        this.completarAcutalizacion();
        this.formPaquete.reset();
        this.paqueteElegido.set(null);
      },
      error: () => {
        alert('No se pudo actulizar el paquete');
      }
    })
  }

  habilitarPaquete(habilitado: boolean) {
    const actual: Paquete = {...this.paqueteElegido()!, estadoPaquete: habilitado};
    this.paqueteService.actualizarPaquete(actual.idPaquete!, actual).subscribe({
      next: () => {
        this.paqueteElegido.update(paquete => paquete ? {...paquete, estadoPaquete: habilitado}: paquete);
        this.completarAcutalizacion();
      }
    })
  }

  completarAcutalizacion() {
    this.todosPaquetes.update(todo => todo.filter(paquete => paquete.idPaquete !== this.paqueteElegido()!.idPaquete));
    this.todosPaquetes.update(todo => todo ? [...todo, this.paqueteElegido()!]: todo);
  }

  eliminarPaquete() {
    this.paqueteService.eliminarPaquete(this.paqueteElegido()!.idPaquete!).subscribe({
      next: () => {
        this.todosPaquetes.update(todo => todo.filter(paquete => paquete.idPaquete != this.paqueteElegido()?.idPaquete));
        this.paqueteElegido.set(null);
        this.formPaquete.reset();
      }
    })
  }

}
