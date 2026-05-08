import { Component, OnInit, signal } from '@angular/core';
import { ProyectoService } from '../../../restApi/proyecto-service/proyecto-service';
import { EntregaService } from '../../../restApi/entrega-service/entrega-service';
import { Proyecto } from '../../../models/proyecto/proyecto';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Usuario } from '../../../models/usuario/usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { Entrega } from '../../../models/entrega/entrega';
import { Cancelacion } from '../../../models/cancelacion/cancelacion';
import { CancelacionService } from '../../../restApi/cancelacion-service/cancelacion-service';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Rechazo } from '../../../models/rechazo/rechazo';
import { RechazoService } from '../../../restApi/rechazo-service/rechazo-service';
import { EntregaServiceLink } from '../../../restApi/entrega-servive-link/entrega-service-link';
import { CalificacionService } from '../../../restApi/calificacion-service/calificacion-service';
import { CalificacionFreelancer } from '../../../models/calificacion-freelancer/calificacion-freelancer';

@Component({
  selector: 'app-ver-entregas',
  imports: [Header, MenuSecundario, FormsModule, ReactiveFormsModule],
  templateUrl: './ver-entregas.html',
  styles: `.botones {display: flex; flex-direction: row;} .boton{width: 45px; height: 45px;}`
})
export class VerEntregas implements OnInit {

  titulo: string = 'Ver Entregas';
  linkAnterior: string = '/acceso-cliente';

  usuarioLogeado!: Usuario;
  proyectos = signal<Proyecto[]>([]);
  entregasProyecto = signal<Entrega[]>([]);
  proyectoElegido = signal<Proyecto | null>(null);
  calificacion = signal<CalificacionFreelancer | null>(null);
  rechazar: boolean = false;
  motivoRechazo: string = '';
  cancelar: boolean = false;
  motivoCancelacion: string = '';
  comentarioCalificacion: string = '';
  formCalificacion!: FormGroup;

  constructor(
    private guardado: GuardadoUsuario,
    private proyectoService: ProyectoService, 
    private entregaService: EntregaServiceLink,
    private rechazoService: RechazoService,
    private cancelacionService: CancelacionService,
    private calificacionService: CalificacionService) {}

  ngOnInit(): void {
    this.usuarioLogeado = this.guardado.getUsuarioLogeado();
    this.proyectoService.getProyectosCliente(this.usuarioLogeado.nombreUsuario).subscribe({
      next: (proyectos: Proyecto[]) => {
        this.proyectos.set(proyectos);
      }
    });
    this.formCalificacion = new FormGroup({
      calificacion: new FormControl<number>(1, Validators.required),
      comentario: new FormControl<string>(''),
      proyecto: new FormControl<number>(0, Validators.required),
      freelancer: new FormControl<string>('', Validators.required)
    })
  }

  elegirProyecto(proyecto: Proyecto): void {
    this.proyectoElegido.set(proyecto);
    this.calificacion.set(null);
    this.entregaService.getEntregasProyecto(proyecto.idProyecto!).subscribe({
      next: (entregas: Entrega[]) => this.entregasProyecto.set(entregas)
    })
    if (proyecto.estado === 5) {
      this.formCalificacion.patchValue({
        proyecto: proyecto.idProyecto,
        freelancer: proyecto.freelancer
      })
      this.calificacionService.getCalificacionProyecto(proyecto.idProyecto!).subscribe({
        next: (calificacion: CalificacionFreelancer) => {
          if (calificacion !== null) {
            this.calificacion.set(calificacion);
            this.formCalificacion.patchValue({
              calificacion: calificacion.calificacion,
              comentario: calificacion.comentario
            });
          }
        }
      })
    }
  }

  // descargarEntrega(idEntrega: number): void {
  //   this.entregaService.getEntrega(idEntrega).subscribe({
  //     next: (entrega: Blob) => {
  //       const url = window.URL.createObjectURL(entrega);
  //       const a = document.createElement('a');

  //       a.href = url;
  //       a.download = 'entrega_proyecto.zip';
  //       a.click();

  //       window.URL.revokeObjectURL(url);
  //     },
  //     error: () => alert('El archivo que se intenta descargar ya no existe')
  //   })
  // }

  rechazarProyecto(): void {
    this.rechazar = true;
    this.cancelar = false;
  }

  mandarRechazo(): void {
    if (!this.motivoRechazo) {
      alert('Es necesario una descripcion de por que es que se rechaza');
      return;
    }

    const rechazo: Rechazo = {
      respuesta: this.motivoRechazo,
      proyecto: this.proyectoElegido()?.idProyecto!,
      freelancer: this.proyectoElegido()?.freelancer!
    }

    this.rechazoService.agregarRechazo(rechazo).subscribe({
      next: () => {
        alert('Se ha mandado la respuesta')
        this.motivoRechazo = ''
        this.rechazar = false;
        this.cancelar = false;
      }
    })
    
    this.proyectoService.rechazarProyecto(this.proyectoElegido()?.idProyecto!, this.proyectoElegido()!).subscribe({
      next: (actual: Proyecto) => this.actualizarProyectos(actual)
    });
  }

  aprobarEntrega(): void {
    this.proyectoService.aprobarProyecto(this.proyectoElegido()?.idProyecto!, this.proyectoElegido()!).subscribe({
      next: (actual: Proyecto) => this.actualizarProyectos(actual)
    })
  }

  cancelarProyecto(): void {
    this.rechazar = false;
    this.cancelar = true;
  }

  mandarCancelacion(): void {
    if (!this.motivoCancelacion) {
      alert('Es necesario agregar una descripcion de porque se Cancela');
      return;
    }
    const cancelacion: Cancelacion = {
      motivo: this.motivoCancelacion,
      proyecto: this.proyectoElegido()?.idProyecto!,
      freelancer: this.proyectoElegido()?.freelancer!
    }
    this.cancelacionService.agregarCancelacion(cancelacion).subscribe({
      next: () => {
        alert('La cancelacion se ha mandado');
        this.rechazar = false;
        this.cancelar = false;
      }
    })
    this.proyectoService.cancelarProyecto(this.proyectoElegido()?.idProyecto!, this.proyectoElegido()!).subscribe({
      next: (actual: Proyecto) => this.actualizarProyectos(actual)
    })
  }

  cambiarCalificacion(puntuacion: number): void {
    this.formCalificacion.patchValue({
      calificacion: puntuacion
    })
  }

  mandarCalificarAFreelancer(): void {
    if (!this.calificacion()) {
      this.calificacionService.agregarCalificacion(this.formCalificacion.value).subscribe({
        next: () => {
          alert('Se ha mandado la calificacion')
        }
      })
    } else {
      this.calificacionService.modificarCalificacion(this.formCalificacion.value, this.calificacion()?.idCalificacion!).subscribe({
        next: () => alert('Se ha actualizado la calificacion')
      })
    }
  }

  private actualizarProyectos(actual: Proyecto): void {
    this.proyectoElegido.set(actual);
    this.proyectos.update(todo => todo.filter(todo => todo.idProyecto != actual.idProyecto));
    this.proyectos.update(todo => [...todo, actual])
    this.guardado.actualizarUsuario();
  }

}
