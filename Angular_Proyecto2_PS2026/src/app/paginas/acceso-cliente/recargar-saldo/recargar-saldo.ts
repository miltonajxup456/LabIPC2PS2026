import { Component, OnInit, signal } from '@angular/core';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { FormsModule } from '@angular/forms';
import { RecargaService } from '../../../restApi/recarga-service/recarga-service';
import { Recarga } from '../../../models/recarga/recarga';
import { Usuario } from '../../../models/usuario/usuario';
import { UsuarioService } from '../../../restApi/usuario-service/usuario-service';

@Component({
  selector: 'app-recargar-saldo',
  imports: [Header, MenuSecundario, FormsModule],
  templateUrl: './recargar-saldo.html',
  styles: ``
})
export class RecargarSaldo implements OnInit {

  titulo: string = 'Area para Recargar Saldo';
  linkAnterior: string = '/acceso-cliente';
  saldoARecargar: number = 0;
  usuarioLogeado = signal<Usuario | null>(null);
  
  constructor(private guardado: GuardadoUsuario, private recargaService: RecargaService) {}

  ngOnInit(): void {
    this.usuarioLogeado.set(this.guardado.getUsuarioLogeado());
  }

  recargarSaldo(): void {
    const usuario: Usuario = this.guardado.getUsuarioLogeado();
    if (this.saldoARecargar <= 0) {
      alert('El saldo debe ser mayor a cero');
      return;
    }

    const recarga: Recarga = {
      monto: this.saldoARecargar,
      cliente: usuario.nombreUsuario
    }

    this.recargaService.agregarRecarga(recarga).subscribe({
      next: (actual: Usuario) => {
        alert('La recarga ha sido ingresad con exito');
        this.guardado.setUsuarioLogeado(actual);
        this.usuarioLogeado.set(actual);
        this.saldoARecargar = 0;
      }
    })
  }
}
