import { Component, Input, signal } from '@angular/core';
import { RouterLink } from "@angular/router";
import { Usuario } from '../../../models/usuario/usuario';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';

@Component({
  selector: 'app-completar-datos',
  imports: [RouterLink],
  templateUrl: './completar-datos.html',
  styles: `
  .contPrincipal {flex-direction: column; align-items: center;} 
  p {color: red; font-size: 23px;} 
  .btnCuadrado {width: 250px;}
  `,
})
export class CompletarDatos {

  @Input({required: true}) accion: string = '';
  // @Input({required: true}) 
  linkModificacion: string = '/editar-informacion-cliente';
  usuarioLogeado = signal<Usuario | null>(null);

  constructor(private guardadoUsuario: GuardadoUsuario) {
    this.usuarioLogeado.set(guardadoUsuario.getUsuarioLogeado())
  }

  puedePublicar(): boolean {
    if (this.usuarioLogeado()?.rol === 2) {
      const descripcion: string = this.usuarioLogeado()?.descripcionEmpresa || '';
      const sector: string = this.usuarioLogeado()?.industriaPerteneciente || '';
      const sitioWeb: string = this.usuarioLogeado()?.sitioWeb || '';
      
      return !!descripcion.trim() && !!sector.trim() && !!sitioWeb.trim();
    } else if (this.usuarioLogeado()?.rol === 3) {
      const biografia: string = this.usuarioLogeado()?.biografia || '';
      const tarifaRefencial: number = this.usuarioLogeado()?.tarifaReferencial || 0;
      const nivelDeExperiencia: number = this.usuarioLogeado()?.nivelExperiencia || 0;

      return !!biografia.trim() && tarifaRefencial > 0 && nivelDeExperiencia > 0;
    }
    return false;
  }

}
