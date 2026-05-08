import { Component, signal } from '@angular/core';
import { PropuestaProyecto } from '../../../models/propuesta-proyecto/propuesta-proyecto';
import { PropuestaProyectoService } from '../../../restApi/propuesta-proyecto-service/propuesta-proyecto-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ver-propuestas-enviadas',
  imports: [Header, MenuSecundario, FormsModule],
  templateUrl: './ver-propuestas-enviadas.html',
  styles: ``,
})
export class VerPropuestasEnviadas {

  titulo: string = 'Propuestas enviadas por el Usuario';
  linkAnterior: string = '/acceso-freelancer';
  propuestas = signal<PropuestaProyecto[]>([]);
  fechaInicial!: string;
  fechaFinal!: string;

  constructor(private propuestaService: PropuestaProyectoService, private guardado: GuardadoUsuario) {}

  llamarProyectos(): void {
    const fechaI = new Date(this.fechaInicial);
    const fechaF = new Date(this.fechaFinal);

    if (fechaI > fechaF) {
      alert('No se puede colocar una fecha inicial que sea posterior a la final');
      return;
    }
    this.propuestaService.getPropuestasEnviadas(this.fechaInicial, this.fechaFinal, this.guardado.getUsuarioLogeado().nombreUsuario).subscribe({
      next: (propuestas: PropuestaProyecto[]) => this.propuestas.set(propuestas)
    })
  }

}
