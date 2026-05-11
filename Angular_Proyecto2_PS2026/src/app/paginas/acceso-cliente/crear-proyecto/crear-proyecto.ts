import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../componentes/header/header";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProyectoService } from '../../../restApi/proyecto-service/proyecto-service';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { Categoria } from '../../../models/categoria/categoria';
import { CategoriaService } from '../../../restApi/categoria-service/categoria-service';
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { Usuario } from '../../../models/usuario/usuario';
import { CompletarDatos } from '../../componentes-extra/completar-datos/completar-datos';
import { ComisionService } from '../../../restApi/comision-service/comision-service';
import { Comision } from '../../../models/comision/comision';
import { HabilidadService } from '../../../restApi/habilidad-service/habilidad-service';
import { Habilidad } from '../../../models/habilidad/habilidad';
import { Cliente } from '../../../models/usuario/cliente';

@Component({
  selector: 'app-crear-proyecto',
  imports: [Header, ReactiveFormsModule, MenuSecundario, CompletarDatos],
  templateUrl: './crear-proyecto.html',
  styles: `.celda {margin: auto; font-size: var(--font-size); width: 500px;}`
})
export class CrearProyecto implements OnInit {

  titulo: string = 'Crear un Proyecto';
  linkAnterior: string = '/acceso-cliente';
  linkModificacion: string = '/editar-informacion-cliente';

  formProyecto!: FormGroup;
  categorias = signal<Categoria[]>([]);
  habilidades = signal<Habilidad[]>([]);
  categoriaElegida = signal<number | null>(null);
  habilidadesProyecto = signal<number[]>([]);
  usuarioLogeado = signal<Usuario | null>(null);
  comisionProyecto = signal<Comision | null>(null);

  constructor(
    private categoriaService: CategoriaService,
    private habilidadService: HabilidadService,
    private guardadoUsuario: GuardadoUsuario, 
    private proyectoService: ProyectoService, 
    private comisionService: ComisionService) {}

  ngOnInit(): void {
    this.usuarioLogeado.set(this.guardadoUsuario.getUsuarioLogeado());

    this.categoriaService.getTodasLasCategorias().subscribe({
      next: (categoriasGuardadas: Categoria[]) => this.categorias.set(categoriasGuardadas)
    });

    this.habilidadService.getHabilidades().subscribe({
      next: (habilidades: Habilidad[]) => this.habilidades.set(habilidades)
    })

    this.comisionService.getUltimaComision().subscribe({
      next: (comision: Comision) => this.comisionProyecto.set(comision)
    })

    this.formProyecto = new FormGroup({
      titulo: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required),
      presupuesto: new FormControl<number | null>(null, Validators.required),
      fechaLimite: new FormControl<Date | null>(null, Validators.required),
      cliente: new FormControl<string>(''),
      categoria: new FormControl<number | null>(null), 
      habilidades: new FormControl<number[]>([])
    });
  }

  elegirCategoria(idCategoria: number): void {
    this.categoriaElegida.set(idCategoria)
    this.formProyecto.patchValue({
      categoria: idCategoria
    });
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

  guardarProyecto(): void {
    this.formProyecto.patchValue({
      cliente: this.usuarioLogeado()?.nombreUsuario,
      habilidades: this.habilidadesProyecto()
    });

    const miForm = this.formProyecto.value;
    const fechaProyecto = new Date(miForm.fechaLimite)
    const fechaActual = new Date();

    if (miForm.presupuesto <= 0) {
      alert('El presupuesto no puede ser menor o igual que cero');
      return;
    }
    if (fechaProyecto <= fechaActual) {
      alert('La fecha no puede ser anterior a la actual');
      return;
    }
    if (!miForm.categoria) {
      alert('Aun no se ha seleccionado una categoria');
      return;
    }
    if (this.habilidadesProyecto().length <= 0) {
      alert('Aun no se ha escogido una habilidad')
      return;
    }

    this.proyectoService.agregarProyecto(miForm).subscribe({
      next: () => {
        alert('El proyecto se ha guardado y publicado con exito');
        this.formProyecto.reset();
        this.categoriaElegida.set(null);
        this.habilidadesProyecto.set([]);
      },
      error: () => alert('Ocurrio un error al momento de Guardar el Proyecto')
    });
  }

  puedePublicar(): boolean {
    const descripcion: string = this.usuarioLogeado()?.descripcionEmpresa || '';
    const sector: string = this.usuarioLogeado()?.industriaPerteneciente || '';
    const sitioWeb: string = this.usuarioLogeado()?.sitioWeb || '';
    
    return !!descripcion.trim() && !!sector.trim() && !!sitioWeb.trim();
  }

}
