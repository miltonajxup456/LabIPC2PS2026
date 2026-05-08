import { Component, OnInit, signal } from '@angular/core';
import { Usuario } from '../../../models/usuario/usuario';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProyectoService } from '../../../restApi/proyecto-service/proyecto-service';
import { Proyecto } from '../../../models/proyecto/proyecto';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { EntregaService } from '../../../restApi/entrega-service/entrega-service';
import { CancelacionService } from '../../../restApi/cancelacion-service/cancelacion-service';
import { Cancelacion } from '../../../models/cancelacion/cancelacion';
import { EntregaServiceLink } from '../../../restApi/entrega-servive-link/entrega-service-link';
import { RechazoService } from '../../../restApi/rechazo-service/rechazo-service';
import { Rechazo } from '../../../models/rechazo/rechazo';

@Component({
  selector: 'app-entregar-proyecto',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './entregar-proyecto.html',
  styles: `p {color: red;} .rechazos {width: 300px; display: flex; flex-direction: column; overflow: auto;} .plan {margin: 5px; border: 2px solid black;}`
  // .celda {border: 3px solid rgb(4, 0, 255); color: rgb(4, 0, 255);}
})
export class EntregarProyecto implements OnInit {

  titulo: string = 'Area para entregas de Proyectos';
  linkAnterior: string = '/acceso-freelancer';

  usuarioLogeado!: Usuario;
  proyectos = signal<Proyecto[]>([]);
  rechazos = signal<Rechazo[]>([]);
  proyectoElegido = signal<Proyecto | null>(null);
  cancelacion = signal<Cancelacion | null>(null);
  formEntrega!: FormGroup;
  archivoElegido: File | null = null;
  notificaciones: number[] = [];

  constructor(
    private guardado: GuardadoUsuario, 
    private proyectoService: ProyectoService,
    private entregaService: EntregaServiceLink,
    private rechazoService: RechazoService,
    private cancelacionService: CancelacionService) {}

  ngOnInit(): void {
    this.usuarioLogeado = this.guardado.getUsuarioLogeado();

    this.formEntrega = new FormGroup({
      descripcion: new FormControl<string>('', Validators.required),
      link: new FormControl<string>('', Validators.required),
      proyecto: new FormControl<number>(0, Validators.required),
      freelancer: new FormControl<string>('', Validators.required)
    });

    this.llamarProyectos();
  }

  elegirProyecto(proyecto: Proyecto) {
    if (proyecto.estado != 6) {
      this.proyectoElegido.set(proyecto);
      this.cancelacion.set(null);
      this.formEntrega.patchValue({
        proyecto: proyecto.idProyecto,
        freelancer: proyecto.freelancer
      });
      this.buscarCancelaciones(proyecto.idProyecto!);
    } else {
      this.proyectoElegido.set(null);
      this.formEntrega.reset();
      this.cancelacionService.getCancelacion(proyecto.idProyecto!, proyecto.freelancer!).subscribe({
        next: (cancelacion: Cancelacion) => this.cancelacion.set(cancelacion)
      })
    }
  }

  subirEntrega(event: any): void {
    this.archivoElegido = event.target.files[0];
  }

  mandarEntrega(): void {
    this.entregaService.agregarEntrega(this.formEntrega.value).subscribe({
      next: () => {
        alert('Se ha mandado la entrega');
        this.formEntrega.reset();
        this.proyectoElegido.set(null);
        this.llamarProyectos();
      }
    })
    // if (!this.archivoElegido) {
    //   alert('Aun no se ha cargado un archivo');
    //   return;
    // }

    // const formData = new FormData();

    // formData.append('entrega', this.archivoElegido!);

    // formData.append('registro-entrega', new Blob(
    //   [JSON.stringify(this.formEntrega.value)],
    //   { type: 'application/json' }
    // ));

    // this.entregaService.subirEntrega(formData).subscribe({
    //   next: () => {
    //     alert('La entrega se subió con exito');
    //     this.formEntrega.reset();
    //     this.archivoElegido = null;
    //     this.proyectoElegido.set(null);
    //   }
    // })
  }

  private llamarProyectos(): void {
    this.proyectoService.getProyectosFreelancer(this.usuarioLogeado.nombreUsuario).subscribe({
      next: (proyectos: Proyecto[]) => {
        this.proyectos.set(proyectos);
      }
    });
  }

  private buscarCancelaciones(idProyecto: number): void {
    this.rechazoService.getRechazos(idProyecto).subscribe({
      next: (rechazos: Rechazo[]) => this.rechazos.set(rechazos)
    })
  }

  private buscarCancelacionesProyecto(): void {
    for (let i = 0; i < this.proyectos().length; i++) {
      this.rechazoService.getRechazos(this.proyectos()[i].idProyecto!).subscribe({
        next: (rechazos: Rechazo[]) => {
          if (rechazos.length > 0) {

          }
        }
      })
    }
  }

}
