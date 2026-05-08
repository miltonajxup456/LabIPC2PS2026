import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { PropuestaHabilidadService } from '../../../restApi/propuesta-service/propuesta-habilidad-service';
import { Propuesta } from '../../../models/propuesta/propuesta';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HabilidadService } from '../../../restApi/habilidad-service/habilidad-service';

@Component({
  selector: 'app-ver-propuestas-habilidad',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './ver-propuestas-habilidad.html',
  styles: ``,
})
export class VerPropuestasHabilidad implements OnInit {

  titulo: string = 'Propuestas de Freelancers de Habilidad';
  linkAnterior: string = '/acceso-administrador';
  propuestas = signal<Propuesta[]>([]);
  propuestaElegida = signal<Propuesta | null>(null);
  formHabilidad!: FormGroup;

  constructor(private propuestaService: PropuestaHabilidadService, private habilidadService: HabilidadService) {}

  ngOnInit(): void {
    this.formHabilidad = new FormGroup({
      nombreHabilidad: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required)
    })
    this.llamarPropuestas();
  }

  elegirPropuesta(propuesta: Propuesta): void {
    this.propuestaElegida.set(propuesta);
    this.formHabilidad.patchValue({
      nombreHabilidad: propuesta.nombre,
      descripcion: propuesta.descripcion
    })
  }

  aprobarHabilidad(): void {
    const miForm = this.formHabilidad.value;
    if (miForm.nombreHabilidad.length > 20) {
      alert('El nombre es demasiado largo (max 20 caracteres)');
      return;
    }
    if (miForm.descripcion.length > 200) {
      alert('La descripcion es demasiado larga (max 200 caracteres)');
      return;
    }
    this.habilidadService.agregarHabilidad(this.formHabilidad.value, this.propuestaElegida()?.idPropuestaHabilidad!).subscribe({
      next: () => {
        alert('Se ha aceptado la nueva habilidad');
        this.propuestaElegida.set(null);
        this.llamarPropuestas();
      }
    })
  }

  private llamarPropuestas(): void {
    this.propuestaService.getPropuestas().subscribe({
      next: (propuestas: Propuesta[]) => {
        this.propuestas.set([]);
        this.propuestas.set(propuestas);
      }
    })
  }

}
