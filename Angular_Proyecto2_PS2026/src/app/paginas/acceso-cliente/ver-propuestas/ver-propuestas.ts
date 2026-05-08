import { Component, OnInit, signal } from '@angular/core';
import { PropuestaProyectoService } from '../../../restApi/propuesta-proyecto-service/propuesta-proyecto-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { ProyectoService } from '../../../restApi/proyecto-service/proyecto-service';
import { Proyecto } from '../../../models/proyecto/proyecto';
import { PropuestaProyecto } from '../../../models/propuesta-proyecto/propuesta-proyecto';
import { Usuario } from '../../../models/usuario/usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-ver-propuestas',
  imports: [Header, MenuSecundario],
  templateUrl: './ver-propuestas.html',
  styles: `p {color: red;}`
})
export class VerPropuestas implements OnInit {

  titulo: string = 'Ver Propuestas de Proyectos';
  linkAnterior: string = '/acceso-cliente';

  cliente = signal<Usuario | null>(null);
  proyectos = signal<Proyecto[]>([]);
  proyectoElegido = signal<Proyecto | null>(null);
  propuestas = signal<PropuestaProyecto[]>([]);
  propuestaElegida = signal<PropuestaProyecto | null>(null);

  constructor(
    private guardadoUsuario: GuardadoUsuario,
    private proyectoService: ProyectoService,
    private propuestaService: PropuestaProyectoService){}

  ngOnInit(): void {
    this.cliente.set(this.guardadoUsuario.getUsuarioLogeado());

    this.llamarProyectos();
  }

  elegirProyecto(proyecto: Proyecto): void {
    this.proyectoElegido.set(proyecto);
    this.llamarPropuestas();
    this.propuestaElegida.set(null);
  }

  elegirPropuesta(propuesta: PropuestaProyecto): void {
    this.propuestaElegida.set(propuesta);
  }

  aceptarPropuesta(): void {
    const saldoActual = this.cliente()?.saldo!;
    const montoProyecto = this.propuestaElegida()?.montoOfertado!;
    if (montoProyecto > saldoActual) {
      alert('No se puede aceptar la propuesta porque no se cuenta con el saldo suficiente');
      return;
    }
    const aceptado: Proyecto = {...this.proyectoElegido()!, freelancer: this.propuestaElegida()?.freelancer, estado: 3};
    this.proyectoService.aceptarPropuesta(this.proyectoElegido()?.idProyecto!, aceptado).subscribe({
      next: (actual: Proyecto) => {
        alert('Se ha aceptado la propuesta');
        this.llamarProyectos();
        // this.proyectos.update(lista => lista.filter(pro => pro.idProyecto !== this.proyectoElegido()?.idProyecto));
        // this.proyectos.update(lista => [...lista, actual]);
        this.proyectoElegido.set(actual);
        this.llamarPropuestas();
        this.propuestaElegida.update(cont => cont ? {...cont, estado: 2, tipoEstado: 'ACEPTADO'}:cont);
        this.cliente.set(this.guardadoUsuario.actualizarUsuario());
      }
    })
  }

  rechazarPropuesta(): void {
    this.propuestaService.rechazarPropuesta(this.propuestaElegida()?.idPropuesta!, this.propuestaElegida()!).subscribe({
      next: () => {
        this.propuestaElegida.update(cont => cont ? {...cont, estado: 3, tipoEstado: 'RECHAZADO'}:cont);
        this.llamarPropuestas()
      }
    });
  }

  private llamarProyectos(): void {
    this.proyectoService.getProyectosCliente(this.cliente()?.nombreUsuario!).subscribe({
      next: (proyectos: Proyecto[]) => {
        this.proyectos.set(proyectos);
      }
    });
  }

  private llamarPropuestas(): void {
    this.propuestaService.getPropuestasProyecto(this.proyectoElegido()?.idProyecto!).subscribe({
      next: (propuestas: PropuestaProyecto[]) => {
        this.propuestas.set(propuestas);
      }
    });
  }

}
