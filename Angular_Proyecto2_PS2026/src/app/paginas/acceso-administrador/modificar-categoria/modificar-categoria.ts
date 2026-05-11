import { Component, OnInit, signal } from '@angular/core';
import { Categoria } from '../../../models/categoria/categoria';
import { CategoriaService } from '../../../restApi/categoria-service/categoria-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";

@Component({
  selector: 'app-modificar-categoria',
  imports: [ReactiveFormsModule, Header, MenuSecundario],
  templateUrl: './modificar-categoria.html',
  styles: ``,
})
export class ModificarCategoria implements OnInit {

  titulo: string = 'Área de Modificación de Categoria';
  linkAnterior: string = '/acceso-administrador';
  categorias = signal<Categoria[]>([]);
  categoriaElegida = signal<Categoria | null>(null);
  formCategoria!: FormGroup;

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.categoriaService.getTodasLasCategorias().subscribe({
      next: (categorias: Categoria[]) => this.categorias.set(categorias)
    })
    this.formCategoria = new FormGroup({
      nombreCategoria: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required)
    });
  }

  elegirCategoria(categoria: Categoria): void {
    this.categoriaElegida.set(categoria);
    this.formCategoria.patchValue({
      nombreCategoria: categoria.nombreCategoria,
      descripcion: categoria.descripcion
    });
  }

  actualizarCategoria(): void {
    const miForm = this.formCategoria.value;
    const idCategoria = this.categoriaElegida()?.idCategoria!;
    this.categoriaService.editarCategoria(idCategoria, miForm).subscribe({
      next: () => {
        alert('Se ha actualizado la categoria con exito');
        this.categorias.update(lista => lista.map(categoria => categoria.idCategoria === idCategoria 
          ? {...categoria, nombreCategoria: miForm.nombreCategoria, descripcion: miForm.descripcion}: categoria));
        this.categoriaElegida.set(null);
        this.formCategoria.reset();
      }
    });
  }

}
