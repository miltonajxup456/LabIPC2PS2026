import { Component, signal } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { CargarArchivosService } from '../../../restapi/cargar-archivos-service/cargar-archivos-service';
import { RegistroErrores } from '../../../modelos/registro-errores/registro-errores';

@Component({
  selector: 'app-carga-de-archivos',
  imports: [Head, RegresoMenu],
  templateUrl: './carga-de-archivos.html',
  styleUrl: './carga-de-archivos.css',
})
export class CargaDeArchivos {

  titulo: string = 'Espacio para la Carga de Archivos';
  linkAnterior: string = '/area-administrador';

  archivoElegido!: File;
  registroErrores = signal<RegistroErrores | null>(null);

  constructor(private cargarArchivosService: CargarArchivosService) {}

  cargarArchivo(event: any): void {
    this.archivoElegido = event.target.files[0];

    const formData: FormData = new FormData();
    formData.append('archivo', this.archivoElegido);

    this.cargarArchivosService.cargarArchivo(formData).subscribe({
      next: (errores: RegistroErrores) => {
        alert('El archivo ha sido cargado exitosamente');
        this.registroErrores.set(errores);
      },
      error: () => alert('Ocurrio un problema con el Archivo')
    })
  }
}
