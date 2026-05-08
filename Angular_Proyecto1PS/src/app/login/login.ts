import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { RestUrl } from '../restapi/rest-url/rest-url';
import { Router } from '@angular/router';
import { GuardadoUsuario } from './guardado-usuario/guardado-usuario';
import { Usuario } from '../modelos/usuario/usuario';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  logVisible: boolean = false;

  constructor (
    private httpClient: HttpClient, 
    private restUrl: RestUrl,
    private router: Router, 
    private guardadoUsuario: GuardadoUsuario) {}


  mostrarLog() {
    this.logVisible = !this.logVisible;
  }

  loginForm = new FormGroup({
    nombre: new FormControl<string>(''),
    password_user: new FormControl<string>('')
  });
  
  logearse() {
    
    this.httpClient.post<Usuario>(`${this.restUrl.getApiURL()}login`, this.loginForm.value).subscribe({
      next: 
      (usuario: Usuario) => {
        console.log(usuario)
        if (usuario.rol == 1) {
          this.router.navigate(['/atencion-cliente'])
        } else if (usuario.rol == 2) {
          this.router.navigate(['/area-operador'])
        } else if (usuario.rol == 3) {
          this.router.navigate(['/area-administrador'])
        }
        this.guardadoUsuario.setUsuario(usuario)
      },

      error: err => {
        alert('No se pudo acceder'),
        console.log('No se pudo acceder')
      }
    })
  };

}
