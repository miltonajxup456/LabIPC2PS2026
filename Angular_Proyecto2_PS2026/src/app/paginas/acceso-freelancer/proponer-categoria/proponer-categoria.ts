import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PropuestaCategoriaService } from '../../../restApi/propuesta-service/propuesta-categoria-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-proponer-categoria',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './proponer-categoria.html',
  styles: ``,
})
export class ProponerCategoria {

  titulo: string = 'Espacio para proponer nuevas Categorias';
  linkAnterior: string = '/acceso-freelancer';
  formCategoria!: FormGroup;

  constructor(private propuestaService: PropuestaCategoriaService) {}
  
  ngOnInit(): void {
    this.formCategoria = new FormGroup({
      nombre: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required)
    });
  }
  
  mandarPropuesta(): void {
    const miForm = this.formCategoria.value;
    if (miForm.nombre.length > 20) {
      alert('El nombre es demasiado largo (max 20 caracteres)');
      return;
    }
    if (miForm.descripcion.length > 200) {
      alert('La descripcion es demasiado larga (max 200 caracteres)');
      return;
    }
    this.propuestaService.agregarPropuesta(this.formCategoria.value).subscribe({
      next: () => {
        alert('La propuesta se ha enviado');
        this.formCategoria.reset();
      }
    })
  }
}
