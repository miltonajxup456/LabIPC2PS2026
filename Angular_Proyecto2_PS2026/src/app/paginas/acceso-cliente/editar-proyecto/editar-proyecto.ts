import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProyectoService } from '../../../restApi/proyecto-service/proyecto-service';
import { Proyecto } from '../../../models/proyecto/proyecto';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { Categoria } from '../../../models/categoria/categoria';
import { Usuario } from '../../../models/usuario/usuario';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { CategoriaService } from '../../../restApi/categoria-service/categoria-service';
import { ComisionService } from '../../../restApi/comision-service/comision-service';
import { Comision } from '../../../models/comision/comision';
import { Habilidad } from '../../../models/habilidad/habilidad';
import { HabilidadService } from '../../../restApi/habilidad-service/habilidad-service';

@Component({
  selector: 'app-editar-proyecto',
  imports: [Header, MenuSecundario, ReactiveFormsModule],
  templateUrl: './editar-proyecto.html',
  styles: `p {color: red;} .celda {margin-bottom: 20px; width: 350px;}`
})
export class EditarProyecto implements OnInit {

  titulo: string = 'Área para editar Proyectos';
  linkAnterior: string = '/acceso-cliente';

  formProyecto!: FormGroup;
  usuarioLogeado = signal<Usuario | null>(null);
  proyectosCliente = signal<Proyecto[]>([]);
  proyectoElegido = signal<Proyecto | null>(null);
  categorias = signal<Categoria[]>([]);
  habilidades = signal<Habilidad[]>([]);
  habilidadesProyecto = signal<number[]>([]);
  categoriaID: number = 0;
  comisionProyecto = signal<Comision | null>(null);

  constructor(
    private guardado: GuardadoUsuario,
    private proyectoService: ProyectoService, 
    private categoriaService: CategoriaService,
    private habilidadService: HabilidadService,
    private comisionService: ComisionService) {}

  ngOnInit(): void {
    this.usuarioLogeado.set(this.guardado.getUsuarioLogeado());

    this.formProyecto = new FormGroup({
      titulo: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required),
      presupuesto: new FormControl<number | null>(null, Validators.required),
      fechaLimite: new FormControl<Date | null>(null, Validators.required),
      categoria: new FormControl<number | null>(null), 
      habilidades: new FormControl<number[]>([])
    });

    this.proyectoService.getProyectosCliente(this.usuarioLogeado()?.nombreUsuario!).subscribe({
      next: (proyectos: Proyecto[]) => this.proyectosCliente.set(proyectos)
    });

    this.categoriaService.getTodasLasCategorias().subscribe({
      next: (categorias: Categoria[]) => this.categorias.set(categorias)
    });

    this.habilidadService.getHabilidades().subscribe({
      next: (habilidades: Habilidad[]) => this.habilidades.set(habilidades)
    })
  }

  elegirProyecto(proyecto: Proyecto): void {
    const fecha: Date = new Date(proyecto.fechaLimite);
    this.formProyecto.patchValue({
      titulo: proyecto.titulo,
      descripcion: proyecto.descripcion,
      presupuesto: proyecto.presupuesto,
      fechaLimite: proyecto.fechaLimite,
      categoria: proyecto.categoria
    });
    this.proyectoElegido.set(proyecto);
    this.categoriaID = proyecto.categoria;
    this.habilidadesProyecto.set(proyecto.habilidades!);
    this.comisionService.getComisionPorID(proyecto.comision!).subscribe({
      next: (comision: Comision) => this.comisionProyecto.set(comision)
    });
  }

  elegirCategoria(idCategoria: number): void {
    this.formProyecto.patchValue({
      categoria: idCategoria
    })
  }

  elegirHabilidad(idHabilidad: number): void {
    if (this.existeEnLista(idHabilidad)) {
      this.habilidadesProyecto.update(habs => habs.filter(id => id !== idHabilidad));
    } else {
      this.habilidadesProyecto.update(habs => [...habs, idHabilidad]);
    }
  }

  existeEnLista(idHabilidad: number): boolean {
    for (let i = 0; i < this.habilidadesProyecto().length; i++) {
      if (idHabilidad === this.habilidadesProyecto()[i]) {
        return true;
      }
    }
    return false;
  }

  guardarCambios(): void {
    this.formProyecto.patchValue({habilidades: this.habilidadesProyecto()})
    const miForm = this.formProyecto.value;
    const fechaProyecto = new Date(miForm.fechaLimite)
    const fechaActual = new Date();
    
    if (!this.proyectoElegido()) {
      alert('Aun no se ha escogido un Proyecto');
      return;
    }
    if (miForm.presupuesto <= 0) {
      alert('El presupuesto no puede ser menor o igual que cero');
      return;
    }
    if (fechaProyecto <= fechaActual) {
      alert('La fecha no puede ser anterior a la actual');
      return;
    }

    this.proyectoService.modificarProyecto(this.proyectoElegido()?.idProyecto!, miForm).subscribe({
      next: (actual: Proyecto) => {
        alert('El proyecto se ha guardado y publicado con exito');
        this.proyectosCliente.update(lista => lista.filter(pro => pro.idProyecto !== this.proyectoElegido()?.idProyecto));
        this.proyectosCliente.update(lista => [...lista, actual]);
        this.formProyecto.reset();
        this.proyectoElegido.set(null);
        this.categoriaID = 0;
        this.habilidadesProyecto.set([]);
      },
      error: () => alert('Ocurrio un error al momento de Guardar el Proyecto')
    });
  }

}
