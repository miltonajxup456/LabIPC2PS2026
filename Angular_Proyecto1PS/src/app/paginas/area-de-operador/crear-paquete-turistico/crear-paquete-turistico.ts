import { Component, OnInit, signal } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { Destino } from '../../../modelos/destino/destino';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PaqueteService } from '../../../restapi/paquete-service/paquete-service';

@Component({
  selector: 'app-crear-paquete-turistico',
  imports: [Head, RegresoMenu, ReactiveFormsModule],
  templateUrl: './crear-paquete-turistico.html',
  styleUrl: './crear-paquete-turistico.css',
})
export class CrearPaqueteTuristico implements OnInit {

  titulo: string = 'Crear Paquete Turistico';
  linkAnterior: string = '/area-operador';

  todosDestinos = signal<Destino[]>([]);
  destinoElegido = signal<Destino | null>(null);

  formPaquete!: FormGroup;

  constructor(private destinoService: DestinoService, private paqueteService: PaqueteService) {}

  ngOnInit(): void {
    this.destinoService.getAllDestinos().subscribe({
      next: (destinos: Destino[]) => {
        this.todosDestinos.set(destinos);
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

  setDestino(destino: Destino) {
    this.destinoElegido.set(destino);
    this.formPaquete.patchValue({
      idDestino: destino.idDestino
    })
  }

  guardarPaquete() {
    const form = this.formPaquete.value;
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
    this.paqueteService.crearPaquete(form).subscribe({
      next: () => {
        alert('El paquete se ha guardado con Exito');
        this.formPaquete.reset();
        this.destinoElegido.set(null);
      },
      error: () => {
        alert('No se pudo crear el paquet porque no se puede repetir nombre');
      }
    })
    
  }

}
