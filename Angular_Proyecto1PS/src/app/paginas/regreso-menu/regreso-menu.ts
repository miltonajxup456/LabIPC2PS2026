import { Component, Input } from '@angular/core';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-regreso-menu',
  imports: [RouterLink],
  template: `
    <div class="container" >
      <div class="espacioMenuSuperior" >
          <button class="botonMenuSuperior" [routerLink]="linkAnterior" >Regresar</button>
          <button class="botonMenuSuperior" routerLink="/home" (click)="cerrarSesion()" >Cerrar Sesion</button>
      </div>
    </div>
  `,
  styles: ``,
})
export class RegresoMenu {

  @Input({required: true}) linkAnterior!:string;

  constructor(private guardadoUsuario: GuardadoUsuario) {}

  cerrarSesion() {
    this.guardadoUsuario.cerrarSesion();
  }

  getLink() {
    return this.linkAnterior;
  }

}
