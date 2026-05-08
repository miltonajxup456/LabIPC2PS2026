import { Component, OnInit, signal } from '@angular/core';
import { PropuestaCategoriaService } from '../../../restApi/propuesta-service/propuesta-categoria-service';
import { CategoriaService } from '../../../restApi/categoria-service/categoria-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Propuesta } from '../../../models/propuesta/propuesta';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ver-propuestas-categoria',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './ver-propuestas-categoria.html',
  styles: ``,
})
export class VerPropuestasCategoria implements OnInit {

  titulo: string = 'Propuestas de Freelancers para nuevas Categorias';
  linkAnterior: string = '/acceso-administrador';
  propuestas = signal<Propuesta[]>([]);
  propuestaElegida = signal<Propuesta | null>(null);
  formCategoria!: FormGroup;

  constructor(private propuestaService: PropuestaCategoriaService, private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.formCategoria = new FormGroup({
      nombreCategoria: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required)
    });
    this.llamarPropuestas();
  }

  elegirPropuesta(propuesta: Propuesta): void {
    this.propuestaElegida.set(propuesta);
    this.formCategoria.patchValue({
      nombreCategoria: propuesta.nombre,
      descripcion: propuesta.descripcion
    })
  }

  aprobarCategoria(): void {
    const miForm = this.formCategoria.value;
    if (miForm.nombreCategoria.length > 20) {
      alert('El nombre es demasiado largo (max 20 caracteres)');
      return;
    }
    if (miForm.descripcion.length > 200) {
      alert('La descripcion es demasiado larga (max 200 caracteres)');
      return;
    }
    this.categoriaService.agregarCategoria(miForm, this.propuestaElegida()?.idPropuestaCategoria!).subscribe({
      next: () => {
        alert('Se ha aceptado la Categoria');
        this.propuestaElegida.set(null);
        this.llamarPropuestas();
      }
    })
  }

  llamarPropuestas(): void {
    this.propuestaService.getPropuestas().subscribe({
      next: (propuestas: Propuesta[]) => {
        this.propuestas.set([]);
        this.propuestas.set(propuestas);
      }
    })
  }

}
