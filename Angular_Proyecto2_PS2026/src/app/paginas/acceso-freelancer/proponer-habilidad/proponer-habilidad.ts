import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { PropuestaHabilidadService } from '../../../restApi/propuesta-service/propuesta-habilidad-service';

@Component({
  selector: 'app-proponer-habilidad',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './proponer-habilidad.html',
  styles: ``,
})
export class ProponerHabilidad implements OnInit {

  titulo: string = 'Area para proponer una nueva Habilidad';
  linkAnterior: string = '/acceso-freelancer';
  formCategoria!: FormGroup;

  constructor(private propuestaService: PropuestaHabilidadService) {}
  
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
