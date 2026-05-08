import { Component } from '@angular/core';
import { Head } from "../../head/head";
import { MenuAdministrador } from "./menu-administrador/menu-administrador";

@Component({
  selector: 'app-area-administrador',
  imports: [Head, MenuAdministrador],
  template: `
    <app-head [titulo]="titulo"></app-head>
    <div class="container">
        <p>Espacio para las opciones del Administrador</p>
        <app-menu-administrador></app-menu-administrador>
    </div>
  `,
  styles: ``,
})
export class AreaAdministrador {

  titulo: string = 'Area de Administrador';

}
