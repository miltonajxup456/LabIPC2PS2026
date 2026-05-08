import { Component } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-reportes',
  imports: [Head, RegresoMenu, RouterLink],
  templateUrl: './reportes.html',
  styles: `.menuSecundario{margin: 30px 150px;}`
})
export class Reportes {

  titulo: string = 'Reportes';
  linkAnterior: string = '/area-administrador';

}
