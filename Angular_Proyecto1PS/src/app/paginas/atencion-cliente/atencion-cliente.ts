import { Component } from '@angular/core';
import { Head } from "../../head/head";
import { MenuAtencionCliente } from "./menu-atencion-cliente/menu-atencion-cliente";

@Component({
  selector: 'app-atencion-cliente',
  imports: [Head, MenuAtencionCliente],
  templateUrl: './atencion-cliente.html',
  styleUrl: './atencion-cliente.css',
})
export class AtencionCliente {

  titulo: string = 'Atencion al Cliente'

}
