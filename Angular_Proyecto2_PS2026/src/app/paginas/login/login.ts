import { Component, OnInit } from '@angular/core';
import { Header } from "../../componentes/header/header";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../restApi/login-service/login-service';
import { Usuario } from '../../models/usuario/usuario';
import { Router, RouterLink } from '@angular/router';
import { GuardadoUsuario } from './guardado-usuario/guardado-usuario';
import { Generico } from '../../models/extras/generico/generico';
import { UsuarioService } from '../../restApi/usuario-service/usuario-service';

@Component({
  selector: 'app-login',
  imports: [Header, ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styles: '',
})
export class Login implements OnInit {

  titulo: string = 'Bienvenido al Proyecto 2 de IPC2';

  formLogin!: FormGroup;

  constructor(
    private loginService: LoginService, 
    private router: Router, 
    private guardadoUsuario: GuardadoUsuario,
    private usuarioService: UsuarioService){}

  ngOnInit(): void {
    this.usuarioService.crearAdministrador().subscribe();

    this.formLogin = new FormGroup({
      nombreUsuario: new FormControl<String | null>(null, Validators.required),
      passwordUser: new FormControl<String | null>(null, Validators.required)
    })
  }

  ingresarAlSistema(): void {
    this.loginService.ingresar(this.formLogin.value).subscribe({
      next: (resp: Generico<Usuario>) => {
        const usuario: Usuario = resp.data;
        this.guardadoUsuario.setUsuarioLogeado(usuario);
        const redirigido:boolean = this.revisionUsuario(usuario);

        if (redirigido) {
          return;
        }

        if (usuario.rol === 1) {
          this.router.navigate(['/acceso-administrador'])
        } else if (usuario.rol === 2) {
          this.router.navigate(['/acceso-cliente']);
        } else if (usuario.rol === 3) {
          this.router.navigate(['/acceso-freelancer']);
        }
      },
      error: (error) => {
        const mensaje: string = error.error.mensaje;
        alert(mensaje);
      }
    })
  }

  registro(): void {
    this.router.navigate(['/registro-usuario']);
  }

  private revisionUsuario(usuario: Usuario): boolean {
    if (usuario.rol === 2) {
      const descripcion: string = usuario.descripcionEmpresa || '';
      const sector: string = usuario.industriaPerteneciente || '';
      const sitioWeb: string = usuario.sitioWeb || '';
      
      if (!descripcion.trim() || !sector.trim() || !sitioWeb.trim()) {
        this.completarDatos();
        return true;
      }
    } else if (usuario.rol === 3) {
      const biografia: string = usuario.biografia || '';
      const tarifaRefencial: number = usuario.tarifaReferencial || 0;
      const nivelDeExperiencia: number = usuario.nivelExperiencia || 0;

      if (!biografia.trim() || tarifaRefencial <= 0 || nivelDeExperiencia <= 0) {
        this.completarDatos();
        return true;
      }
    }
    return false;
  }

  private completarDatos() {
    this.router.navigate(['/editar-informacion-cliente']);
  }

}
