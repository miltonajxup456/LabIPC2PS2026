import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../componentes/header/header";
import { Proyecto } from '../../../models/proyecto/proyecto';
import { ProyectoService } from '../../../restApi/proyecto-service/proyecto-service';
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PropuestaProyectoService } from '../../../restApi/propuesta-proyecto-service/propuesta-proyecto-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Usuario } from '../../../models/usuario/usuario';
import { PropuestaProyecto } from '../../../models/propuesta-proyecto/propuesta-proyecto';
import { CompletarDatos } from "../../componentes-extra/completar-datos/completar-datos";

@Component({
  selector: 'app-agregar-propuesta',
  imports: [Header, MenuSecundario, ReactiveFormsModule, CompletarDatos],
  templateUrl: './agregar-propuesta.html',
  styles: `p {color: red;} .btnRedondo {margin-bottom: 25px;}`
})
export class AgregarPropuesta implements OnInit {

  titulo: string = 'Agregar una Propuesta';
  linkAnterior: string = '/acceso-freelancer';

  usuarioLogeado = signal<Usuario | null>(null);
  proyectos = signal<Proyecto[]>([]);
  proyectoElegido = signal<Proyecto | null>(null);
  propuestaElegida = signal<PropuestaProyecto | null>(null);
  formPropuesta!: FormGroup;

  constructor(
    private guardado: GuardadoUsuario,
    private proyectoService: ProyectoService, 
    private propuestaService: PropuestaProyectoService) {}

  ngOnInit(): void {
    this.usuarioLogeado.set(this.guardado.getUsuarioLogeado());
    this.proyectoService.getProyectos().subscribe({
      next: (proyectos: Proyecto[]) => {
        this.proyectos.set(proyectos);
      }
    })

    this.formPropuesta = new FormGroup ({
      presentacion: new FormControl<string>('', Validators.required),
      montoOfertado: new FormControl<number | null>(null, Validators.required),
      plazoEntrega: new FormControl<number | null>(null, Validators.required),
      freelancer: new FormControl<string>(''),
      proyecto: new FormControl<number>(0)
    })
  }

  elegirProyecto(proyecto: Proyecto): void {
    this.proyectoElegido.set(proyecto);
    this.formPropuesta.reset();
    this.formPropuesta.patchValue({
      proyecto: proyecto.idProyecto
    })
    this.propuestaService.getPropuestaFreelancer(proyecto.idProyecto!, this.usuarioLogeado()?.nombreUsuario!).subscribe({
      next: (propuesta: PropuestaProyecto) => {
        if (propuesta != null) {
          this.propuestaElegida.set(propuesta)
          this.formPropuesta.patchValue({
            presentacion: propuesta.presentacion,
            montoOfertado: propuesta.montoOfertado,
            plazoEntrega: propuesta.plazoEntrega,
          })
        } else {
          this.propuestaElegida.set(null);
        }
      }
    })
  }

  subirPropuesta(): void {
    if (this.propuestaElegida()) {
      this.actualizarPropuesta();
    } else {
      this.subirPropuestaBaseDatos();
    }
  }

  subirPropuestaBaseDatos(): void {
    this.formPropuesta.patchValue({
      freelancer: this.usuarioLogeado()?.nombreUsuario
    })
    const miForm = this.formPropuesta.value;
    if (!this.proyectoElegido()) {
      alert('Aun no se ha seleccionado un Proyecto');
      return;
    }
    if (miForm.montoOfertado <= 0 || miForm.plazoEntrega <= 0) {
      alert('Los valores numericos deben ser mayores a 0');
      return;
    }
    if (miForm.montoOfertado > this.proyectoElegido()?.presupuesto!) {
      alert('No se puede proponer una cifra mayor a proporcianada por el cliente');
      return;
    }
    this.propuestaService.agregarPropuesta(miForm).subscribe({
      next: () => {
        alert('La propuesta se ha pubicado');
      }
    })
  }

  actualizarPropuesta(): void {
    const miForm = this.formPropuesta.value;
    if (miForm.montoOfertado <= 0 || miForm.plazoEntrega <= 0) {
      alert('Los valores numericos deben ser mayores a 0');
      return;
    }
    if (miForm.montoOfertado > this.proyectoElegido()?.presupuesto!) {
      alert('No se puede proponer una cifra mayor a proporcianada por el cliente');
      return;
    }
    this.propuestaService.editarPropuesta(this.propuestaElegida()?.idPropuesta!, this.proyectoElegido()?.idProyecto!, miForm).subscribe({
      next: () => {
        alert('La propuesta se ha actualizado');
      }
    })
  }

  eliminarPropuesta(): void {
    this.propuestaService.eliminarPropuesta(this.propuestaElegida()?.idPropuesta!).subscribe({
      next: () => {
        alert('Se ha retirado la propuesta del proyecto');
        this.formPropuesta.reset();
        this.propuestaElegida.set(null)
      }
    })
  }

  puedePublicar(): boolean {
    const biografia: string = this.usuarioLogeado()?.biografia || '';
    const tarifaRefencial: number = this.usuarioLogeado()?.tarifaReferencial || 0;
    const nivelDeExperiencia: number = this.usuarioLogeado()?.nivelExperiencia || 0;

    return !!biografia.trim() && tarifaRefencial > 0 && nivelDeExperiencia > 0;
  }

}
