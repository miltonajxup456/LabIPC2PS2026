import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HabilidadService } from '../../../restApi/habilidad-service/habilidad-service';
import { Habilidad } from '../../../models/habilidad/habilidad';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-modificar-habilidad',
  imports: [ReactiveFormsModule, Header, MenuSecundario],
  templateUrl: './modificar-habilidad.html',
  styles: ``,
})
export class ModificarHabilidad implements OnInit {

  titulo: string = 'Área de Modificacion de las Habilidades';
  linkAnterior: string = '/acceso-administrador';
  formHabilidad!: FormGroup;
  habilidades = signal<Habilidad[]>([]);
  habilidadSeleccionada = signal<Habilidad | null>(null);

  constructor(private habilidadService: HabilidadService) {}

  ngOnInit(): void {
    this.habilidadService.getHabilidades().subscribe({
      next: (habilidades: Habilidad[]) => this.habilidades.set(habilidades)
    });
    this.formHabilidad = new FormGroup({
      nombreHabilidad: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required)
    });
  }

  elegirHabilidad(habilidad: Habilidad): void {
    this.habilidadSeleccionada.set(habilidad);
    this.formHabilidad.patchValue({
      nombreHabilidad: habilidad.nombreHabilidad,
      descripcion: habilidad.descripcion
    });
  }

  actualizarHabilidad(): void {
    const miForm = this.formHabilidad.value;
    const idHabilidad = this.habilidadSeleccionada()?.idHabilidad!;
    this.habilidadService.editarHabilidad(idHabilidad, miForm).subscribe({
      next: () => {
        alert('La habilidad se ha actualizado con exito');
        this.habilidades.update(lista => lista.map(habilidad => habilidad.idHabilidad === idHabilidad 
          ? {...habilidad, nombreHabilidad: miForm.nombreHabilidad, descripcion: miForm.descirpcion}: habilidad));
        this.habilidadSeleccionada.set(null);
        this.formHabilidad.reset();
      }
    });
  }

}
