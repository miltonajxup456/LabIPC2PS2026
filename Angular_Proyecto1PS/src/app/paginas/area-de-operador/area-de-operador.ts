import { Component } from '@angular/core';
import { Head } from "../../head/head";
import { MenuOperador } from "./menu-operador/menu-operador";

@Component({
  selector: 'app-area-de-operador',
  imports: [Head, MenuOperador],
  templateUrl: './area-de-operador.html',
  styleUrl: './area-de-operador.css',
})
export class AreaDeOperador {

  titulo: string = 'Area de Operador';

}
