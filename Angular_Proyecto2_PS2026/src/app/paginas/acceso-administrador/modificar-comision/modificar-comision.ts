import { Component, OnInit, signal } from '@angular/core';
import { ComisionService } from '../../../restApi/comision-service/comision-service';
import { Comision } from '../../../models/comision/comision';
import { FormsModule } from '@angular/forms';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-modificar-comision',
  imports: [FormsModule, Header, MenuSecundario],
  templateUrl: './modificar-comision.html',
  styles: ``,
})
export class ModificarComision implements OnInit {

  titulo: string = 'Area para modificar la comision para los proyectos';
  linkAnterior: string = '/acceso-administrador';
  ultimaComision = signal<Comision | null>(null);
  nuevaComision!: number | null;

  constructor(private comisionService: ComisionService) {}

  ngOnInit(): void {
    this.llamarComision();
  }

  actualizarComision(): void {
    if (!Number.isInteger(this.nuevaComision)) {
      alert('El numero tiene que ser un entero');
      return;
    }
    if (this.nuevaComision! < 0) {
      alert('La comision no puede ser menor a 0%');
      return;
    }
    if (this.nuevaComision! > 100) {
      alert('La comision no puede ser mayor al 100%');
      return;
    }
    const comision: Comision = {
      porcentaje: this.nuevaComision!
    }
    this.comisionService.agregarComision(comision).subscribe({
      next: () => {
        alert('Se ha actualizado la comision');
        this.nuevaComision = null;
        this.llamarComision();
      }
    })
  }

  llamarComision(): void {
    this.comisionService.getUltimaComision().subscribe({
      next: (comision: Comision) => this.ultimaComision.set(comision)
    })
  }

}
