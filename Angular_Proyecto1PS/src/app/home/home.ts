import { Component } from '@angular/core';
import { Head } from "../head/head";
import { Login } from "../login/login";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-home',
  imports: [Head, Login, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

  titulo: string = 'Proyecto 1 IPC2 PS';

}
